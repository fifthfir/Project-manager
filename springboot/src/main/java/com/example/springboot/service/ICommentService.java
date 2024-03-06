package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-28
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer articleId);
}
