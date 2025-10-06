package com.vnua.mapper;

import com.vnua.model.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PatentMapper {
    List<Patent> getPatents();
}
