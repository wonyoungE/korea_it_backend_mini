package com.korit.BoardStudy.repository;

import com.korit.BoardStudy.entity.Comment;
import com.korit.BoardStudy.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CommentRepository {
    @Autowired
    private CommentMapper commentMapper;

    public Optional<Comment> addComment(Comment comment) {
        try {
            commentMapper.addComment(comment);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
        return Optional.of(comment);
    }

}
