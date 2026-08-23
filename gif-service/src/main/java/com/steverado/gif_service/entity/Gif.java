package com.steverado.gif_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gifs")
@NoArgsConstructor
@Getter
@Setter
public class Gif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url" , nullable = false)
    private String imageUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Gif(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }
}
