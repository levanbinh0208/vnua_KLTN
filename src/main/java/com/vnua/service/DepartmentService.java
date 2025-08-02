package com.vnua.service;

import com.vnua.mapper.DepartmentMapper;
import com.vnua.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    List<Department> findAll() {return departmentMapper.findAll();};
    Department findById(int id){return departmentMapper.findById(id);};
    int insert(Department department){return departmentMapper.insert(department);};
    int update(Department department){return departmentMapper.update(department);};
    int delete(int id){return departmentMapper.delete(id);};
}
