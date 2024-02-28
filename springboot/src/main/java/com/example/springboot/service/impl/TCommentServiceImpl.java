package com.example.springboot.service.impl;

import com.example.springboot.entity.TComment;
import com.example.springboot.mapper.TCommentMapper;
import com.example.springboot.service.ITCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment> implements ITCommentService {

}
