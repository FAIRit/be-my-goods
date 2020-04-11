package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.album.track.TrackMapper;
import com.slyszmarta.bemygoods.album.track.TrackService;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationUserService userService;
    private final TrackService trackService;

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

    public AlbumDto saveAlbum(AlbumDto dto, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumToSave = AlbumMapper.INSTANCE.map(dto);
        albumToSave.setUser(user);
        var albumToSaveTracks = trackService.getAllAlbumTracks(albumToSave.getId()).stream().map(TrackMapper.INSTANCE::map).collect(Collectors.toList());
        albumToSave.setTracksList(albumToSaveTracks);
        albumRepository.save(albumToSave);
        return AlbumMapper.INSTANCE.map(albumToSave);
    }

    public void deleteUserAlbum(Long userId, Long albumId) {
        var albumToDelete = albumRepository.findById(albumId);
        if (!albumToDelete.get().getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        albumRepository.delete(albumToDelete.get());
    }

    public void deleteAllUserAlbum(Long userId) {
        albumRepository.deleteAllByUserId(userId);
    }

    public AlbumDto getExistingAlbumByUserIdAndAlbumId(Long userId, Long albumId) {
        var albumToFind = albumRepository.findById(albumId);
        if (!albumToFind.get().getId().equals(userId)){
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        return AlbumMapper.INSTANCE.map(albumToFind.get());
    }

    public AlbumDto getExistingAlbumByAlbumId(Long albumId) {
        return AlbumMapper.INSTANCE.map(albumRepository.findById(albumId).get());
    }
}
