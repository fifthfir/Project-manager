package com.example.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;

import com.example.springboot.service.ITCommentService;
import com.example.springboot.entity.TComment;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/t-comment")
public class TCommentController {

    @Resource
    private ITCommentService tCommentService;

    @PostMapping
    public Result save(@RequestBody TComment tComment) {
        return Result.success(tCommentService.saveOrUpdate(tComment));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(tCommentService.removeByIds(ids));

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(tCommentService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
        return Result.success (tCommentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(tCommentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<TComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(tCommentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

