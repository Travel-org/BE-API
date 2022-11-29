package com.ssu.travel.domain.friend.entity;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.exception.custom.DuplicatedRequestException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", referencedColumnName = "user_id")
    private User followee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", referencedColumnName = "user_id")
    private User follower;

    private Boolean isFriend;

    @Builder(access = AccessLevel.PRIVATE)
    private Friend(User followee, User follower, Boolean isFriend) {
        this.followee = followee;
        this.follower = follower;
        this.isFriend = isFriend;
    }

    public static Friend of(User followee, User follower) {
        return Friend.builder()
                .followee(followee)
                .follower(follower)
                .isFriend(false)
                .build();
    }

    public static Friend of(User followee, User follower, Boolean isFriend) {
        return Friend.builder()
                .followee(followee)
                .follower(follower)
                .isFriend(isFriend)
                .build();
    }

    public void acceptFriend() {
        if(this.isFriend) {
            throw new DuplicatedRequestException("해당 User와 이미 친구 입니다.");
        }
        this.isFriend = true;
    }
}
