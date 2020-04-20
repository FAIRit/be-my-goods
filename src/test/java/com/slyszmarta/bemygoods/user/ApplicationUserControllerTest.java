package com.slyszmarta.bemygoods.user;

import com.slyszmarta.bemygoods.security.jwt.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.slyszmarta.bemygoods.testHelpers.testUserDetails.userDetails;
import static org.hamcrest.Matchers.is;
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

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        var userDetails = userDetails();
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
        var userDetails = userDetails();

        doNothing().when(userController).deleteUser(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/myprofile")
                .with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk());
    }

}
