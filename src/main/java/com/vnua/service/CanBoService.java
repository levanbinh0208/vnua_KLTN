package com.vnua.service;

import com.vnua.model.CanBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vnua.mapper.CanBoMapper;
import java.util.List;

@Service
public class CanBoService {

    @Autowired
    private CanBoMapper canBoMapper;

    public List<CanBo> getAllCanBo() {
        return canBoMapper.getAllCanBo();
    }

    public CanBo getById(Long id) {
        return canBoMapper.getCanBoById(id);
    }

    // Nếu dùng MyBatis thì có thể viết thêm hàm insert/delete/update tại XML
}
