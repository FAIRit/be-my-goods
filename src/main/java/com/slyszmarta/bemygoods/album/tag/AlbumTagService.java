package com.slyszmarta.bemygoods.album.tag;

import com.slyszmarta.bemygoods.album.AlbumDto;
import com.slyszmarta.bemygoods.album.AlbumMapper;
import com.slyszmarta.bemygoods.album.AlbumService;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumTagService {

    private final ApplicationUserService userService;
    private final AlbumTagRepository albumTagRepository;
    private final AlbumService albumService;

    public AlbumTags getAllUserAlbumTags (Long userId) {
        return new AlbumTags(albumTagRepository.findAllByUserId(userId).stream()
                .map(AlbumTagMapper.INSTANCE::mapAlbumTagToDto)
                .collect(Collectors.toList()));
    }

    public AlbumTagDto getAlbumTagById (Long userId, Long tagId) {
        var albumTag = albumTagRepository.findByIdAndUserId(tagId, userId);
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(albumTag);
    }

    public AlbumTagDto saveAlbumTagToAlbum (Long tagId, Long userId, Long albumId) {
        var tagToSave = albumTagRepository.findByIdAndUserId(tagId, userId);
        var albumToSave = albumService.getAlbumByIdAndUserId(albumId, userId);
        albumToSave.addAlbumTag(tagToSave);
        albumTagRepository.save(tagToSave);
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(tagToSave);
    }

    public Set<AlbumDto> getAllTagAlbums(Long userId, Long tagId) {
        return albumTagRepository.findByIdAndUserId(tagId, userId).getAlbums().stream()
                .map(AlbumMapper.INSTANCE::mapAlbumToDto)
                .collect(Collectors.toSet());
    }

    public AlbumTagDto saveAlbumTag (AlbumTagDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumTagToSave = AlbumTagMapper.INSTANCE.mapDtoToAlbumTag(dto);
        user.addTag(albumTagToSave);
        albumTagRepository.save(albumTagToSave);
        return AlbumTagMapper.INSTANCE.mapAlbumTagToDto(albumTagToSave);
    }

    public void deleteUserAlbumTag (Long userId, Long tagId) {
        var albumTagToDelete = albumTagRepository.findByIdAndUserId(tagId, userId);
        var user = userService.getExistingUser(userId);
        user.removeTag(albumTagToDelete);
        albumTagRepository.delete(albumTagToDelete);
    }
}
