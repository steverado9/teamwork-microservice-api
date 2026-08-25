package com.steverado.gif_service.repository;

import com.steverado.gif_service.entity.GifComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GifCommentRepository extends JpaRepository<GifComment, Long> {
    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO gif_comments(comment, gif_id, user_id, created_at)
            VALUES (:comment, :gifId, :userId, CURRENT_TIMESTAMP)
            """, nativeQuery = true)
    void saveComment(
            @Param("comment") String comment,
            @Param("userId") Long userId,
            @Param("gifId") Long gifId);

    @Query(value = """
            SELECT * FROM gif_comments
            WHERE gif_id = :gifId
            AND id = LAST_INSERT_ID();
            """, nativeQuery = true)
    Optional<GifComment> getGifCommentByGifId(@Param("gifId") Long gifId);

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM gif_comments
            WHERE gif_id = :gifId
            """, nativeQuery = true)
    void deleteCommentsWithGifId(@Param("gifId") Long id);

    @Transactional
    @Modifying
    @Query(value = """
            SELECT * FROM gif_comments
            WHERE gif_id = :gifId
            ORDER BY id DESC
            """, nativeQuery = true)
    List<GifComment> getAllCommentsByGifId(@Param("gifId") Long gifId);
}
