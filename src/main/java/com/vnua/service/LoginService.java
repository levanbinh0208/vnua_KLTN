package com.vnua.service;

import com.vnua.mapper.LoginMapper;
import com.vnua.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public SysUser findByUsernameAndPassword(String loginname, String password) {
        return loginMapper.findByUsernameAndPassword(loginname, password);
    }
    public SysUser logFullName(String loginname) {
        return loginMapper.logFullName(loginname);
    }

    public SysUser updateLoginDate(String loginname) {
        return loginMapper.logFullName(loginname);
    }

}
