package com.vnua.service;

import com.vnua.mapper.LoginMapper;
import com.vnua.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public SysUser login(String email, String password) {
        return loginMapper.login(email, password);
    }
}
