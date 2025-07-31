package com.vnua.mapper;

import com.vnua.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    SysUser findByUsernameAndPassword(@Param("loginname") String loginname,
                                      @Param("password") String password);
    SysUser logFullName(@Param("loginname") String loginname);
    int updateLoginDate(@Param("loginname") String loginname);
}
