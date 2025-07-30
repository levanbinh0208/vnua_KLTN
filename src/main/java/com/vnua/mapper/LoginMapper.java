package com.vnua.mapper;

import com.vnua.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    SysUser login(String username, String password);
}
