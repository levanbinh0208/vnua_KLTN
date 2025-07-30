package com.vnua.service;

import com.vnua.model.CanBo;
import com.vnua.repository.CanBoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanBoService {
    @Autowired
    private CanBoRepository canBoRepository;

    public CanBo save(CanBo canBo) {
        return canBoRepository.save(canBo);
    }

    public List<CanBo> findAll() {
        return canBoRepository.findAll();
    }

    public CanBo findById(Long id) {
        return canBoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        canBoRepository.deleteById(id);
    }
}