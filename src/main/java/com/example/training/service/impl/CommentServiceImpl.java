package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.training.entity.*;
import com.example.training.mapper.CommentMapper;
import com.example.training.mapper.IntentionMapper;
import com.example.training.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private IntentionMapper intentionMapper;

    @Override
    public boolean comment(Intention it, String comment) {
        if (Objects.isNull(it) || Objects.isNull(it.getId())) {
            throw new IllegalArgumentException("Intention or intention is null");
        }
        Comment cm =new Comment();
        cm.setIntentionid(it.getId());
        cm.setComment(comment);
        return commentMapper.insert(cm)>0;
    }

    @Override
    public PageBean getcomment(Course cs, Integer page, Integer pageSize) {
        if (Objects.isNull(cs) || Objects.isNull(cs.getId())) {
            throw new IllegalArgumentException("Course or course id is null");
        }
        if (Objects.isNull(page) || Objects.isNull(pageSize) ){
            throw new IllegalArgumentException("Page number or page size is null");
        }

        // 根据 course_id 查询 intention 表的多个 id
        LambdaQueryWrapper<Intention> intentionWrapper = new LambdaQueryWrapper<>();
        intentionWrapper.eq(Intention::getCourseId, cs.getId());
        List<Intention> intentions = intentionMapper.selectList(intentionWrapper);

        // 提取 intention 表的 id 集合
        List<Integer> intentionIds = intentions.stream()
                .map(Intention::getId)
                .collect(Collectors.toList());

        //设置分页参数（页数，每页size）
        PageHelper.startPage(page,pageSize);

        // 根据 intention 表的 id 集合查询 comment 表的记录
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.in(Comment::getIntentionid, intentionIds);
        List<Comment> comments = commentMapper.selectList(commentWrapper);

        Page<Comment> pg = (Page<Comment>) comments;
        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;

    }


}
