package com.example.training.service.impl;

import com.example.training.entity.EnrollmentPlan;
import com.example.training.entity.PageBean;
import com.example.training.mapper.CustomerMapper;
import com.example.training.mapper.EnrollmentPlanMapper;
import com.example.training.service.IEnrollmentPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-04-25
 */
@Service
public class EnrollmentPlanServiceImpl extends ServiceImpl<EnrollmentPlanMapper, EnrollmentPlan> implements IEnrollmentPlanService {

    @Autowired
    private EnrollmentPlanMapper enrollmentPlanMapper;


    @Override
    public PageBean pageEnrollmentPlan(Integer page, Integer pageSize) {
        //设置分页参数（页数，每页size）
        PageHelper.startPage(page,pageSize);

        //执行查询
        List<EnrollmentPlan> enrList = enrollmentPlanMapper.selectList(null);
        Page<EnrollmentPlan> pg = (Page<EnrollmentPlan>) enrList;

        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;
    }
}
