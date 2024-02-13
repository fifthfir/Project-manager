package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * Add and modify
     * @param user the User to add or modify
     * @return 1 if success
     */
    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Search all saved data
     * @return list of all saved data
     */
    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

//    /**
//     *
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping("/page")
//    public Map<String, Object>findPage(@RequestParam Integer pageNum,
//                                       @RequestParam Integer pageSize,
//                                       @RequestParam String username,
//                                       @RequestParam String email,
//                                       @RequestParam String address) {
//        pageNum = (pageNum - 1) * pageSize;
//        List<User> data = userMapper.selectPage(pageNum, pageSize, username, email, address);
//        Integer totalNum = userMapper.selectTotal(username, email, address);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", totalNum);
//        return res;
//    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String username,
                                        @RequestParam(defaultValue = "") String nickname,
                                        @RequestParam(defaultValue = "") String email,
                                        @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("nickname", nickname);
        queryWrapper.like("email", email);
        queryWrapper.like("address", address);
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }
}
