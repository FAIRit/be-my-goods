package com.slyszmarta.bemygoods.album.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumTagRepository extends JpaRepository<AlbumTag, Long> {
    List<AlbumTag> findAllByUserId(Long id);
    AlbumTag findByIdAndUserId(Long id, Long userId);
}
