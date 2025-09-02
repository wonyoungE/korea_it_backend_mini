package com.korit.BoardStudy.mapper;

import com.korit.BoardStudy.dto.board.GetBoardRespDto;
import com.korit.BoardStudy.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    int addBoard(Board board);
    int deleteBoard(Integer boardId);
    Optional<GetBoardRespDto> getBoardByBoardId(Integer boardId);
    List<Board> getBoardList(int size, int offset);
    List<Board> getBoardListByUserId(int userId);
    int updateBoard(Board board);
    int getBoardCount();
}
