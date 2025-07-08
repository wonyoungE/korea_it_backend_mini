package com.korit.BoardStudy.dto.board;

import com.korit.BoardStudy.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBoardReqDto {
    private Integer boardId;
    private String title;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .build();
    }
}
