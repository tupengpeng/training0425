package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.training.entity.*;
import com.example.training.mapper.CustomerMapper;
import com.example.training.mapper.EnrollmentPlanMapper;
import com.example.training.mapper.ExtensionworkerMapper;
import com.example.training.mapper.IntentionMapper;
import com.example.training.service.IExtensionworkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
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
 * @since 2023-06-05
 */
@Log4j2
@Service
public class ExtensionworkerServiceImpl extends ServiceImpl<ExtensionworkerMapper, Extensionworker> implements IExtensionworkerService {

    @Autowired
    private ExtensionworkerMapper extensionworkerMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EnrollmentPlanMapper enrollmentPlanMapper;
    @Autowired
    private IntentionMapper intentionMapper;

    @Override
    public List<Extensionworker> getworkertoLogin(Extensionworker cs) {
            if ( Objects.isNull(cs) || StringUtils.isBlank(cs.getPhone()) || StringUtils.isBlank(cs.getPassword())) {
                throw new IllegalArgumentException("Phone number or password is null or blank");
            }
            QueryWrapper<Extensionworker> qw = new QueryWrapper<>();
            qw.eq("phone",cs.getPhone());
            qw.eq("password",cs.getPassword());
            List<Extensionworker> cl = extensionworkerMapper.selectList(qw);
            log.info(extensionworkerMapper.selectList(qw));
            return cl;

    }

    @Override
    public boolean share(Extensionworker ex, EnrollmentPlan en) {
        if (Objects.isNull(ex)  || Objects.isNull(en) ) {
            throw new IllegalArgumentException("Extension worker or enrollment plan is null");
        }
        // 根据Extensionworker的id查询Extensionworker表
        Extensionworker worker = extensionworkerMapper.selectById(ex.getId());

        // 根据EnrollmentPlan的id查询EnrollmentPlan表
        EnrollmentPlan plan = enrollmentPlanMapper.selectById(en.getId());

        // 追加字符串到EnrollmentPlan的title字段
        plan.setTitle(plan.getTitle() + "[转发]");

        // 追加字符串到EnrollmentPlan的content字段
        String content = plan.getContent();
        content += "\nName: " + worker.getName();
        content += "\nEmail: " + worker.getEmail();
        content += "\nPhone: " + worker.getPhone();
        plan.setContent(content);

        // 更新EnrollmentPlan表
        int affectedRows = enrollmentPlanMapper.updateById(plan);

        // 返回更新结果
        return affectedRows > 0;
    }

    @Override
    public PageBean getIntentionByCustomerName(String name, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(name) || page == null || pageSize == null) {
            throw new IllegalArgumentException("Customer name, page or page size is null or blank");
        }
        //
        LambdaQueryWrapper<Customer> customerLambdaQueryWrapperWrapper = new LambdaQueryWrapper<>();
        customerLambdaQueryWrapperWrapper.eq(Customer::getName, name);//:: 表示方法引用
        List<Customer> customersustomer = customerMapper.selectList(customerLambdaQueryWrapperWrapper);

        // 提取
        List<Integer> Ids = customersustomer.stream()
                .map(Customer::getId)
                .collect(Collectors.toList());

        //设置分页参数（页数，每页size）
        PageHelper.startPage(page,pageSize);

        // 根据
        LambdaQueryWrapper<Intention> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.in(Intention::getCustomerId, Ids);
        List<Intention> intentions = intentionMapper.selectList(Wrapper);

        Page<Intention> pg = (Page<Intention>) intentions;
        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;
    }

    @Override
    public PageBean findExtensionWorkerByCondition(Extensionworker worker, Integer page, Integer pageSize) {
        if (Objects.isNull(page) || Objects.isNull(pageSize)) {
            throw new IllegalArgumentException("Page or page size is null");
        }

        //开始分页
        PageHelper.startPage(page, pageSize);

        //根据条件查询，使用 MyBatis-Plus 的 QueryWrapper
        QueryWrapper<Extensionworker> queryWrapper = new QueryWrapper<>();

        if (Objects.nonNull(worker)) {
            if (Objects.nonNull(worker.getId())) {
                queryWrapper.eq("id", worker.getId());
            }
            if (Objects.nonNull(worker.getName())) {
                queryWrapper.eq("name", worker.getName());
            }
            if (Objects.nonNull(worker.getEmail())) {
                queryWrapper.eq("email", worker.getEmail());
            }
            if (Objects.nonNull(worker.getPhone())) {
                queryWrapper.eq("phone", worker.getPhone());
            }
            if (Objects.nonNull(worker.getPassword())) {
                queryWrapper.eq("password", worker.getPassword());
            }
        }

        //执行查询
        List<Extensionworker> workers = extensionworkerMapper.selectList(queryWrapper);
        Page<Extensionworker> pg = (Page<Extensionworker>) workers;

        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;

    }

    // 增加一个新的 worker
    public boolean addWorker(Extensionworker worker) {
        if (Objects.isNull(worker)) {
            throw new IllegalArgumentException("Worker is null");
        }
        return extensionworkerMapper.insert(worker) > 0;
    }

    // 根据 id 删除 worker
    public boolean deleteWorker(Extensionworker worker) {
        if (Objects.isNull(worker) || Objects.isNull(worker.getId())) {
            throw new IllegalArgumentException("Worker or worker ID is null");
        }
        return extensionworkerMapper.deleteById(worker.getId()) > 0;
    }

    // 更新 worker
    public boolean updateWorker(Extensionworker worker) {
        if (Objects.isNull(worker) || Objects.isNull(worker.getId())) {
            throw new IllegalArgumentException("Worker or worker ID is null");
        }
        return extensionworkerMapper.updateById(worker) > 0;
    }

}

