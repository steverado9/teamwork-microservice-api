package com.steverado.article_service.repository;

import com.steverado.article_service.entity.Article;
import com.steverado.article_service.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
>>>>>>> fe30fbc2a1bd1887e71828b7b30ebf900ef75c16

import java.util.List;
import java.util.Optional;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
<<<<<<< HEAD

    @Transactional
    @Modifying
=======
>>>>>>> fe30fbc2a1bd1887e71828b7b30ebf900ef75c16
    @Query(value = """
            INSERT INTO article_comments(comment, article_id, user_id, created_at)
            VALUE ( :comment, :articleId, :userId, CURRENT_TIMESTAMP)
            """, nativeQuery = true)
    void saveComment(
            @Param("comment") String comment,
            @Param("articleId") Long articleId,
            @Param("userId") Long userId);

    @Query(value = """
            SELECT * FROM article_comments
            WHERE article_id = :articleId
            AND comment_id = LAST_INSERT_ID();
            """, nativeQuery = true)
    Optional<ArticleComment> getArticleCommentByArticleId(@Param("articleId") Long id);

    @Query(value = """
            SELECT * FROM article_comments
            WHERE article_id = :articleId
            ORDER BY created_at DESC
            """, nativeQuery = true)
    List<ArticleComment> getAllCommentsByArticleId(@Param("articleId") Long articleId);
<<<<<<< HEAD

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM article_comments
            WHERE article_id = :articleId
            """, nativeQuery = true)
    void deleteCommentsByArticleId(@Param("articleId") Long articleId);
=======
>>>>>>> fe30fbc2a1bd1887e71828b7b30ebf900ef75c16
}
