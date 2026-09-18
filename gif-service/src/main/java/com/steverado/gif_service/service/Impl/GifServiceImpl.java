package com.steverado.gif_service.service.Impl;

import com.steverado.gif_service.dto.CommentItemsDto;
import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.entity.Gif;
import com.steverado.gif_service.entity.GifComment;
import com.steverado.gif_service.entity.User;
import com.steverado.gif_service.enums.Role;
import com.steverado.gif_service.exception.GifNotFoundException;
import com.steverado.gif_service.exception.NotAdminException;
import com.steverado.gif_service.mapper.CommentItemsMapper;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.reponse.DataGifResponse;
import com.steverado.gif_service.reponse.DataViewGifResponse;
import com.steverado.gif_service.reponse.DeleteDataResponse;
import com.steverado.gif_service.repository.GifCommentRepository;
import com.steverado.gif_service.repository.GifRepository;
import com.steverado.gif_service.service.CloudinaryService;
import com.steverado.gif_service.service.GifService;
import com.steverado.gif_service.util.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GifServiceImpl implements GifService {

    private final CloudinaryService cloudinaryService;

    private final GifRepository gifRepository;

    private final HttpServletRequest request;

    private final RestTemplate restTemplate;

    private final GifCommentRepository gifCommentRepository;

    private final CommentItemsMapper commentItemsMapper;


    //get user id
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    @Override
    public Optional<Gif> getGifById(Long gifId) {
        return gifRepository.findGifById(gifId);
    }

    @Override
    public ResponseEntity<ApiResponse> saveGif(GifDto gifDto, MultipartFile file) {
        log.info("Received request to create gif with title: {}", gifDto.getTitle());

        Long userId = getUserId();

        final String image_url = cloudinaryService.uploadFile(file);
        log.info("image url: {}", image_url);

        Gif gif = new Gif();
        gif.setTitle(gifDto.getTitle());
        gif.setImageUrl(image_url);
        gif.setUserId(userId);

        gifRepository.saveGif(gif.getImageUrl(), gif.getTitle(), gif.getUserId());

        Gif savedGif = gifRepository.findGifByUserId(userId);

        DataGifResponse data = new DataGifResponse();

        data.setGifId(savedGif.getId());
        data.setMessage("Gif Image Successfully Posted");
        data.setCreatedOn(savedGif.getCreatedAt());
        data.setTitle(gif.getTitle());
        data.setImageUrl(gif.getImageUrl());

        ApiResponse<DataGifResponse> response = new ApiResponse<>("Success", data);
        log.info("Returning CREATED response for article '{}'", gif.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteGifById(Long id) {
        log.info("Received request to delete gif with id: {}", id);

        Long userId = getUserId();

        Optional<Gif> existingGif = getGifById(id);

        if (existingGif.isEmpty()) {
            return null;
        }

        Long existingGifId = existingGif.get().getId();

        String url = "http://user-service/auth/" + userId;

        String authorizationHeader = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

            User user = response.getBody();
            log.info("userId: {}", user.getId());

            if (user.getRole() != Role.ADMIN && user.getId() != userId) {
                throw new NotAdminException("FORBIDDEN!");
            }

        } catch (Exception e) {
            System.out.println("error getting user -> : " + e.getMessage());
        }

        gifCommentRepository.deleteCommentsWithGifId(id);
        gifRepository.deleteGifById(id);

        DeleteDataResponse data = new DeleteDataResponse();
        data.setMessage("gif post successfully deleted");

        ApiResponse<DeleteDataResponse> response = new ApiResponse<>("Success", data);
        log.info("Returning DELETED response for article '{}'", existingGif.get().getTitle());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> getGifAndCommentByGifId(Long gifId) {
        log.info("Get gif and comments using article id: {}", gifId);

        Gif gif = getGifById(gifId).orElseThrow(() -> new GifNotFoundException("Gif not found"));
        log.info("gif title: {}", gif.getTitle());

        List<GifComment> comments = gifCommentRepository.getAllCommentsByGifId(gifId);

        List<CommentItemsDto> gifComments = comments.stream()
                .map(commentItemsMapper::gifComment)
                .toList();

        DataViewGifResponse data = new DataViewGifResponse();
        data.setId(gif.getId());
        data.setCreatedOn(gif.getCreatedAt());
        data.setTitle(gif.getTitle());
        data.setUrl(gif.getImageUrl());
        data.setComments(gifComments);

        ApiResponse response = new ApiResponse("success", data);
        log.info("Returning Gif and comments response for gif title '{}'", gif.getTitle());
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Gif> getAllGifs() {
        return gifRepository.findAllGifs();
    }
}
