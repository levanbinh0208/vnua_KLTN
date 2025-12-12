package com.vnua.service;

import com.vnua.mapper.DeptMapper;
import com.vnua.model.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private DeptMapper deptMapper;

    public List<Dept> getAllDepts() {
        return deptMapper.getAllDepts();
    }

    public void insertDept(String deptName) {
        if (deptName != null && !deptName.trim().isEmpty()) {
            deptMapper.insertDept(deptName.trim());
        }
    }

    public void updateDept(int deptId, String deptName) {
        Dept dept = new Dept();
        dept.setDeptId(deptId);
        dept.setDeptName(deptName.trim());
        deptMapper.updateDept(dept);
    }

    public void deleteDept(int deptId) {
        deptMapper.deleteDept(deptId);
    }
}