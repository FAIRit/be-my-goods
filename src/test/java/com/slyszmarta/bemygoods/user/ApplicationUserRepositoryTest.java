package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ApplicationUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        //given
        var faker = new Faker();
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .country(faker.country().name())
                .email("faker@faker.com")
                .albumList(Collections.emptyList())
                .build();
        entityManager.merge(user);
        entityManager.flush();
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByUsername(user.getUsername());
        // then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        //given
        var faker = new Faker();
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .country(faker.country().name())
                .email("faker@faker.com")
                .albumList(Collections.emptyList())
                .build();
        entityManager.merge(user);
        entityManager.flush();
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByEmail((user.getEmail()));
        // then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindById_thenReturnUser() {
        //given
        var faker = new Faker();
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .country(faker.country().name())
                .email("faker@faker.com")
                .albumList(Collections.emptyList())
                .build();
        entityManager.merge(user);
        entityManager.flush();
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserById((user.getId()));
        // then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue(){
        //given
        var faker = new Faker();
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .country(faker.country().name())
                .email("faker@faker.com")
                .albumList(Collections.emptyList())
                .build();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByEmail(user.getEmail());
        //then
        Assertions.assertTrue(found);
    }

    @Test
    public void whenExistsByUsername_thenReturnTrue(){
        //given
        var faker = new Faker();
        ApplicationUser user = ApplicationUser.builder()
                .id(faker.number().randomNumber())
                .username(faker.name().firstName())
                .password(faker.animal().name())
                .country(faker.country().name())
                .email("faker@faker.com")
                .albumList(Collections.emptyList())
                .build();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByUsername((user.getUsername()));
        //then
        Assertions.assertTrue(found);
    }

}
