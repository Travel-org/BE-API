package com.ssu.travel.domain.photo.entity;

import com.ssu.travel.domain.event.entity.Event;
import com.ssu.travel.domain.notice.entity.Notice;
import com.ssu.travel.domain.post.entity.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;


@Getter
@Entity
public class Photo<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    private String path;  // 이미지 소스(encode) => path(decode)

    private String extension;  // 확장자

    @Column(name = "photo_order")
    private int order;  // 사진 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    protected Photo() {
    }

    @Builder
    public Photo(T record, int order, String path, String extension) {
        if (record.getClass().equals(Event.class)) {
            this.event = (Event) record;
            event.getPhotos().add(this);
        } else if (record.getClass().equals(Notice.class)) {
            this.notice = (Notice) record;
            notice.getPhotos().add(this);
        } else if (record.getClass().equals(Post.class)) {
            this.post = (Post) record;
            post.getPhotos().add(this);
        }
        this.path = path;
        this.extension = extension;
        this.order = order;
    }
}
