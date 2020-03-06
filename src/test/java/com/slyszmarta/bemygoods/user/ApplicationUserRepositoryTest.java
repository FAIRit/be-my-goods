package com.slyszmarta.bemygoods.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    // given
    public ApplicationUser given(){
        ApplicationUser alex = new ApplicationUser();
        alex.setId(1L);
        alex.setUsername("alex");
        alex.setPassword("password");
        alex.setCountry("country");
        alex.setEmail("alex@alex.com");
        alex.setAlbumList(Collections.emptyList());
        entityManager.merge(alex);
        entityManager.flush();

        return alex;
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByUsername(given().getUsername());

        // then
        assertThat(found.get().getUsername())
                .isEqualTo(given().getUsername());
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByEmail((given().getEmail()));

        // then
        assertThat(found.get().getUsername())
                .isEqualTo(given().getUsername());
    }

    @Test
    public void whenFindById_thenReturnUser() {

        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserById((given().getId()));

        // then
        assertThat(found.get().getUsername())
                .isEqualTo(given().getUsername());
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue(){

        //when
        Boolean found = applicationUserRepository.existsByEmail((given().getEmail()));


        //then
        Assert.assertTrue(found);
    }

    @Test
    public void whenExistsByUsername_thenReturnTrue(){

        //when
        Boolean found = applicationUserRepository.existsByUsername((given().getUsername()));


        //then
        Assert.assertTrue(found);
    }

}
