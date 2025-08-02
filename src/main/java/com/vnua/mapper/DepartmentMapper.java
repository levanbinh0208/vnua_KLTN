package com.vnua.mapper;

import com.vnua.model.Department;
import java.util.List;

public interface DepartmentMapper {
    List<Department> findAll();
    Department findById(int id);
    int insert(Department department);
    int update(Department department);
    int delete(int id);
}
