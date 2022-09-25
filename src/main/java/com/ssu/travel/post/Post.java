package com.ssu.travel.post;


import com.ssu.travel.comment.Comment;
import com.ssu.travel.common.auditing.BaseTimeEntity;
import com.ssu.travel.photo.Photo;
import com.ssu.travel.schedule.Schedule;
import com.ssu.travel.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private Post(Schedule schedule, User user, String title, String content) {
        this.schedule = schedule;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Post of(Schedule schedule, User user, String title, String content) {
        Post post = new Post();
        post.schedule = schedule;
        post.user = user;
        post.title = title;
        post.content = content;
        return post;
    }
}
