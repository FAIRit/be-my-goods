package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.AlbumRepository;
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

    public AlbumTagDto saveAlbumTagToAlbum(Long tagId, Long userId, Long albumId){
        var albumTag = albumTagRepository.findById(tagId);
        var albumToSave = albumRepository.findById(albumId);
        if (!albumTag.get().getId().equals(userId) || !albumToSave.get().getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        var albums = albumTag.get().getAlbums();
        albums.add(albumToSave.get());
        albumTag.get().setAlbums(albums);
        var tags = albumToSave.get().getAlbumTags();
        tags.add(albumTag.get());
        albumToSave.get().setAlbumTags(tags);
        albumRepository.save(albumToSave.get());
        return AlbumTagMapper.INSTANCE.map(albumTag.get());
    }

    public AlbumTagDto saveAlbumTag (AlbumTagDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumTagToSave = AlbumTagMapper.INSTANCE.map(dto);
        albumTagToSave.setUser(user);
        albumTagRepository.save(albumTagToSave);
        return AlbumTagMapper.INSTANCE.map(albumTagToSave);
    }

    public AlbumTagDto getExistingAlbumTagByUserIdAndTagId(Long userId, Long tagId) {
        var albumTagToFind = albumTagRepository.findById(tagId);
        if (!albumTagToFind.get().getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        return AlbumTagMapper.INSTANCE.map(albumTagToFind.get());
    }

    public void deleteUserAlbumTag(Long userId, Long tagId) {
        var albumTagToDelete = albumTagRepository.findById(tagId);
        if (!albumTagToDelete.get().getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        albumTagRepository.delete(albumTagToDelete.get());
    }
}
