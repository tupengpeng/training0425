package com.example.training.controller;

import com.example.training.entity.Customer;
import com.example.training.entity.Result;
import com.example.training.service.ICustomerService;
import com.example.training.service.IEnrollmentPlanService;
import com.example.training.utility.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wp
 * @since 2023-04-23
 */
@Log4j2
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping("/hello")
    public String hello(){
        return "hello !";
    }

    @PostMapping("/login")
    public Result login(@RequestBody Customer cs){

        log.info("Cus登录",cs);
        List<Customer> listc =iCustomerService.getCustomtoLogin(cs);

        //登录成功,生成令牌并下发令牌
        if (!listc.isEmpty()){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", listc.get(0).getId());
            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登录的员工信息
            return Result.success(jwt);
        }
        //登录失败, 返回错误信息
        return Result.error("用户名不存在或密码错误");

    }




}
