package com.steverado.article_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@NoArgsConstructor
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title field should not be empty")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "content field should not be empty")
    @Column(name = "content", nullable = false , columnDefinition = "TEXT")
    private String content;

    //Many articles can belong to one user
    @NotNull(message = "user id field should not be empty")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
