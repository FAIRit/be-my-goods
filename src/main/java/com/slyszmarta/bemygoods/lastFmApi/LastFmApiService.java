package com.slyszmarta.bemygoods.lastFmApi;

import com.slyszmarta.bemygoods.album.AlbumDto;
import com.slyszmarta.bemygoods.album.AlbumMapper;
import com.slyszmarta.bemygoods.album.Albums;
import com.slyszmarta.bemygoods.lastFmApi.response.Album;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumSearchResultWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LastFmApiService implements IAlbumService {
    @Value("${LASTFM_API_URL}")
    private String baseApiUrl;

    @Value("${LASTFM_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LastFmApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Albums searchAlbums(String artist, String title){
        String fullPath = getFullPath(artist, title);
        var entity = restTemplate.getForEntity(fullPath, AlbumSearchResultWrapper.class);
        return entity.getBody() != null ? new Albums(mapResponseEntitytoAlbumDtoList(entity.getBody())) : new Albums(Collections.emptyList());
    }

    public Albums searchAlbums(String musicbrainzId){
        String fullPath = getFullPath(musicbrainzId);
        var entity = restTemplate.getForEntity(fullPath, AlbumSearchResultWrapper.class);
        return entity.getBody() != null ? new Albums(mapResponseEntitytoAlbumDtoList(entity.getBody())) : new Albums(Collections.emptyList());
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

    private String getFullPath(String musicbrainzId){
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(baseApiUrl);
        fullPath.append("/2.0/?method=album.getinfo&api_key=");
        fullPath.append(apiKey);
        fullPath.append("&mbid=");
        fullPath.append(musicbrainzId);
        fullPath.append("&format=json");
        return fullPath.toString();
    }

    private Album[] convertAlbumListToArray(List<Album> list){
        Album[] albums = new Album[list.size()];
        list.toArray(albums);
        return albums;
    }

    private List<AlbumDto> mapResponseEntitytoAlbumDtoList(AlbumSearchResultWrapper wrapper){
        return Arrays.stream(convertAlbumListToArray(wrapper.getAlbum()))
                .map(AlbumMapper.INSTANCE::map)
                .collect(Collectors.toList());
    }
}
