package com.slyszmarta.bemygoods.album;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;


    public List<AlbumDto> findAll(){
        return albumRepository.findAll().stream()
                .map(AlbumMapper.INSTANCE::map)
                .collect(Collectors.toList());
    }

    public AlbumDto create(AlbumDto dto){
        Album entity = AlbumMapper.INSTANCE.map(dto);
        Album savedEntity = albumRepository.save(entity);
        return AlbumMapper.INSTANCE.map(savedEntity);
    }

    public AlbumDto update(Album album) {
        Album updatedAlbum = albumRepository.findById(album.getId())
                .orElseThrow(() -> new AlbumNotFoundException(album.getId()));
        albumRepository.save(updatedAlbum);
        return AlbumMapper.INSTANCE.map(updatedAlbum);
    }

    public void delete(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        albumRepository.delete(album);
    }

}
