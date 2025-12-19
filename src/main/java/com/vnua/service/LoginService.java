package com.vnua.service;

import com.vnua.mapper.LoginMapper;
import com.vnua.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public SysUser findByUsernameAndPassword(String login_name, String password) {
        return loginMapper.findByUsernameAndPassword(login_name, password);
    }

    public SysUser logFullName(String login_name) {
        return loginMapper.logFullName(login_name);
    }

    public void updateLoginDate(int user_id) {
        loginMapper.updateLoginDate(user_id);
    }

    public List<SysUser> showProfile(String name, String role, String delFlag,String email) {
        return loginMapper.showProfile(name, role, delFlag,email);
    }

    public int del(int user_id) {
        return loginMapper.del(user_id);
    }

    public SysUser findUserByLoginName(String loginName) {
        return loginMapper.findByLoginname(loginName);
    }

    public void updateProfile(SysUser user) {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            SysUser existing = loginMapper.findByLoginname(user.getLogin_name());
            if (existing != null) {
                user.setPassword(existing.getPassword());
            }
        }
        loginMapper.updateProfile(user);
    }

    public void insertUser(SysUser user) {
        loginMapper.insertUser(user);
    }
}