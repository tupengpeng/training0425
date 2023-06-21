package com.example.training.controller;

import com.example.training.entity.*;
import com.example.training.service.ICommentService;
import com.example.training.service.ICourseService;
import com.example.training.service.IEnrollmentPlanService;
import com.example.training.service.IPromotionArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//针对部分service实现层抛出异常使用
//catch (IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + ex.getMessage());
//        }
public class AlluserController {

    @Autowired
    private IEnrollmentPlanService iEnrollmentPlanService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private IPromotionArticleService iPromotionArticleService;

    // 分页查询招生计划
    @GetMapping("/enrollmentPlan/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询, 参数: {},{}", page, pageSize);
        PageBean pageBean = iEnrollmentPlanService.pageEnrollmentPlan(page, pageSize);
        return pageBean != null ? Result.success(pageBean) : Result.error("分页查询招生计划失败");
    }

    // 获取课程列表
    @GetMapping("/course/getcourse")
    public Result getcourse(@RequestBody Course cs,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取课程列表");
        log.info("课程分页查询, 参数: {},{}", page, pageSize);
        PageBean pageBean = iCourseService.getCourse(cs, page, pageSize);
        return pageBean != null ? Result.success(pageBean) : Result.error("获取课程列表失败");
    }

    // 获取课程评论
    @GetMapping("/comment/getcommentclass")
    public Result getcommentclass(@RequestBody Course cs,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取课程评论");
        log.info("分页查询, 参数: {},{}", page, pageSize);
        PageBean pageBean = iCommentService.getcomment(cs, page, pageSize);
        return pageBean != null ? Result.success(pageBean) : Result.error("获取课程评论失败");
    }

    // 获取简章
    @PostMapping("/promotionArticle/getpromotionArticlepage")
    public Result pagePromotionArticle(@RequestBody PromotionArticle promotionArticle,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageBean pageBean = iPromotionArticleService.pagePromotionArticle(promotionArticle, page, pageSize);
        return pageBean != null ? Result.success(pageBean) : Result.error("获取简章失败");
    }
}
