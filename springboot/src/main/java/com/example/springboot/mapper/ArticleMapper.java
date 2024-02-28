package com.example.springboot.mapper;

import com.example.springboot.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-28
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
