package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.album.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AlbumService albumService;

    public List<TrackDto> getAllAlbumTracks(Long albumId, Long userId) throws AccessDeniedException {
        var albumToFind = albumService.getAlbumByIdAndUserId(albumId, userId);
        if (!albumToFind.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to access this resource.");
        }
        return trackRepository.findAllByAlbumId(albumId).stream()
                .map(TrackMapper.INSTANCE::mapTrackToDto)
                .collect(Collectors.toList());
    }
}
