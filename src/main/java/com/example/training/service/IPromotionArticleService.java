package com.example.training.service;

import com.example.training.entity.PageBean;
import com.example.training.entity.PromotionArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-06-06
 */
public interface IPromotionArticleService extends IService<PromotionArticle> {

    public PageBean pagePromotionArticle(PromotionArticle promotionArticle, Integer page, Integer pageSize);
}
