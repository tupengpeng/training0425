package com.example.training.controller;

import com.example.training.entity.*;
import com.example.training.service.ICustomerService;
import com.example.training.service.IEnrollmentPlanService;
import com.example.training.service.IExtensionworkerService;
import com.example.training.service.IIntentionService;
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
@RequestMapping("/extensionworker")
//针对部分service实现层抛出异常使用
//catch (IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + ex.getMessage());
//        }
public class ExtensionworkerController {

    @Autowired
    private IExtensionworkerService iExtensionworkerService;
    @Autowired
    private IIntentionService iIntentionService;
    @Autowired
    private ICustomerService customerService;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody Extensionworker cs) {
        log.info("Worker登录", cs);
        // 进行身份验证
        List<Extensionworker> listc = iExtensionworkerService.getworkertoLogin(cs);

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

    // 附带自己的联系方式进行转发
    @PostMapping("/share")
    public Result share(@RequestBody Map<String, Object> requestMap) {
        Extensionworker ex = (Extensionworker) requestMap.get("extensionworker");
        EnrollmentPlan en = (EnrollmentPlan) requestMap.get("enrollmentPlan");

        return iExtensionworkerService.share(ex, en) ? Result.success() : Result.error("转发失败");
    }

    // 统计每一个课程的人数
    @GetMapping("/count")
    public Result count() {
        String res = iIntentionService.countclass();
        return res.isEmpty() ? Result.error("转发失败") : Result.success(res);
    }

    // 根据名字查询该人的课程
    @PostMapping("/intentions")
    public Result getIntentionsByCustomerName(@RequestBody Course cs,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("根据名字查询该人的课程");
        log.info("分页查询, 参数: {},{}", page, pageSize);
        PageBean pageBean = iExtensionworkerService.getIntentionByCustomerName(cs.getName(), page, pageSize);
        return Result.success(pageBean);
    }
}

