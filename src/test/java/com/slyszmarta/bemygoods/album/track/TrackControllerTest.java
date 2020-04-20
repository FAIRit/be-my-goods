package com.slyszmarta.bemygoods.album.track;

import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.slyszmarta.bemygoods.testHelpers.testTrackDto.dto;
import static com.slyszmarta.bemygoods.testHelpers.testUserDetails.userDetails;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackController trackController;

    @MockBean
    private TrackService trackService;

    @MockBean
    private ApplicationUserService userService;

    @MockBean
    private JwtToken jwtToken;

    @Test
    public void shouldReturnAllTracks() throws Exception {
        var userDetails = userDetails();
        List<TrackDto> trackDtos = new ArrayList<>();
        TrackDto dto = dto();
        trackDtos.add(dto);
        given(trackController.getAllAlbumTracks(1L, userDetails)).willReturn(ResponseEntity.ok(trackDtos));
        mockMvc.perform(get("/tracks/1")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(dto.getName())));
    }

}
