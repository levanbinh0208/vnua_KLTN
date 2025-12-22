package com.vnua.mapper;

import com.vnua.model.Publication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PublicationMapper {

    List<Publication> getAllPublications();

    void insertPublication(Publication publication);

    void updatePublication(Publication publication);

    void deletePublication(int id);

    Publication findById(int id);

    List<String> getAuthors();

    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

    List<Publication> findByStatus(int status);

    List<Publication> getByStatus(int status);
}
