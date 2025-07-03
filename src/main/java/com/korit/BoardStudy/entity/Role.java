package com.korit.BoardStudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleNameKor;
}
