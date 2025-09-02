package com.korit.BoardStudy.controller;

import com.korit.BoardStudy.dto.account.UpdateUserReqDto;
import com.korit.BoardStudy.security.model.PrincipalUser;
import com.korit.BoardStudy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "유저 id로 유저 정보 조회", description = "유저의 상세 정보를 가져옵니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserReqDto updateUserReqDto,
                                        @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userService.updateUser(updateUserReqDto, principalUser));
    }
}
