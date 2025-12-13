package com.vnua.mapper;

import com.vnua.model.Conference;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ConferenceMapper {
    List<Conference> getConferences();

    Conference findById(int id);

    void insertConference(Conference conf);

    void updateConference(Conference conf);

    void deleteConference(int id);

    List<Conference> getByStatus(int status);

    void updateStatus(int id, int status);
}