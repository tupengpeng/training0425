package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        qw.eq("id",cs.getId());
        qw.eq("password",cs.getPassword());
        List<Customer> cl = customerMapper.selectList(qw);
        log.info(customerMapper.selectList(qw));
        return cl;
    }

}
