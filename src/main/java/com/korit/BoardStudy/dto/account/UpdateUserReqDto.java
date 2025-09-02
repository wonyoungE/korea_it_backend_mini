package com.korit.BoardStudy.dto.account;

import com.korit.BoardStudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReqDto {
    private Integer userId;
    private String username;
    private String profileImg;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .username(username)
                .profileImg(profileImg)
                .build();
    }
}
