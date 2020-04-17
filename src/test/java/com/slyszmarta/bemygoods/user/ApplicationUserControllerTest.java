package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ApplicationUserController.class)
public class ApplicationUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserService userService;

    @MockBean
    private ApplicationUserController userController;

    @MockBean
    private JwtToken jwtToken;

    Faker faker = new Faker();

    public ApplicationUser user(){
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .email(faker.internet().emailAddress())
                .country(faker.country().name())
                .albumList(Collections.emptyList())
                .albumTags(Collections.emptySet())
                .build();
        return user;
    }

    public ApplicationUserDetails userDetails(){
        var user = user();
        ApplicationUserDetails userDetails = new ApplicationUserDetails(user);
        return userDetails;
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        ApplicationUserDetails userDetails = userDetails();
        var dto = ApplicationUserMapper.INSTANCE.map(userDetails);

        given(userController.getCurrentUser(userDetails)).willReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/myprofile")
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(userDetails.getUsername())))
                .andExpect(jsonPath("$.password", is(userDetails.getPassword())))
                .andExpect(jsonPath("$.email", is(userDetails.getEmail())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        ApplicationUserDetails userDetails = userDetails();

        doNothing().when(userController).deleteUser(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/myprofile")
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk());

    }

}
