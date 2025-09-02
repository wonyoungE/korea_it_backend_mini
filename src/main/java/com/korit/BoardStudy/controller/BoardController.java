package com.korit.BoardStudy.controller;

import com.korit.BoardStudy.dto.board.AddBoardReqDto;
import com.korit.BoardStudy.dto.board.UpdateBoardReqDto;
import com.korit.BoardStudy.security.model.PrincipalUser;
import com.korit.BoardStudy.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto,
                                      @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Integer boardId,
                                         @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.deleteBoardId(boardId, principalUser));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(boardService.getBoardList(page, size));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getBoardListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.getBoardListByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBoard(@RequestBody UpdateBoardReqDto updateBoardReqDto,
                                         @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.updateBoard(updateBoardReqDto, principalUser));
    }
}
