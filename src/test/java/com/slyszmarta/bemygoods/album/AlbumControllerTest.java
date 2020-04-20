package com.slyszmarta.bemygoods.album;

import com.google.gson.Gson;
import com.slyszmarta.bemygoods.lastFmApi.response.AlbumResponse;
import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.slyszmarta.bemygoods.testHelpers.testAlbumDto.dto;
import static com.slyszmarta.bemygoods.testHelpers.testAlbumDto.dtowithIdSpecified;
import static com.slyszmarta.bemygoods.testHelpers.response.testAlbumResponse.responseWithSpecifiedParams;
import static com.slyszmarta.bemygoods.testHelpers.testUserDetails.userDetails;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private AlbumController albumController;

    @MockBean
    private ApplicationUserService userService;

    @MockBean
    private JwtToken jwtToken;

    @Test
    public void shouldReturnAllUserAlbums() throws Exception {
        var userDetails = userDetails();
        List<AlbumDto> albumDtoList = new ArrayList<>();
        var dto = dto();
        albumDtoList.add(dto);
        var albums = new Albums(albumDtoList);
        given(albumController.getAllUsersAlbums(userDetails)).willReturn(ResponseEntity.ok(albums));
        mockMvc.perform(get("/albums")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albums[0].mbid", is(dto.getMbid())))
                .andExpect(jsonPath("$.albums[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.albums[0].artist", is(dto.getArtist())))
                .andExpect(jsonPath("$.albums[0].wiki", is(dto.getWiki())));
    }

    @Test
    public void shouldReturnAlbumOfSpecifiedId() throws Exception {
        var userDetails = userDetails();
        var dto = dtowithIdSpecified();
        given(albumController.getAlbumById(dto.getId(), userDetails)).willReturn(ResponseEntity.ok(dto));
        mockMvc.perform(get("/albums/1")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mbid", is(dto.getMbid())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.artist", is(dto.getArtist())))
                .andExpect(jsonPath("$.wiki", is(dto.getWiki())));
    }

    @Test
    public void shouldReturnAddedAlbum() throws Exception {
        var userDetails = userDetails();
        var response = responseWithSpecifiedParams();
        var dto = AlbumMapper.INSTANCE.mapAlbumToDto(AlbumMapper.INSTANCE.mapResponseToAlbum(response));
        Gson gson = new Gson();
        String jsonString = gson.toJson(response, AlbumResponse.class);
        given(albumController.addAlbum(response, userDetails)).willReturn(ResponseEntity.ok(dto));
        mockMvc.perform(post("/albums").contentType(MediaType.APPLICATION_JSON).content(jsonString)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mbid", is(dto.getMbid())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.artist", is(dto.getArtist())))
                .andExpect(jsonPath("$.wiki", is(dto.getWiki())));
    }

    @Test
    public void shouldDeleteAllAlbums() throws Exception {
        var userDetails = userDetails();
        doNothing().when(albumController).deleteAllUsersAlbums(userDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/albums")
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAlbumOfSpecifiedId() throws Exception {
        var userDetails = userDetails();
        var dto = dtowithIdSpecified();
        doNothing().when(albumController).deleteAlbum(userDetails, dto.getId());
        mockMvc.perform(MockMvcRequestBuilders.get("/albums/1")
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk());
    }
}
