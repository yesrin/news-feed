package com.sparta.newsfeed.User.service;

import com.sparta.newsfeed.User.dto.IntroduceRequestDto;
import com.sparta.newsfeed.User.dto.IntroduceResponseDto;
import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.SignupResponseDto;
import com.sparta.newsfeed.User.entity.User;
import com.sparta.newsfeed.User.entity.UserRoleEnum;
import com.sparta.newsfeed.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto createUser(SignupRequestDto requestDto) {

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // password Encode
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 ROLE 설정
        UserRoleEnum role = UserRoleEnum.USER;

        // 권리자 키 방식
//        if (requestDto.isAdmin()) {
//            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User(requestDto.getPassword(), password, requestDto.getEmail(), role);
        userRepository.save(user);
        return new SignupResponseDto(user.getUsername(), "Signup Success");
    }

    public IntroduceResponseDto editIntroduce(Long id,IntroduceRequestDto requestDto) {

        User user = userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        user.update(requestDto.getNickname(), requestDto.getMy_comment());

        return new IntroduceResponseDto(requestDto.getMy_comment(),requestDto.getNickname());
    }

    public IntroduceResponseDto selecteIntroduce(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        return new IntroduceResponseDto(user.getNickname(), user.getMy_content());
    }

//    public SignupResponseDto editPassword(SignupRequestDto requestDto) {
//    }


}
