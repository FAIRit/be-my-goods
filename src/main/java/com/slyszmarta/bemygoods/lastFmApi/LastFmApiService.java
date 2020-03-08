package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.AlbumDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class LastFmApiService {
    @Value("${app.last.fm.api-url}")
    private String baseApiUrl;

    @Value("${api.last.fm.api-url}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LastFmApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AlbumDto> searchAlbums(final String artist, final String title){
        String fullPath = getFullPath(artist, title);

        ResponseEntity<AlbumDto> apiResponse = restTemplate.getForEntity(fullPath, AlbumDto.class);

        return apiResponse.getBody() != null ? Arrays.asList(apiResponse.getBody()) :
                Collections.emptyList();
    }


    private String getFullPath(final String artist, final String title){
        StringBuffer fullPath = new StringBuffer();
        fullPath.append(baseApiUrl);
        fullPath.append(" /2.0/?method=album.getinfo&api_key=");
        fullPath.append(apiKey);
        fullPath.append("&artist=");
        fullPath.append(artist.replaceAll(" ", "%20"));
        fullPath.append("&album=");
        fullPath.append(title.replaceAll(" ", "%20"));
        fullPath.append("&format=json");
        return fullPath.toString();
    }
}
