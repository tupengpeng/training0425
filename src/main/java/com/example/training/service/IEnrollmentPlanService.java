package com.example.training.service;

import com.example.training.entity.Customer;
import com.example.training.entity.EnrollmentPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.entity.PageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-04-25
 */
public interface IEnrollmentPlanService extends IService<EnrollmentPlan> {
    public PageBean pageEnrollmentPlan(Integer page, Integer pageSize);

}
