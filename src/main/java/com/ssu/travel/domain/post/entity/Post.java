package com.ssu.travel.domain.post.entity;


import com.ssu.travel.domain.comment.entity.Comment;
import com.ssu.travel.domain.photo.entity.Photo;
import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.entity.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private Post(String title, String content, Schedule schedule, User user) {
        this.title = title;
        this.content = content;
        this.schedule = schedule;
        this.user = user;
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
