package com.ssu.travel.notice;


import com.ssu.travel.common.auditing.BaseTimeEntity;
import com.ssu.travel.photo.Photo;
import com.ssu.travel.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private User publisher;

    @OneToMany(mappedBy = "notice")
    private List<Photo> photos = new ArrayList<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


    private Notice(String title, String content, User publisher) {
        this.title = title;
        this.content = content;
        this.publisher = publisher;
    }

    public static Notice of(String title, String content, User publisher) {
        return new Notice(title, content, publisher);
    }
}
