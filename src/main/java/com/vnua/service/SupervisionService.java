package com.vnua.service;

import com.vnua.mapper.SupervisionMapper;
import com.vnua.model.Supervision;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisionService {
    private final SupervisionMapper supervisionMapper;

    public SupervisionService(SupervisionMapper supervisionMapper) {
        this.supervisionMapper = supervisionMapper;
    }

    public List<Supervision> getSupervisions() {
        return supervisionMapper.getSupervisions();
    }

    public void updateSupervision(Supervision sup) {
        supervisionMapper.updateSupervision(sup);
    }

    public Supervision getSupervisionById(int id) {
        return supervisionMapper.getSupervisionById(id);
    }

    public void deleteSupervision(int id) {
        supervisionMapper.deleteSupervision(id);
    }

    public void insertSupervision(Supervision sup) {
        supervisionMapper.insertSupervision(sup);
    }

}