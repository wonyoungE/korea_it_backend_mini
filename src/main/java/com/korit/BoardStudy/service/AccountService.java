package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.account.ChangePasswordReqDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 비밀번호 변경
    // PrincipalUser는 왜 들고옴? ContextHolder에 등록된 user 정보의 password와 사용자로부터 입력받은 현재 비밀번호랑 같은지 비교 위함
    public ApiRespDto<?> changePassword(ChangePasswordReqDto changePasswordReqDto, PrincipalUser principalUser) {
        // 있는 사용자인지 확인
        Optional<User> userByUserId = userRepository.getUserByUserId(principalUser.getUserId());
        if(userByUserId.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 사용자입니다.", null);
        }

        // 요청으로 들어온 userId와 principalUser의 userId가 같은지 확인
        if(!Objects.equals(changePasswordReqDto.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }
        // 비밀번호가 같은지 확인
        if(!bCryptPasswordEncoder.matches(changePasswordReqDto.getOldPassword(), userByUserId.get().getPassword())) {
            return new ApiRespDto<>("failed", "기존 비밀번호가 일치하지 않습니다.", null);
        }

        // 새 비밀번호가 기존 비밀번호와 같은 경우
        if(bCryptPasswordEncoder.matches(changePasswordReqDto.getNewPassword(), userByUserId.get().getPassword())) {
            return new ApiRespDto<>("failed", "기존에 사용하던 비밀번호는 사용할 수 없습니다.", null);
        }

        // 새 비밀번호와 확인용 새 비밀번호가 같지 않은 경우
        if(!changePasswordReqDto.getNewPassword().equals(changePasswordReqDto.getNewCheckPassword())) {
            return new ApiRespDto<>("failed", "새 비밀번호가 일치하지 않습니다.", null);
        }

        int result = userRepository.updatePassword(changePasswordReqDto.toEntity(bCryptPasswordEncoder));
        if(result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);
        }
        return new ApiRespDto<>("success", "비밀번호 변경이 완료되었습니다.\n다시 로그인해주세요.", null);
    }
}
