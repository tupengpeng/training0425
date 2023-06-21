package com.example.training.service;

import com.example.training.entity.Course;
import com.example.training.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IIntentionService extends IService<Intention> {

    public boolean insertInf(Customer cs, Course cou);

    public boolean deleteInf(Customer cs, Course cou);

    public boolean changeInf(Customer cs, Course cou, String evaluation);


    public PageBean getapplyclass(Customer cs, Integer page, Integer pageSize);

    public String countclass();
}
