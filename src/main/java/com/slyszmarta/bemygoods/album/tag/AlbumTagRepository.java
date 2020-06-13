package com.slyszmarta.bemygoods.album.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumTagRepository extends JpaRepository<AlbumTag, Long> {
    List<AlbumTag> findAllByUserId(Long id);
    AlbumTag findByIdAndUserId(Long id, Long userId);
}
