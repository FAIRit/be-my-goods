package com.slyszmarta.bemygoods.album;

import com.google.gson.Gson;
import com.slyszmarta.bemygoods.exceptions.AlbumNotFoundException;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.lastFmApi.response.Track;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationUserService userService;
    private final Gson gson;

    public Albums getAllUserAlbums(Long userId) {
        return new Albums(albumRepository.findAllByUserId(userId).stream()
                .map(AlbumMapper.INSTANCE::mapAlbumToDto)
                .collect(Collectors.toList()));
    }

    public Albums getAllTagAlbums(Long userId, String tag) {
        return new Albums(albumRepository.findByUserIdAndAlbumTags(userId, tag).stream()
                .map(AlbumMapper.INSTANCE::mapAlbumToDto)
                .collect(Collectors.toList()));
    }

    public AlbumDto saveAlbum(AlbumResponse albumResponse, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumToSave = AlbumMapper.INSTANCE.mapResponseToAlbum(albumResponse);
        albumToSave.setUser(user);
        var trackList = getAlbumTracksFromJson(albumResponse);
        albumToSave.setTracksList(trackList);
        albumToSave.setWiki(getWikiFromJson(albumResponse));
        albumRepository.save(albumToSave);
        var updatedTrackList = updateAlbumOnTrackList(albumToSave, trackList);
        albumToSave.setTracksList(updatedTrackList);
        albumRepository.save(albumToSave);
        return AlbumMapper.INSTANCE.mapAlbumToDto(albumToSave);
    }

    public void deleteUserAlbum(Long userId, Long albumId) {
        var albumToDelete = getAlbumById(albumId);
        if (!albumToDelete.getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        albumRepository.delete(albumToDelete);
    }

    public void deleteAllUserAlbum(Long userId) {
        albumRepository.deleteAllByUserId(userId);
    }

    public AlbumDto getExistingAlbumByUserIdAndAlbumId(Long userId, Long albumId) {
        var albumToFind = getAlbumById(albumId);
        if (!albumToFind.getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this resource.");
        }
        return AlbumMapper.INSTANCE.mapAlbumToDto(albumToFind);
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
    }

    private List<com.slyszmarta.bemygoods.album.track.Track> updateAlbumOnTrackList(Album album, List<com.slyszmarta.bemygoods.album.track.Track> trackList) {
        for (int i = 0; i < trackList.size(); i++) {
            var track = trackList.get(i);
            track.setAlbum(album);
        }
        return trackList;
    }

    private List<com.slyszmarta.bemygoods.album.track.Track> getAlbumTracksFromJson(AlbumResponse albumResponse) {
        String jsonString = gson.toJson(albumResponse);
        AlbumResponse response = gson.fromJson(jsonString, AlbumResponse.class);
        List<Track> trackList = response.getTracks().getTrack();
        List<com.slyszmarta.bemygoods.album.track.Track> resultList = new ArrayList<>();
        Iterator iterator = trackList.iterator();
        while (iterator.hasNext()) {
            com.slyszmarta.bemygoods.album.track.Track track = new com.slyszmarta.bemygoods.album.track.Track();
            track.setName(iterator.next().toString());
            resultList.add(track);
        }
        return resultList;
    }

    private String getWikiFromJson(AlbumResponse albumResponse) {
        String jsonString = gson.toJson(albumResponse);
        AlbumResponse response = gson.fromJson(jsonString, AlbumResponse.class);
        return response.getWiki().getSummary();
    }

}
