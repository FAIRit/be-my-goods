package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.Album;
import com.slyszmarta.bemygoods.album.AlbumRepository;
import com.slyszmarta.bemygoods.exceptions.AlbumNotFoundException;
import com.slyszmarta.bemygoods.exceptions.AlbumTagNotFoundException;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumTagService {

    private final ApplicationUserService userService;
    private final AlbumTagRepository albumTagRepository;
    private final AlbumRepository albumRepository;


    public AlbumTags getAllUserAlbumTags(Long userId) {
        return new AlbumTags(albumTagRepository.findAllByUserId(userId).stream()
                .map(AlbumTagMapper.INSTANCE::map)
                .collect(Collectors.toList()));
    }

    public AlbumTagDto getAlbumTagById (Long userId, Long tagId){
        var albumTag = getAlbumTagById(tagId);
        if (!albumTag.getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        return AlbumTagMapper.INSTANCE.map(albumTag);
    }

    public AlbumTagDto saveAlbumTagToAlbum(Long tagId, Long userId, Long albumId){
       var tagToSave = getAlbumTagById(tagId);
       var albumToSave = getAlbumById(albumId);
       if (!albumToSave.getId().equals(userId) || !tagToSave.getId().equals(userId) ){
           throw new AccessDeniedException("You don't have permission to delete this resource.");
       }
       var albums = tagToSave.getAlbums();
       albums.add(albumToSave);
       tagToSave.setAlbums(albums);
       albumTagRepository.save(tagToSave);
       var tags = albumToSave.getAlbumTags();
       tags.add(tagToSave);
       albumToSave.setAlbumTags(tags);
       albumRepository.save(albumToSave);
       return AlbumTagMapper.INSTANCE.map(tagToSave);
    }

    public AlbumTagDto saveAlbumTag (AlbumTagDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumTagToSave = AlbumTagMapper.INSTANCE.map(dto);
        albumTagToSave.setUser(user);
        albumTagRepository.save(albumTagToSave);
        return AlbumTagMapper.INSTANCE.map(albumTagToSave);
    }

    public void deleteUserAlbumTag(Long userId, Long tagId) {
        var albumTagToDelete = getAlbumTagById(tagId);
        if (!albumTagToDelete.getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        albumTagRepository.delete(albumTagToDelete);
    }

    public AlbumTag getAlbumTagById(Long id){
        return albumTagRepository.findById(id).orElseThrow(() -> new AlbumTagNotFoundException(id));
    }

    public Album getAlbumById(Long id){
        return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
    }
}
