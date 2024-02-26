package com.example.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page,
        @Param("username") String username,
        @Param("nickname") String nickname,
        @Param("email") String email,
        @Param("address") String address);
}
