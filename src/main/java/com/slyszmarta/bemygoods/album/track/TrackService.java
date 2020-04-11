package com.slyszmarta.bemygoods.album.track;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackService {

    private final TrackRepository trackRepository;


    public List<TrackDto> getAllAlbumTracks(Long albumId){
        return (trackRepository.findAllByAlbumId(albumId).stream()
                .map(TrackMapper.INSTANCE::map)
                .collect(Collectors.toList()));
    }

//    private Tracks saveAlbumTracks(Long albumId){
//        return new Tracks(trackRepository.findAllByAlbumId(albumId).stream()
//                .map(TrackMapper.INSTANCE::map)
//                .map(trackDto -> saveTrack(trackDto, albumId))
//                .collect(Collectors.toList()));
//    }
//
//    private TrackDto saveTrack(TrackDto trackDto, Long albumId){
//        var album = AlbumMapper.INSTANCE.map(albumService.getExistingAlbumByAlbumId(albumId));
//        var trackToSave = TrackMapper.INSTANCE.map(trackDto);
//        trackToSave.setAlbum(album);
//        trackRepository.save(trackToSave);
//        return TrackMapper.INSTANCE.map(trackToSave);
//    }

}
