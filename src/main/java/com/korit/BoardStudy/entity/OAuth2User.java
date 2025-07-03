package com.korit.BoardStudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class OAuth2User {
    private Integer oAuth2UserId;
    private Integer userId;
    private String provider;
    private String providerUserId;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}