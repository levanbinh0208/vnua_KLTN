package com.vnua.mapper;

import com.vnua.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {
    SysUser findByUsernameAndPassword(@Param("loginname") String loginname,
                                      @Param("password") String password);
    SysUser logFullName(@Param("loginname") String loginname);
    int updateLoginDate(@Param("loginname") String loginname);
    List<SysUser> showProfile(@Param("name") String name,
                              @Param("dept") String dept,
                              @Param("role") String role);

}
