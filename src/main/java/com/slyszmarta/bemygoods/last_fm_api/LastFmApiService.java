package com.slyszmarta.bemygoods.last_fm_api;

import com.slyszmarta.bemygoods.last_fm_api.response.AlbumResponse;
import com.slyszmarta.bemygoods.last_fm_api.response.AlbumSearchResultWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LastFmApiService{
    @Value("${lastfm_api_url}")
    private String baseApiUrl;

    @Value("${lastfm_api_key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LastFmApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AlbumResponse> searchAlbums(String artist, String title) {
        String fullPath = getFullPath(artist, title);
        var entity = restTemplate.getForEntity(fullPath, AlbumSearchResultWrapper.class);
        return entity.getBody() != null ? (mapResponseEntitytoAlbumDtoList(entity.getBody())) : (Collections.emptyList());
    }

    public List<AlbumResponse> searchAlbums(String musicbrainzId) {
        String fullPath = getFullPath(musicbrainzId);
        var entity = restTemplate.getForEntity(fullPath, AlbumSearchResultWrapper.class);
        return entity.getBody() != null ? (mapResponseEntitytoAlbumDtoList(entity.getBody())) : (Collections.emptyList());
    }

    private String getFullPath(String artist, String title) {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(baseApiUrl);
        fullPath.append("/2.0/?method=album.getinfo&api_key=");
        fullPath.append(apiKey);
        fullPath.append("&artist=");
        fullPath.append(artist.replace(" ", "%20"));
        fullPath.append("&album=");
        fullPath.append(title.replace(" ", "%20"));
        fullPath.append("&format=json");
        return fullPath.toString();
    }

    private String getFullPath(String musicbrainzId) {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(baseApiUrl);
        fullPath.append("/2.0/?method=album.getinfo&api_key=");
        fullPath.append(apiKey);
        fullPath.append("&mbid=");
        fullPath.append(musicbrainzId);
        fullPath.append("&format=json");
        return fullPath.toString();
    }

    private AlbumResponse[] convertAlbumListToArray(List<AlbumResponse> list) {
        AlbumResponse[] albumResponses = new AlbumResponse[list.size()];
        list.toArray(albumResponses);
        return albumResponses;
    }

    private List<AlbumResponse> mapResponseEntitytoAlbumDtoList(AlbumSearchResultWrapper wrapper) {
        return Arrays.stream(convertAlbumListToArray(wrapper.getAlbumResponse()))
                .collect(Collectors.toList());
    }
}
