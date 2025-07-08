package com.korit.BoardStudy.repository;

import com.korit.BoardStudy.entity.UserRole;
import com.korit.BoardStudy.mapper.UserRoleMapper;
import com.sun.jdi.IntegerValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRoleRepository {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole) {
        return userRoleMapper.addUserRole(userRole);
    }

    public Optional<UserRole> getUserRoleByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
    }

    public int updateRoleId(Integer userId, Integer userRoleId) {
        return userRoleMapper.updateRoleId(userId, userRoleId);
    }
}
