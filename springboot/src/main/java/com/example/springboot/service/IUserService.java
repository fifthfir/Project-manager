package com.example.springboot.service;

import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-13
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    UserDTO register(UserDTO userDTO);
}
