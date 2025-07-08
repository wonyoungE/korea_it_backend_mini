package com.korit.BoardStudy.dto.board;

import com.korit.BoardStudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBoardRespDto {
    private Integer boardId;
    private String title;
    private String content;

    private User user;
}
