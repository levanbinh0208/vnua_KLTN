package com.vnua.mapper;

import com.vnua.model.Supervision;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

import java.util.List;

public interface SupervisionMapper {
    List<Supervision> getSupervisions();

    void insertSupervision(Supervision sup);

    Supervision getSupervisionById(int id);

    void updateSupervision(Supervision sup);

    void deleteSupervision(int id);

<<<<<<< HEAD
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

=======
    void updateStatus(int id, int status);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
}
