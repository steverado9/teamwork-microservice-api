package com.steverado.article_service.service.Impl;

import com.steverado.article_service.dto.CommentDto;
import com.steverado.article_service.entity.Article;
import com.steverado.article_service.entity.ArticleComment;
import com.steverado.article_service.entity.User;
import com.steverado.article_service.enums.Role;
import com.steverado.article_service.exception.NotAdminException;
import com.steverado.article_service.repository.ArticleCommentRepository;
import com.steverado.article_service.response.ApiResponse;
import com.steverado.article_service.response.DataArticleCommentResponse;
import com.steverado.article_service.service.ArticleCommentService;
import com.steverado.article_service.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleService articleService;

    private final RestTemplate restTemplate;

    private final HttpServletRequest request;

    private final ArticleCommentRepository commentRepository;

    @Override
    public ResponseEntity<ApiResponse> saveComment(Long articleId, CommentDto commentDto) {
        //get existing article with id
        Article article = articleService.getArticleById(articleId).orElseThrow();

        Long userId = article.getUserId();

        ArticleComment comment = new ArticleComment();
        comment.setComment(commentDto.getComment());
        comment.setUserId(userId);
        comment.setArticleId(articleId);

        commentRepository.saveComment(comment.getComment(), comment.getArticleId(), comment.getUserId());

        Optional<ArticleComment> articleComment = commentRepository.getArticleCommentByArticleId(article.getId());

        DataArticleCommentResponse data = new DataArticleCommentResponse();
        data.setMessage("Comment successfully created");
        data.setCreatedOn(articleComment.get().getCreatedAt());
        data.setArticleTitle(article.getTitle());
        data.setArticle(article.getContent());
        data.setComment(articleComment.get().getComment());

        ApiResponse response = new ApiResponse("success", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
