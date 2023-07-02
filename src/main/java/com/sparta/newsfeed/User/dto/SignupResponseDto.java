package com.sparta.newsfeed.User.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String username;

    private String msg;


    public SignupResponseDto(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
}
