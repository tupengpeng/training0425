package com.example.training.service;

import com.example.training.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.entity.Customer;
import com.example.training.entity.PageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-05-16
 */
public interface ICourseService extends IService<Course> {


     public PageBean getCourse(Course cs, Integer page, Integer pageSize);
}
