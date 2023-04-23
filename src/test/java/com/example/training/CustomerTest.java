package com.example.training;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.training.entity.Customer;
import com.example.training.mapper.CustomerMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class CustomerTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void getCustom() {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("id","10100");
        qw.eq("password","1000");
        List<Customer> cl = customerMapper.selectList(qw);
        System.out.println("9999999999999999999999"+customerMapper.selectList(qw).isEmpty());
        log.info(customerMapper.selectList(qw));


    }
}
