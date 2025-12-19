package com.vnua.mapper;

import com.vnua.model.Supervision;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionMapper {
    List<Supervision> getSupervisions();

    void insertSupervision(Supervision sup);

    Supervision getSupervisionById(int id);

    void updateSupervision(Supervision sup);

    void deleteSupervision(int id);

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

}
