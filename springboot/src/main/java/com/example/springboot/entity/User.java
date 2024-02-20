package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-13
 */
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * email	nickname
     */
    private String nickname;

    /**
     * email
     */
    private String email;

    /**
     * phone
     */
    private String phone;

    /**
     * address
     */
    private String address;

    /**
     * time when create
     */
    private LocalDateTime createTime;

    /**
     * avatar
     */
    private String avatarUrl;

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", createTime=" + createTime +
            ", avatarUrl='" + avatarUrl + '\'' +
            '}';
    }


}
