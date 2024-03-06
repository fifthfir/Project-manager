package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Comment;
import com.example.springboot.mapper.CommentMapper;
import com.example.springboot.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;
    @Override
    public List<Comment> findCommentDetail(Integer articleId) {
        return commentMapper.findCommentDetail(articleId);
    }
}
