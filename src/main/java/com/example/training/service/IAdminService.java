package com.example.training.service;

import com.example.training.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.training.entity.Extensionworker;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2023-06-05
 */
public interface IAdminService extends IService<Admin> {


    public List<Admin> getadmintoLogin(Extensionworker cs);
}
