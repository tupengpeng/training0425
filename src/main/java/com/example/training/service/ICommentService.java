package com.example.training.service;

import com.example.training.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.entity.Course;
import com.example.training.entity.Intention;
import com.example.training.entity.PageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-05-16
 */
public interface ICommentService extends IService<Comment> {

    public boolean comment(Intention it, String comment);

    public PageBean getcomment(Course cs, Integer page, Integer pageSize);
}
