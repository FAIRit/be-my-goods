package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.album.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackService {

    private final AlbumService albumService;
    private final TrackRepository trackRepository;

    public List<TrackDto> getAllAlbumTracks(Long albumId, Long userId){
        var albumToFind = albumService.getAlbumById(albumId);
        if (!albumToFind.getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to access this resource.");
        }
        return trackRepository.findAllByAlbumId(albumId).stream()
                .map(TrackMapper.INSTANCE::mapTrackToDto)
                .collect(Collectors.toList());
    }
}
