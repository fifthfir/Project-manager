package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "sys_user")
@Data  // setter, getter, toString
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    @JsonIgnore  // Hide the password at frontend
    private String password;

    private String nickname;
    private String email;
    private String phone;
    private String address;

    @TableField(value = "avatar_url")
    private String avatar;
}
