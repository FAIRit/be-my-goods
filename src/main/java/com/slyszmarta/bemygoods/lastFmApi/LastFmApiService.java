package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.Albums;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class LastFmApiService implements AlbumService {
    @Value("${last^fm.api-url}")
    private String baseApiUrl;

    @Value("${last^fm.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LastFmApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Albums searchAlbums(String artist, String title){
        String fullPath = getFullPath(artist, title);
        ResponseEntity<Albums> entity = restTemplate.getForEntity(fullPath, Albums.class);
        return entity.getBody() != null ? new Albums(entity.getBody().getAlbums()) : new Albums(Collections.emptyList());
    }

    private String getFullPath(String artist, String title) {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(baseApiUrl);
        fullPath.append("/2.0/?method=album.getinfo&api_key=");
        fullPath.append(apiKey);
        fullPath.append("&artist=");
        fullPath.append(artist.replaceAll(" ", "%20"));
        fullPath.append("&album=");
        fullPath.append(title.replaceAll(" ", "%20"));
        fullPath.append("&format=json");
        return fullPath.toString();
    }
}
