package com.vnua.mapper;

import com.vnua.model.CanBo;
import java.util.List;

public interface CanBoMapper {
    List<CanBo> getAllCanBo();
    CanBo getCanBoById(Long id);
}
