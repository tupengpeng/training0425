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
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IIntentionService iIntentionService;
    @Autowired
    private ICommentService iCommentService;

    //test
    @GetMapping("/hello")
    public String hello(){
        return "hello !";
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody Customer cs){
        log.info("Cus注册",cs);
        if(iCustomerService.insertInf(cs)){
            return Result.success();
        }
        return Result.error("账号已存在");
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Customer cs){
        log.info("Cus登录",cs);
        //进行身份验证
        List<Customer> listc =iCustomerService.getCustomtoLogin(cs);

        //登录成功,生成令牌并下发令牌
        if (!listc.isEmpty()){
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
    public Result loginout(){
        return Result.success();
    }

    //对自己信息进行查询
    @PostMapping("/query")
    public Result query(@RequestBody Customer cs){
        log.info("Cus信息查询",cs);
        if(iCustomerService.query(cs)!=null){
            return Result.success(iCustomerService.query(cs));
        }
        //查询失败, 返回错误信息
        return Result.error("Cus信息查询失败");
    }

    //更新自己的信息
    @PostMapping("/updateInf")
    public Result updateInf(@RequestBody Customer cs){
        log.info("Cus信息修改",cs);
        if(iCustomerService.updateInf(cs)){
            return Result.success();
        }
        //修改失败, 返回错误信息
        return Result.error("Cus信息修改失败");
    }

    //申请上课
    @PostMapping("/applyforclasses")
    public Result applyforclasses(@RequestBody Customer cs,@RequestBody Course cou){
        log.info("Cus申请上课",cs);
        if (iIntentionService.insertInf(cs,cou)){
            return Result.success();
        }
        //Cus申请上课失败, 返回错误信息
        return Result.error("Cus申请上课失败");
    }

    //删除试听记录
    @PostMapping("/deleteclasses")
    public Result deleteclasses(@RequestBody Customer cs,@RequestBody Course cou){
        log.info("Cus删除记录",cs);
        if (iIntentionService.deleteInf(cs,cou)){
            return Result.success();
        }
        //Cus删除试听记录失败, 返回错误信息
        return Result.error("Cus删除试听记录失败");
    }

    //给课程评分
    @PostMapping("/gradeclasses")
    public Result gradeclasses(@RequestBody Customer cs,@RequestBody Course cou,String evaluation){
        log.info("Cus给课程评分",cs);
        if (iIntentionService.changeInf(cs,cou,evaluation)){
            return Result.success();
        }
        //Cus给课程评分失败, 返回错误信息
        return Result.error("Cus给课程评分失败");
    }

    //给课程评论
    @PostMapping("/commentclass")
    public Result commentclass(@RequestBody Intention it, String comment){
        log.info("Cus给课程评论",comment);
        if (iCommentService.comment(it,comment)){
            return Result.success();
        }
        //Cus给课程评论失败, 返回错误信息
        return Result.error("Cus给课程评论失败");
    }


    //查询自己申请的课程
    @PostMapping("/isapplyforclasses")
    public Result commentclass(@RequestBody Customer cs ,@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("查询自己申请的课程",cs.getId());
        PageBean pageBean = iIntentionService.getapplyclass(cs,page,pageSize);
        return Result.success(pageBean);
    }



}
