package com.example.training.controller;

import com.example.training.entity.Course;
import com.example.training.entity.Intention;
import com.example.training.entity.PageBean;
import com.example.training.entity.Result;
import com.example.training.service.ICommentService;
import com.example.training.service.ICourseService;
import com.example.training.service.IEnrollmentPlanService;
import com.example.training.service.impl.CourseServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
public class AlluserController {

    @Autowired
    private IEnrollmentPlanService iEnrollmentPlanService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private ICourseService iCourseService;

    @GetMapping("/enrollmentPlan/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("分页查询, 参数: {},{}",page,pageSize);
        PageBean pageBean = iEnrollmentPlanService.pageEnrollmentPlan(page,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/comment/getcommentclass")
    public Result getcommentclass(@RequestBody Course cs,@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("获取课程评论");
        PageBean pageBean = iCommentService.getcomment(cs,page,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/course/getcourse")
    public Result getcourse(@RequestBody Course cs,@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("获取课程列表");
        log.info("分页查询, 参数: {},{}",page,pageSize);
        PageBean pageBean = iCourseService.getCourse(cs,page,pageSize);
        return Result.success(pageBean);
    }


}