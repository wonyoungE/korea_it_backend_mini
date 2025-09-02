package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.account.UpdateUserReqDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public ApiRespDto<?> updateUser(UpdateUserReqDto updateUserReqDto, PrincipalUser principalUser) {
        System.out.println(updateUserReqDto);
        Optional<User> optionalUser = userRepository.getUserByUserId(updateUserReqDto.getUserId());

        if(optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 회원입니다.", null);
        }

        User user = optionalUser.get();
        if(!Objects.equals(user.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "수정 권한이 없습니다.", null);
        }

        User newUser = updateUserReqDto.toEntity();
        try {
            int result = userRepository.updateUser(newUser);
            if(result != 1) {
                return new ApiRespDto<>("failed", "프로필 수정에 실패했습니다.", null);
            }
            return new ApiRespDto<>("success", "프로필이 성공적으로 업데이트 되었습니다.", null);
        } catch(Exception e) {
            return new ApiRespDto<>("failed", "서버 오류로 프로필 수정에 실패했습니다.", null);
        }
    }
}
