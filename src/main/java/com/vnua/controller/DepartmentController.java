package com.vnua.controller;

import com.vnua.mapper.DepartmentMapper;
import com.vnua.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentMapper.findAll();
    }

    @PostMapping
    public String createDepartment(@RequestBody Department department) {
        departmentMapper.insert(department);
        return "Thêm phòng ban thành công!";
    }

    @PutMapping("/{id}")
    public String updateDepartment(@PathVariable int id, @RequestBody Department department) {
        department.setId(id);
        departmentMapper.update(department);
        return "Cập nhật thành công!";
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable int id) {
        departmentMapper.delete(id);
        return "Xóa thành công!";
    }
}
