package com.korit.BoardStudy.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private Integer commentId;
    private String comment;
    private Integer userId;
    private Integer boardId;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}
