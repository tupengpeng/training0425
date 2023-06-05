package com.example.training.service;

import com.example.training.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.entity.Intention;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-04-23
 */
public interface ICustomerService extends IService<Customer> {

    public List<Customer> getCustomtoLogin(Customer cs);

    public boolean updateInf(Customer cs);

    public boolean insertInf(Customer cs);

    public List<Customer> query(Customer cs);


}
