package com.ssu.travel.jpa.post;


import com.ssu.travel.jpa.comment.Comment;
import com.ssu.travel.jpa.photo.Photo;
import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.jpa.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post {
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
    private String text;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Photo> photos = new ArrayList<>();

    @Builder
    public Post(@NonNull Schedule schedule, @NonNull User user, String text, @NonNull String title) {
        this.schedule = schedule;
        this.user = user;
        this.text = text;
        this.title = title;
    }

    public void update(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
