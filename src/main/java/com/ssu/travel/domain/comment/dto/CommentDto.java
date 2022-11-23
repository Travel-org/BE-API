package com.ssu.travel.domain.comment.dto;

import com.ssu.travel.domain.comment.entity.Comment;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto {

    private Long commentId;
    private SimpleUserDto userInfo;
    private String content;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.commentId = comment.getId();
        commentDto.userInfo = SimpleUserDto.from(comment.getUser());
        commentDto.content = comment.getContent();
        return commentDto;
    }
}
