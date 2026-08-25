package com.steverado.gif_service.service.Impl;

import com.steverado.gif_service.dto.CommentDto;
import com.steverado.gif_service.entity.Gif;
import com.steverado.gif_service.entity.GifComment;
import com.steverado.gif_service.mapper.CommentMapper;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.reponse.DataGifCommentResponse;
import com.steverado.gif_service.repository.GifCommentRepository;
import com.steverado.gif_service.service.GifCommentService;
import com.steverado.gif_service.service.GifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GifCommentServiceImpl implements GifCommentService {

    private final GifService gifService;

    private final CommentMapper commentMapper;

    private GifCommentRepository gifCommentRepository;

    @Override
    public ResponseEntity<ApiResponse> postComment(Long gifId, CommentDto commentDto) {

        Gif gif = gifService.getGifById(gifId).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Long userId = gif.getUserId();

        GifComment comment = commentMapper.toGifCommentEntity(commentDto);

        comment.setUserId(userId);
        comment.setGifId(gifId);

        gifCommentRepository.saveComment(comment.getComment(), comment.getUserId(), comment.getGifId());

        Optional<GifComment> gifComment = gifCommentRepository.getGifCommentByGifId(gifId);

        if (gifComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        DataGifCommentResponse data = new DataGifCommentResponse();
        data.setMessage("comment successfully created");
        data.setCreatedOn(gifComment.get().getCreatedAt());
        data.setGifTitle(gif.getTitle());
        data.setComment(gifComment.get().getComment());

        ApiResponse<DataGifCommentResponse> response = new ApiResponse<>("success", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
