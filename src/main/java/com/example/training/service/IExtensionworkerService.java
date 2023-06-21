package com.example.training.service;

import com.example.training.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-06-05
 */
public interface IExtensionworkerService extends IService<Extensionworker> {


    public List<Extensionworker> getworkertoLogin(Extensionworker cs);

    public boolean share(Extensionworker ex, EnrollmentPlan en);


    public PageBean getIntentionByCustomerName(String name, Integer page, Integer pageSize);

    public PageBean findExtensionWorkerByCondition(Extensionworker cs, Integer page, Integer pageSize);

    public boolean addWorker(Extensionworker worker);

    public boolean deleteWorker(Extensionworker worker);

    public boolean updateWorker(Extensionworker worker);
}
