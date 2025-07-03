package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.auth.SigninReqDto;
import com.korit.BoardStudy.dto.auth.SignupReqDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.entity.UserRole;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.repository.UserRoleRepository;
import com.korit.BoardStudy.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        // 아이디 중복 확인
        Optional<User> userByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());
        if(userByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 아이디입니다.", null);
        }
        // 이메일 중복 확인
        Optional<User> userByEmail = userRepository.getUserByEmail(signupReqDto.getEmail());
        if(userByEmail.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 이메일입니다.", null);
        }

        try {
            Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
            if(optionalUser.isEmpty()) {
                throw new RuntimeException("회원 정보 추가 실패");
            }
            User user = optionalUser.get();
            UserRole userRole = UserRole.builder()
                        .userId(user.getUserId())
                        .roleId(3)
                        .build();

            int addUserRoleResult = userRoleRepository.addUserRole(userRole);
            if(addUserRoleResult != 1) {
                throw new RuntimeException("권한 추가 실패");
            }
            return new ApiRespDto<>("success" ,"회원 가입이 성공적으로 완료되었습니다.", user);
        } catch (Exception e) { // throw로 던진 메세지 추가해서 클라이언트한테 전달
            return new ApiRespDto<>("failed", "회원 가입 중 오류 발생: " + e.getMessage(), null);
        }
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> optionalUser = userRepository.getUserByUsername(signinReqDto.getUsername());
        if(optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "아이디 혹은 비밀번호가 일치하지 않습니다.", null);
        }

        User user = optionalUser.get();
        // matches() 첫번째 매개변수가 평문
        if(!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "아이디 혹은 비밀번호가 일치하지 않습니다.", null);
        }

        String accessToken = jwtUtils.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인이 성공적으로 완료되었습니다.", accessToken);
    }
}

