package com.vnua.mapper;

import com.vnua.model.Publication;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PublicationMapper {

    List<Publication> getAllPublications();

    void insertPublication(Publication publication);

    void updatePublication(Publication publication);

    void deletePublication(int id);

    Publication findById(int id);

    List<String> getAuthors();
}
