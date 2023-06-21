package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.training.entity.PageBean;
import com.example.training.entity.PromotionArticle;
import com.example.training.mapper.PromotionArticleMapper;
import com.example.training.service.IPromotionArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-06-06
 */
@Log4j2
@Service
public class PromotionArticleServiceImpl extends ServiceImpl<PromotionArticleMapper, PromotionArticle> implements IPromotionArticleService {

    @Autowired
    private PromotionArticleMapper promotionArticleMapper;
    @Override
    public PageBean pagePromotionArticle(PromotionArticle promotionArticle, Integer page, Integer pageSize) {
        //设置分页参数（页数，每页size）
        PageHelper.startPage(page,pageSize);

        //构造查询条件
        QueryWrapper<PromotionArticle> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(promotionArticle.getId())) {
            queryWrapper.eq("id", promotionArticle.getId());
        }
        if (Objects.nonNull(promotionArticle.getTitle())) {
            queryWrapper.eq("title", promotionArticle.getTitle());
        }
        if (Objects.nonNull(promotionArticle.getContent())) {
            queryWrapper.eq("content", promotionArticle.getContent());
        }
        if (Objects.nonNull(promotionArticle.getModifiedAt())) {
            queryWrapper.eq("modified_at", promotionArticle.getModifiedAt());
        }

        //执行查询
        List<PromotionArticle> promotionArticleList = promotionArticleMapper.selectList(queryWrapper);
        Page<PromotionArticle> pg = (Page<PromotionArticle>) promotionArticleList;

        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;
    }
}
