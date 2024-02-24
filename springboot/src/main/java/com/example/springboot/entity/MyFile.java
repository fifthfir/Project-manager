package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@TableName("sys_file")
public class MyFile {
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private boolean isDeleted;
    private boolean enable;
    private String md5;


    public void setIsDeleted(boolean b) {
        isDeleted = b;
    }
}
