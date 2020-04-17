package com.slyszmarta.bemygoods.user;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ApplicationUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUser given(){
        var faker = new Faker();
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

    @Test
    public void whenFindByUsername_thenReturnUser() {
        //given
        var user = given();
        entityManager.merge(user);
        entityManager.flush();
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByUsername(user.getUsername());
        // then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue(){
        //given
        var user = given();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByEmail(user.getEmail());
        //then
        assertTrue(found);
    }

    @Test
    public void whenExistsByUsername_thenReturnTrue(){
        //given
        var user = given();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByUsername((user.getUsername()));
        //then
        assertTrue(found);
    }

}