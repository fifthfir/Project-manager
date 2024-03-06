package com.example.springboot.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;

import com.example.springboot.service.ICommentService;
import com.example.springboot.entity.Comment;

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
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @PostMapping
    public Result save(@RequestBody Comment comment) {
        comment.setTime(DateUtil.now());
        Integer pid = comment.getPid();
        if (pid != null) {
            Comment pComment = commentService.getById(pid);
            if (pComment.getOriginId() != null) {
                comment.setOriginId(pComment.getOriginId());
            } else {
                comment.setOriginId(comment.getPid());
            }
        }

        return Result.success(commentService.saveOrUpdate(comment));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(commentService.removeByIds(ids));

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(commentService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
        return Result.success (commentService.list());
    }

    @GetMapping("/tree/{articleId}")
    public Result findCommentDetail(@PathVariable Integer articleId) {
        List<Comment> articleComments = commentService.findCommentDetail(articleId);

        List<Comment> originComments = articleComments.stream()
            .filter(comment -> comment.getOriginId() == null).collect(
                Collectors.toList());

        for (Comment origin: originComments) {
            List<Comment> children = articleComments.stream()
                .filter(comment -> origin.getId().equals(comment.getOriginId())).collect(
                    Collectors.toList());
            children.forEach(comment -> {
                articleComments.stream().filter(c1 ->c1.getId().equals(comment.getPid())).findFirst().ifPresent((v -> {
                    comment.setPNickname(v.getNickname());
                    comment.setPUserId(v.getUserId());
                }));
            });
            origin.setChildren(children);
        }

        return Result.success (originComments);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

