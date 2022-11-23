package com.ssu.travel.domain.comment.entity;

import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public static Comment of(User user, Post post, String content) {
        return new Comment(user, post, content);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
