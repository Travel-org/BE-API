package com.ssu.travel.domain.photo.repository;


import com.ssu.travel.domain.photo.entity.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // 대부분 cascade로 삭제됨

    @Modifying(clearAutomatically = true)
    @Query("delete from Photo p where p.id in :ids")
    void deleteAllPhotosByIds(@Param("ids") List<Long> ids);
}
