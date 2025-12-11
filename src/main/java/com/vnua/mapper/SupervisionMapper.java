package com.vnua.mapper;

import com.vnua.model.Supervision;

import java.util.List;

public interface SupervisionMapper {
    List<Supervision> getSupervisions();

    void insertSupervision(Supervision sup);

    Supervision getSupervisionById(int id);

    void updateSupervision(Supervision sup);

    void deleteSupervision(int id);
}
