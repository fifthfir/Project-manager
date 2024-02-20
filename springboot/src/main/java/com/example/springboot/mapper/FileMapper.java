package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.MyFile;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

@Component
public class FileMapper implements BaseMapper<MyFile> {

    @Override
    public int insert(MyFile entity) {
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    public int deleteById(MyFile entity) {
        return 0;
    }

    @Override
    public int delete(Wrapper<MyFile> queryWrapper) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<?> idList) {
        return 0;
    }

    @Override
    public int updateById(MyFile entity) {
        return 0;
    }

    @Override
    public int update(MyFile entity, Wrapper<MyFile> updateWrapper) {
        return 0;
    }

    @Override
    public MyFile selectById(Serializable id) {
        return null;
    }

    @Override
    public List<MyFile> selectBatchIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public void selectBatchIds(Collection<? extends Serializable> idList,
        ResultHandler<MyFile> resultHandler) {

    }

    @Override
    public Long selectCount(Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public List<MyFile> selectList(Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public void selectList(Wrapper<MyFile> queryWrapper, ResultHandler<MyFile> resultHandler) {

    }

    @Override
    public List<MyFile> selectList(IPage<MyFile> page, Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public void selectList(IPage<MyFile> page, Wrapper<MyFile> queryWrapper,
        ResultHandler<MyFile> resultHandler) {

    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public void selectMaps(Wrapper<MyFile> queryWrapper,
        ResultHandler<Map<String, Object>> resultHandler) {

    }

    @Override
    public List<Map<String, Object>> selectMaps(IPage<? extends Map<String, Object>> page,
        Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public void selectMaps(IPage<? extends Map<String, Object>> page, Wrapper<MyFile> queryWrapper,
        ResultHandler<Map<String, Object>> resultHandler) {

    }

    @Override
    public <E> List<E> selectObjs(Wrapper<MyFile> queryWrapper) {
        return null;
    }

    @Override
    public <E> void selectObjs(Wrapper<MyFile> queryWrapper, ResultHandler<E> resultHandler) {

    }
}
