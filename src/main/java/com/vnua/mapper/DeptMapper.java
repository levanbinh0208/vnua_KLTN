package com.vnua.mapper;

import com.vnua.model.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {
    List<Dept> getAllDepts();
    void insertDept(String deptName);
    void updateDept(Dept dept);
    void deleteDept(int deptId);
}