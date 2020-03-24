package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.exceptions.AlbumNotFoundException;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationUserService userService;

    public Albums getAllUserAlbums(Long userId) {
        return new Albums(albumRepository.findAllByUserId(userId).stream()
                .map(AlbumMapper.INSTANCE::map)
                .collect(Collectors.toList()));
    }

    public Albums getAllTagAlbums(Long userId, String tag){
        return new Albums(albumRepository.findByUserIdAndAlbumTags(userId, tag).stream()
                .map(AlbumMapper.INSTANCE::map)
                .collect(Collectors.toList()));
    }

    public Optional<AlbumDto> getUserAlbum(Long userId, Long albumId) {
        var albumToFind = getExistingAlbumById(albumId);
        return Optional.ofNullable(AlbumMapper.INSTANCE.map(albumToFind));
    }

    public Album saveAlbum(AlbumDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumToSave = AlbumMapper.INSTANCE.map(dto);
        albumToSave.setUser(user);
        albumRepository.save(albumToSave);
        return albumToSave;
    }

    public void deleteUsersAlbum(Long userId, Long albumId) {
        var albumToFind = getExistingAlbumById(albumId);
        if (albumToFind.getUser().getId().equals(userId)) {
            albumRepository.deleteById(albumId);
        } else {
            throw new AlbumNotFoundException(albumId);
        }
    }

    public void deleteAllUsersAlbum(Long usersId) {
        albumRepository.deleteAllByUserId(usersId);
    }

    public Album getExistingAlbumById(Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));
    }
}
