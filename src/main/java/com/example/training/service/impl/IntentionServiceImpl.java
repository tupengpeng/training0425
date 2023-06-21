package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.training.entity.*;
import com.example.training.mapper.CourseMapper;
import com.example.training.mapper.IntentionMapper;
import com.example.training.service.IIntentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-05-16
 */
@Service
public class IntentionServiceImpl extends ServiceImpl<IntentionMapper, Intention> implements IIntentionService {

    @Autowired
    private IntentionMapper intentionMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public boolean insertInf(Customer cs, Course cou) {
        Intention intention = new Intention();
        intention.setCustomerId(cs.getId());
        intention.setCourseId(cou.getId());
        LocalDate currentDate = LocalDate.now();
        intention.setIntentionDate(currentDate);
        if(intentionMapper.insert(intention)==0){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteInf(Customer cs, Course cou) {
        UpdateWrapper<Intention> wrapper = new UpdateWrapper<>();
        wrapper.eq("customer_id",cs.getId());
        wrapper.eq("course_id",cou.getId());
        Intention intention = new Intention();
        intention.setIsDeleted(1);
        if(intentionMapper.update(intention,wrapper)==0){
            return false;
        }
        return true;
    }

    @Override
    public boolean changeInf(Customer cs, Course cou, String evaluation) {
        UpdateWrapper<Intention> wrapper = new UpdateWrapper<>();
        wrapper.eq("customer_id",cs.getId());
        wrapper.eq("course_id",cou.getId());
        Intention intention = new Intention();
        intention.setEvaluation(evaluation);
        if(intentionMapper.update(intention,wrapper)==0){
            return false;
        }
        return true;
    }

    @Override
    public PageBean getapplyclass(Customer cs, Integer page, Integer pageSize) {


        // 根据customerId查询intention表中的数据
        QueryWrapper<Intention> intentionWrapper = new QueryWrapper<>();
        intentionWrapper.eq("customer_id", cs.getId());
        List<Intention> intentionList = intentionMapper.selectList(intentionWrapper);

        // 构建courseId集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Intention intention : intentionList) {
            courseIdList.add(intention.getCourseId());
        }

        // 使用PageHelper进行分页
        PageHelper.startPage(page, pageSize);

        // 查询course表中对应的数据
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.in("id", courseIdList);
        List<Course> courseList = courseMapper.selectList(courseWrapper);
        Page<Course> pg = (Page<Course>) courseList;
        //封装PageBean对象
        PageBean pageBean = new PageBean(pg.getTotal(), pg.getResult());
        return pageBean;


    }

    @Override
    public String countclass() {
        // 查询intention表中不同course_id的出现次数
        QueryWrapper<Intention> intentionWrapper = new QueryWrapper<>();
        intentionWrapper.select("course_id, count(*) as count").groupBy("course_id");
        List<Map<String, Object>> statisticsList = intentionMapper.selectMaps(intentionWrapper);

        // 查询course表中的课程名称
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.select("id", "name");
        List<Course> courseList = courseMapper.selectList(courseWrapper);

        // 构建课程ID与课程名称的映射关系
        Map<Integer, String> courseMap = new HashMap<>();
        for (Course course : courseList) {
            courseMap.put(course.getId(), course.getName());
        }

        // 构建统计结果字符串
        StringBuilder result = new StringBuilder();
        for (Map<String, Object> statistics : statisticsList) {
            Integer courseId = (Integer) statistics.get("course_id");
            Integer count = ((Long) statistics.get("count")).intValue();

            String courseName = courseMap.get(courseId);
            if (courseName != null) {
                result.append("Course: ").append(courseName).append(", Count: ").append(count).append("\n");
            }
        }

        return result.toString();
    }

}
