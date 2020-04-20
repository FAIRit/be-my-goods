package com.slyszmarta.bemygoods.album;

import com.google.gson.Gson;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.lastFmApi.response.Track;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AlbumDto saveAlbum(AlbumResponse albumResponse, Long userId) {
        var user = userService.getExistingUser(userId);
        var albumToSave = AlbumMapper.INSTANCE.mapResponseToAlbum(albumResponse);
        albumToSave = getAlbumTracksFromJson(albumResponse);
        albumToSave.setWiki(getWikiFromJson(albumResponse));
        user.addAlbum(albumToSave);
        albumRepository.save(albumToSave);
        return AlbumMapper.INSTANCE.mapAlbumToDto(albumToSave);
    }

    public void deleteUserAlbum(Long userId, Long albumId) {
        var albumToDelete = getAlbumByIdAndUserId(albumId, userId);
        var user = userService.getExistingUser(userId);
        albumRepository.delete(albumToDelete);
    }

    public void deleteAllUserAlbum(Long userId) {
        albumRepository.deleteAllByUserId(userId);
    }

    public AlbumDto getExistingAlbumByUserIdAndAlbumId(Long userId, Long albumId) {
        var albumToFind = getAlbumByIdAndUserId(albumId, userId);
        return AlbumMapper.INSTANCE.mapAlbumToDto(albumToFind);
    }

    public Album getAlbumByIdAndUserId(Long id, Long userId) {
        return albumRepository.findByIdAndUserId(id, userId);
    }

    private Album getAlbumTracksFromJson(AlbumResponse albumResponse){
        String jsonString = gson.toJson(albumResponse);
        AlbumResponse response = gson.fromJson(jsonString, AlbumResponse.class);
        List<Track> trackList = response.getTracks().getTrack();
        Album albumToSave = AlbumMapper.INSTANCE.mapResponseToAlbum(albumResponse);
        Iterator iterator = trackList.iterator();
        while (iterator.hasNext()) {
            com.slyszmarta.bemygoods.album.track.Track track = new com.slyszmarta.bemygoods.album.track.Track();
            StringBuilder builder = new StringBuilder();
            var trackValue = iterator.next().toString();
            builder.append(trackValue.substring(0, trackValue.length()-1).replace("Track(name=", ""));
            String trackName = builder.toString();
            track.setName(trackName);
            albumToSave.addTrack(track);
        }
        return albumToSave;
    }

    private String getWikiFromJson(AlbumResponse albumResponse) {
        String jsonString = gson.toJson(albumResponse);
        AlbumResponse response = gson.fromJson(jsonString, AlbumResponse.class);
        return response.getWiki().getSummary();
    }
}
