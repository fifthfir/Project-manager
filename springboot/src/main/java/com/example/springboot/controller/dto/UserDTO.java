package com.example.springboot.controller.dto;

import lombok.Data;

/**
 * Receive information from frontend login request
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;

}
