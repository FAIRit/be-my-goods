package com.slyszmarta.bemygoods.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByUserId(Long id);
    void deleteAllByUserId(Long id);
    Album findByIdAndUserId (Long id, Long userId);
}
