package com.vnua.mapper;

import com.vnua.model.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatentMapper {
    List<Patent> getPatents();

    int insertPatent(Patent patent);

    void updatePatent(Patent patent);

    void deletePatent(int patentId);

    Patent findById(int id);

    List<Patent> getByStatus(int status);

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

}
