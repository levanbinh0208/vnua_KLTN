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

    public SysUser findByUsernameAndPassword(String loginname, String password) {
        return loginMapper.findByUsernameAndPassword(loginname, password);
    }
    public SysUser logFullName(String loginname) {
        return loginMapper.logFullName(loginname);
    }

    public void updateLoginDate(int user_id) {
         loginMapper.updateLoginDate(user_id);
    }

    public List<SysUser> showProfile(String name, String dept, String role) {
        return loginMapper.showProfile(name, dept, role);
    }

    public int del(int user_id){
        return loginMapper.del(user_id);
    }

//    public SysUser updateProfile(String name, String dept, String role) {
//        return loginMapper.updateProfile(name, dept, role);
//    }


}
