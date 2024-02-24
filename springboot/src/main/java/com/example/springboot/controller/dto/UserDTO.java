package com.example.springboot.controller.dto;

import com.example.springboot.entity.Menu;
import java.util.List;
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
    private String role;
    private List<Menu> menus;

}
