package com.steverado.gif_service.controller;

import com.steverado.gif_service.dto.CommentDto;
import com.steverado.gif_service.dto.GifDto;
import com.steverado.gif_service.entity.Gif;
import com.steverado.gif_service.reponse.ApiResponse;
import com.steverado.gif_service.service.GifCommentService;
import com.steverado.gif_service.service.GifService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "bearerAuth")
public class GifController {

    private final GifService gifService;

    private final GifCommentService gifCommentService;

    //post gif
    @PostMapping(path = "/gifs",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<ApiResponse> postGif(
            @Valid @ModelAttribute GifDto gifDto,
            @RequestPart MultipartFile file
    ) {
        return gifService.saveGif(gifDto, file);
    }

    //delete gif
    @DeleteMapping("/gifs/{id}")
    public ResponseEntity<ApiResponse> deleteGif(@PathVariable Long id) {

        return gifService.deleteGifById(id);
    }

    //post comments on gif
    @PostMapping("/gifs/{gifId}/comments")
    public ResponseEntity<ApiResponse> postComment(@PathVariable Long gifId, @Valid @RequestBody CommentDto commentDto) {
        return gifCommentService.postComment(gifId, commentDto);
    }

    //view gif with comments
    @GetMapping("gifs/{gifId}")
    public ResponseEntity<ApiResponse> viewGif(@PathVariable Long gifId) {
        return gifService.getGifAndCommentByGifId(gifId);
    }

    //get all gifs
    @GetMapping("/gifs")
    public List<Gif> viewAllGifs() {
        return gifService.getAllGifs();
    }
}
