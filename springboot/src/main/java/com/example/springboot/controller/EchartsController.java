package com.example.springboot.controller;

import static com.example.springboot.common.Constants.FILES_KEY;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.config.AuthAccess;
import com.example.springboot.entity.MyFile;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.FileMapper;
import com.example.springboot.service.IUserService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260);
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }

        }
        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);
        System.out.println(q4);
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @AuthAccess
    @GetMapping("/file/front/all")
//    @Cacheable(value = "files", key = "targetClass + methodName")  // "''frontAll"
    public Result frontAll() {
//        String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<MyFile> myFiles = fileMapper.selectList(null); // take from database
//        List<MyFile> myFiles;
//        if (StrUtil.isBlank(jsonStr)) {
//            myFiles = fileMapper.selectList(null); // take from database
//            stringRedisTemplate.opsForValue().set(FILES_KEY, JSONUtil.toJsonStr(myFiles));
//        } else {
//            myFiles = JSONUtil.toBean(jsonStr, new TypeReference<List<MyFile>>() {
//            }, true);
//        }
        return Result.success(myFiles);
    }
}
