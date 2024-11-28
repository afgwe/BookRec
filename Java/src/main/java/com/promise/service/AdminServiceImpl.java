package com.promise.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promise.dao.AdminDao;
import com.promise.dao.UserDao;
import com.promise.pojo.Admin;
import com.promise.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> {
    @Autowired
   AdminDao adminDao;

    public List<User> getByUsername(String username) {
        return adminDao.getByUsername(username);
    }

    public List<User> getDeleted() {
        return adminDao.getDeleted();
    }
}
