package com.ssu.travel.jpa.photo;

import com.ssu.travel.jpa.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String path;

    @Builder
    public Photo(@NonNull Post post, @NonNull String path) {
        this.post = post;
        this.path = path;
    }
}