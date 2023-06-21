package com.example.training.controller;

import com.example.training.entity.*;
import com.example.training.service.*;
import com.example.training.utility.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
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
//针对部分service实现层抛出异常使用
//catch (IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + ex.getMessage());
//        }
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IIntentionService iIntentionService;
    @Autowired
    private ICommentService iCommentService;

    // test
    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestBody Customer cs) {
        log.info("Cus注册", cs);
        return iCustomerService.insertInf(cs) ? Result.success() : Result.error("账号已存在");
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody Customer cs) {
        log.info("Cus登录", cs);
        // 进行身份验证
        List<Customer> listc = iCustomerService.getCustomtoLogin(cs);

        // 登录成功, 生成令牌并下发令牌
        if (!listc.isEmpty()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", listc.get(0).getId());
            // 使用工具类JwtUtils加密信息，jwt包含了当前登录的员工id信息
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        // 登录失败, 返回错误信息
        return Result.error("用户名不存在或密码错误");
    }

    // 登出
    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }

    // 查询自己信息
    @PostMapping("/query")
    public Result query(@RequestBody Customer cs) {
        log.info("Cus信息查询", cs);
        List<Customer> queriedCustomer = iCustomerService.query(cs);
        return queriedCustomer != null ? Result.success(queriedCustomer) : Result.error("Cus信息查询失败");
    }

    // 更新自己的信息
    @PostMapping("/updateInf")
    public Result updateInf(@RequestBody Customer cs) {
        log.info("Cus信息修改", cs);
        return iCustomerService.updateInf(cs) ? Result.success() : Result.error("Cus信息修改失败");
    }

    // 申请上课
    @PostMapping("/applyforclasses")
    public Result applyforclasses(@RequestBody Map<String, Object> requestData) {
        Customer cs = (Customer) requestData.get("customer");
        Course cou = (Course) requestData.get("course");

        log.info("Cus申请上课", cs);
        return iIntentionService.insertInf(cs, cou) ? Result.success() : Result.error("Cus申请上课失败");
    }

    // 删除试听记录
    @PostMapping("/deleteclasses")
    public Result deleteclasses(@RequestBody Map<String, Object> requestData) {
        Customer cs = (Customer) requestData.get("customer");
        Course cou = (Course) requestData.get("course");

        log.info("Cus删除记录", cs);
        return iIntentionService.deleteInf(cs, cou) ? Result.success() : Result.error("Cus删除试听记录失败");
    }
}
