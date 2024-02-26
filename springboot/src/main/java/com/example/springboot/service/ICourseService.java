package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ruotian
 * @since 2024-02-25
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> coursePage, String name);

    void setStudentCourse(String courseId, String studentId);
}
