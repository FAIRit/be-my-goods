package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.AlbumRepository;
import com.slyszmarta.bemygoods.album.AlbumService;
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
    private final AlbumService albumService;
    private final AlbumRepository albumRepository;

    public AlbumTags getAllUserAlbumTags (Long userId) {
        return new AlbumTags(albumTagRepository.findAllByUserId(userId).stream()
                .map(AlbumTagMapper.INSTANCE::mapAlbumTagToDto)
                .collect(Collectors.toList()));
    }

    public AlbumTagDto getAlbumTagById (Long userId, Long tagId) {
        var albumTag = getTagById(tagId);
        if (!albumTag.getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(albumTag);
    }

    public AlbumTagDto saveAlbumTagToAlbum (Long tagId, Long userId, Long albumId) {
        var tagToSave = getTagById(tagId);
        var albumToSave = albumService.getAlbumById(albumId);
        if (!albumToSave.getId().equals(userId) || !tagToSave.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        var tags = albumToSave.getAlbumTags();
        tags.add(tagToSave);
        albumToSave.setAlbumTags(tags);
        albumRepository.save(albumToSave);
        var albums = tagToSave.getAlbums();
        albums.add(albumToSave);
        tagToSave.setAlbums(albums);
        albumTagRepository.save(tagToSave);
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(tagToSave);
    }

    public AlbumTagDto saveAlbumTag (AlbumTagDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumTagToSave = AlbumTagMapper.INSTANCE.mapDtoToAlbumTag(dto);
        albumTagToSave.setUser(user);
        albumTagRepository.save(albumTagToSave);
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(albumTagToSave);
    }

    public void deleteUserAlbumTag (Long userId, Long tagId) {
        var albumTagToDelete = getTagById(tagId);
        if (!albumTagToDelete.getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        albumTagRepository.delete(albumTagToDelete);
    }

    public AlbumTag getTagById (Long id) {
        return albumTagRepository.findById(id).orElseThrow(() -> new AlbumTagNotFoundException(id));
    }
}
