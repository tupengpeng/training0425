package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.training.entity.Comment;
import com.example.training.entity.Course;
import com.example.training.entity.Intention;
import com.example.training.entity.PageBean;
import com.example.training.mapper.CourseMapper;
import com.example.training.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-05-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private  CourseMapper  courseMapper;

    @Override
    public PageBean getCourse(Course cs, Integer page, Integer pageSize) {

        if (Objects.isNull(cs) || Objects.isNull(cs.getId())){
            throw new IllegalArgumentException("Page number or page size is null");
        }
        if (Objects.isNull(page) || Objects.isNull(pageSize) ){
            throw new IllegalArgumentException("Page number or page size is null");
        }
        //设置分页参数（页数，每页size）
        PageHelper.startPage(page,pageSize);

        //根据cs进行模糊查询
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(cs.getName())) {
            queryWrapper.like("name", cs.getName());
        }

        List<Course> comments = courseMapper.selectList(queryWrapper);;
        Page<Course> pg = (Page<Course>) comments;
        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;
    }

}
