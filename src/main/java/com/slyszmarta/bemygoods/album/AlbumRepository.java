package com.slyszmarta.bemygoods.album;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByUserId(Long id);

    void deleteAllByUserId(Long id);

}
