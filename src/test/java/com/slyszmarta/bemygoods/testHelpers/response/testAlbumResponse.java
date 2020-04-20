package com.slyszmarta.bemygoods.testHelpers.response;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.lastFmApi.response.Tracks;
import com.slyszmarta.bemygoods.lastFmApi.response.Wiki;

import static com.slyszmarta.bemygoods.testHelpers.response.testTrackList.trackList;

public class testAlbumResponse {

    static Faker faker = new Faker();

    public static AlbumResponse albumResponse(){
        Tracks tracks = new Tracks();
        tracks.setTrack(trackList());
        Wiki wiki = new Wiki();
        wiki.setSummary(faker.rickAndMorty().quote());
        AlbumResponse response = AlbumResponse.builder()
                .mbid(faker.aviation().airport())
                .name(faker.name().firstName())
                .artist(faker.artist().name())
                .tracks(tracks)
                .wiki(wiki)
                .build();
        return response;
    }

    public static AlbumResponse responseWithSpecifiedParams(){
        Tracks tracks = new Tracks();
        tracks.setTrack(testTracks.track());
        Wiki wiki = new Wiki();
        wiki.setSummary("Metallica (also known as The Black Album) is the fifth studio album by the American heavy metal band Metallica, released August 12, 1991 through Elektra Records. It features some of Metallica's most popular songs, \"Enter Sandman\", \"The Unforgiven\", \"Nothing Else Matters\" , \"Wherever I May Roam\" and \"Sad but True\". It spent four consecutive weeks at number one on Billboard 200. Metallica is the band's best-selling album to date, with over 16 million physical copies sold in the United States and over 31 million copies worldwide. <a href=\"http://www.last.fm/music/Metallica/Metallica\">Read more on Last.fm</a>.");
        AlbumResponse response = AlbumResponse.builder()
                .mbid("6e729716-c0eb-3f50-a740-96ac173be50d")
                .name("Metallica")
                .artist("Metallica")
                .tracks(tracks)
                .wiki(wiki)
                .build();
        return response;
    }
}
