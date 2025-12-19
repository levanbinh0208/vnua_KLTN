package com.vnua.mapper;

import com.vnua.model.Publication;
import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;

=======
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9
import java.util.List;

@Mapper
public interface PublicationMapper {

    List<Publication> getAllPublications();

    void insertPublication(Publication publication);

    void updatePublication(Publication publication);

    void deletePublication(int id);

    Publication findById(int id);

    List<String> getAuthors();

<<<<<<< HEAD
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);

=======
    void updateStatus(int pubId, int status);
>>>>>>> 459e049d54c61ab01b731c30e82a712e98f6abf9

    List<Publication> findByStatus(int status);

    List<Publication> getByStatus(int status);
}
