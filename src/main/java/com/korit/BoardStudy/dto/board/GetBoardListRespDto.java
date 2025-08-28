package com.korit.BoardStudy.dto.board;

import com.korit.BoardStudy.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetBoardListRespDto {
    private Integer totalPages; // 전체 페이지 수
    private Integer totalCount;  // 전체 게시물 수
    private List<Board> boardList;  // 현재 페이지 게시물 목록
}
