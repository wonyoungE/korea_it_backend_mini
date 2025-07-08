package com.korit.BoardStudy.service;

import com.korit.BoardStudy.dto.ApiRespDto;
import com.korit.BoardStudy.dto.mail.SendMailReqDto;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.entity.UserRole;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.repository.UserRoleRepository;
import com.korit.BoardStudy.security.jwt.JwtUtils;
import com.korit.BoardStudy.security.model.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender javaMailSender;

    public ApiRespDto<?> sendMail(SendMailReqDto sendMailReqDto, PrincipalUser principalUser) {
        if(!principalUser.getEmail().equals(sendMailReqDto.getEmail())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> optionalUser = userRepository.getUserByEmail(sendMailReqDto.getEmail());
        if(optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 이메일입니다.", null);
        }

        User user = optionalUser.get();
        boolean hasTempRole = user.getUserRoles().stream().anyMatch(userRole -> userRole.getRoleId() == 3);
        if(!hasTempRole) {
            return new ApiRespDto<>("failed", "인증이 필요한 계정이 아닙니다.", null);
        }

        String verifyToken = jwtUtils.generateVerifyToken(user.getUserId().toString());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("이메일 인증입니다.");
        message.setText("링크를 클릭하여 인증을 완료해주세요.: " +
                "http://localhost:8080/mail/verify?verifyToken=" + verifyToken);
        javaMailSender.send(message);

        return new ApiRespDto<>("success", "인증 메일 전송이 완료되었습니다.", null);
    }

    public Map<String, Object> verify(String token) {
        System.out.println("메일 인증");
        Claims claims = null;
        Map<String, Object> resultMap = null;

        try {
            claims = jwtUtils.getClaims(token);
            String subject = claims.getSubject();
            if(!subject.equals("VerifyToken")) {
                resultMap = Map.of("status", "failed", "message", "인증 실패");
            }

            Integer userId = Integer.parseInt(claims.getId());
            Optional<User> optionalUser = userRepository.getUserByUserId(userId);
            if(optionalUser.isEmpty()) {
                resultMap = Map.of("status", "failed", "message", "존재하지 않는 사용자입니다.");
            }

            Optional<UserRole> optionalUserRole = userRoleRepository.getUserRoleByUserIdAndRoleId(userId, 3);
            if(optionalUserRole.isEmpty()) {
                resultMap = Map.of("status", "failed", "message", "이메일 인증이 완료된 계정입니다.");
            } else {
                int result = userRoleRepository.updateRoleId(userId, optionalUserRole.get().getUserRoleId());
                resultMap = Map.of("status", "success", "message", "이메일 인증이 완료되었습니다.");
            }
        } catch (ExpiredJwtException e) {
            resultMap = Map.of("status", "failed", "message", "인증 시간이 만료된 요청입니다.\n" +
                    "인증 메일을 다시 요청해주세요.");
        } catch (Exception e) {
            resultMap = Map.of("status", "failed", "message", "잘못된 요청입니다.\n인증 메일을 다시 요청해주세요.");
        }

        return resultMap;
    }
}
