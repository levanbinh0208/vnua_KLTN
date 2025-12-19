package com.vnua.mapper;

import com.vnua.model.Conference;
import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;

=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
import java.util.List;

@Mapper
public interface ConferenceMapper {
    List<Conference> getConferences();

    Conference findById(int id);

    void insertConference(Conference conf);

    void updateConference(Conference conf);

    void deleteConference(int id);

    List<Conference> getByStatus(int status);

<<<<<<< HEAD
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

=======
    void updateStatus(int id, int status);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
}