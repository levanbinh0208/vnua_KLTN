package com.vnua.mapper;

import com.vnua.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface LoginMapper {
    SysUser findByUsernameAndPassword(@Param("login_name") String login_name,
                                      @Param("password") String password);
    SysUser logFullName(@Param("login_name") String login_name);
    void updateLoginDate( int id);
    List<SysUser> showProfile(@Param("name") String name,
                              @Param("role") String role,
                              @Param("delFlag") String delFlag,
                              @Param("email") String email);
    int del(@Param("user_id") int user_id);
    SysUser editProfile(@Param("id") String id);
    int updateProfile(@Param("id") String id,
                      @Param("name") String name,
                      @Param("email") String email,
                      @Param("role") String role);
    int deleteProfile(@Param("id") String id);
    int addProfile(@Param("name") String name,
                   @Param("email") String email,
                   @Param("role") String role);
    int updatePassword(@Param("id") String id,
                       @Param("password") String password);

    SysUser findByLoginname(String login_name);

    void insertUser(SysUser user);

    void updateProfile(SysUser user);
}
