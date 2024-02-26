package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page<User> findPage(Page<User> page, String username, String nickname, String email, String address);
}
