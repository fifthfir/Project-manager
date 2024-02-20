package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.utils.TokenUtils;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;

        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "System error");
        }

        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "Wrong username or password");
        }
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        boolean usernameExists = false;
        User one;

        try {
            usernameExists = count(queryWrapper) > 0;
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "System error");
        }

        if (!usernameExists) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "Username already exists");
        }
        return null;
    }
}
