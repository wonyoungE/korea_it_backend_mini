package com.korit.BoardStudy.dto.board;

import com.korit.BoardStudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardRespDto {
    private Integer boardId;
    private String title;
    private String content;
    private LocalDateTime createDt;

    private User user;
}
