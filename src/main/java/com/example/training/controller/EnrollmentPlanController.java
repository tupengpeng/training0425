package com.example.training.controller;

import com.example.training.entity.PageBean;
import com.example.training.entity.Result;
import com.example.training.service.IEnrollmentPlanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wp
 * @since 2023-04-25
 */
@Log4j2
@RestController
@RequestMapping("/enrollmentPlan")
public class EnrollmentPlanController {

    @Autowired
    private IEnrollmentPlanService iEnrollmentPlanService;

    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("分页查询, 参数: {},{}",page,pageSize);
        //调用service分页查询
        PageBean pageBean = iEnrollmentPlanService.pageEnrollmentPlan(page,pageSize);
        return Result.success(pageBean);
    }


}
