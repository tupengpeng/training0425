package com.example.training.controller;

import com.example.training.entity.*;
import com.example.training.service.IAdminService;
import com.example.training.service.IEnrollmentPlanService;
import com.example.training.service.IExtensionworkerService;
import com.example.training.service.IPromotionArticleService;
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
 * @since 2023-06-05
 */
@Log4j2
@RestController
@RequestMapping("/admin")
//针对部分service实现层抛出异常使用
//catch (IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + ex.getMessage());
//        }
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IEnrollmentPlanService iEnrollmentPlanService;
    @Autowired
    private IExtensionworkerService iExtensionworkerService;
    @Autowired
    private IPromotionArticleService iPromotionArticleService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Extensionworker cs) {
        log.info("Worker登录", cs);
        //进行身份验证
        List<Admin> listc = adminService.getadmintoLogin(cs);

        //登录成功,生成令牌并下发令牌
        if (!listc.isEmpty()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", listc.get(0).getId());
            //使用工具类JwtUtils加密信息，jwt包含了当前登录的员工id信息
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名不存在或密码错误");
    }

    //登出
    @PostMapping("/loginout")
    //注销时从客户端删除存储的Token
    public Result loginout() {
        return Result.success();
    }

    // 修改招生计划
    @PostMapping("/updateplan")
    public Result updateplan(@RequestBody EnrollmentPlan en) {
        log.info("修改招生计划", en.getId());
        return iEnrollmentPlanService.updateplan(en) ? Result.success("修改招生计划成功") : Result.error("修改招生计划失败");
    }

    // 删除招生计划
    @PostMapping("/deleteplan")
    public Result deleteplan(@RequestBody EnrollmentPlan en) {
        log.info("删除招生计划", en.getId());
        return iEnrollmentPlanService.deleteplan(en) ? Result.success("删除招生计划成功") : Result.error("删除招生计划失败");
    }

    // 增加招生计划
    @PostMapping("/addplan")
    public Result addplan(@RequestBody EnrollmentPlan en) {
        log.info("增加招生计划", en.getId());
        return iEnrollmentPlanService.addplan(en) ? Result.success("增加招生计划成功") : Result.error("增加招生计划失败");
    }

    // 查询推广员
    @PostMapping("/findExtensionWorkerByCondition")
    public Result findExtensionWorkerByCondition(@RequestBody Extensionworker cs,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询推广员");
        log.info("分页查询, 参数: {},{}", page, pageSize);
        PageBean pageBean = iExtensionworkerService.findExtensionWorkerByCondition(cs, page, pageSize);
        return  pageBean != null ? Result.success(pageBean) : Result.error("查询推广员失败");
    }


    //根据id增删改该人的课程

    @PostMapping("/addworker")
    public Result addWorker(@RequestBody Extensionworker worker) {
        boolean success = iExtensionworkerService.addWorker(worker);
        return success ? Result.success() : Result.error("addworker失败");
    }

    @PostMapping("/deleteworker")
    public Result deleteWorker(@RequestBody Extensionworker worker) {
        boolean success = iExtensionworkerService.deleteWorker(worker);
        return success ? Result.success() : Result.error("deleteworker失败");
    }

    @PostMapping("/updateworker")
    public Result updateWorker(@RequestBody Extensionworker worker) {
        boolean success = iExtensionworkerService.updateWorker(worker);
        return success ? Result.success() : Result.error("updateworker失败");
    }


}
