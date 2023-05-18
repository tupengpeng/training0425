package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.training.mapper.CustomerMapper;
import com.example.training.entity.Customer;
import com.example.training.mapper.CustomerMapper;
import com.example.training.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-04-23
 */

@Log4j2
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> getCustomtoLogin(Customer cs) {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("phone",cs.getPhone());
        qw.eq("password",cs.getPassword());
        List<Customer> cl = customerMapper.selectList(qw);
        log.info(customerMapper.selectList(qw));
        return cl;
    }

    @Override
    public boolean updateInf(Customer cs) {
        UpdateWrapper<Customer> uw = new UpdateWrapper<>();
        uw.eq("id",cs.getId());
        if(customerMapper.update(cs,uw)==0){
            String logInf = "修改行数为："+customerMapper.update(cs,uw);
            log.info(logInf);
            return false;
        }
        return true;
    }

    @Override
    public boolean insertInf(Customer cs) {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("phone",cs.getPhone());
        qw.eq("password",cs.getPassword());
        List<Customer> cl = customerMapper.selectList(qw);
        if (!cl.isEmpty()) {
            log.info("账号已经存在，注册失败");
            return false;
        }

        if(customerMapper.insert(cs)==0){
            log.info("注册失败");
            return false;
        }
        log.info("注册成功");
        return true;
    }

    @Override
    public List<Customer> query(Customer cs) {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("phone",cs.getPhone());
        List<Customer> cl = customerMapper.selectList(qw);
        if (!cl.isEmpty()) {

            return cl;
        }
        return null;
    }



}
