package com.slyszmarta.bemygoods.album;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlbumService {

    private final AlbumMapper albumMapper;
    private final AlbumRepository albumRepository;

    public List<AlbumDto> findAll(){
        return albumRepository.findAll().stream()
                .map(albumMapper::map)
                .collect(Collectors.toList());
    }

    public AlbumDto create(AlbumDto dto){
        Album entity = albumMapper.map(dto);
        Album savedEntity = albumRepository.save(entity);
        return albumMapper.map(savedEntity);
    }

    public AlbumDto update(Album album) {
        Album updatedAlbum = albumRepository.findById(album.getId())
                .orElseThrow(() -> new AlbumNotFoundException(album.getId()));
        albumRepository.save(updatedAlbum);
        return albumMapper.map(updatedAlbum);
    }

    public void delete(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        albumRepository.delete(album);
    }

}
