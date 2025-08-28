package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> getUserByUserId(Integer userId) {
        Optional<User> optionalUser = userRepository.getUserByUserId(userId);

        if(optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 사용자입니다.", null);
        }

        return new ApiRespDto<>("success", "사용자 정보를 가져왔습니다.", optionalUser.get());
    }
}
