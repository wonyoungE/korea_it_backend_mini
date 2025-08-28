package com.korit.BoardStudy.mapper;

import com.korit.BoardStudy.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CommentMapper {
    Optional<Comment> getCommentByBoardId(Integer boardId);
    Optional<Comment> getCommentByUserId(Integer userId);
    int addComment(Comment comment);
    int deleteCommentByCommentId(Integer commentId);
    int updateComment(Comment comment);
}
