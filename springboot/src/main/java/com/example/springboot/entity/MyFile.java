package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_file")
public class MyFile {
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private boolean isDelete;
    private boolean enable;
}
