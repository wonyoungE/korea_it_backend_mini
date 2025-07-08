package com.korit.BoardStudy.mapper;

import com.korit.BoardStudy.dto.board.GetBoardRespDto;
import com.korit.BoardStudy.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    int addBoard(Board board);
    Optional<Board> getBoardByBoardId(Integer boardId);
    Optional<GetBoardRespDto> getBoard(Integer boardId);
    List<Board> getBoardList();
}
