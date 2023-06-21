package com.example.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.training.entity.Admin;
import com.example.training.entity.Customer;
import com.example.training.entity.Extensionworker;
import com.example.training.mapper.AdminMapper;
import com.example.training.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2023-06-05
 */
@Log4j2
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getadmintoLogin(Extensionworker cs) {

        if (Objects.isNull(cs)) {
            throw new IllegalArgumentException("Extensionworker is null");
        }

        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.eq("phone",cs.getPhone());
        qw.eq("password",cs.getPassword());
        List<Admin> cl = this.baseMapper.selectList(qw);
        log.info("Admin login query executed. Phone: {}, password: {}. Found {} matching admin(s).",
                cs.getPhone(), cs.getPassword(), cl.size());

        return cl;
    }


}
