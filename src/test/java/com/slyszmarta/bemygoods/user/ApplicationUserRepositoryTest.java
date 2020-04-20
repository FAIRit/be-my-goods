package com.slyszmarta.bemygoods.user;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.slyszmarta.bemygoods.testHelpers.testUser.user;
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

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres");

    @Test
    @Transactional
    public void whenFindByUsernameReturnUser() {
        //given
        var user = user();
        entityManager.merge(user);
        entityManager.flush();
        // when
        Optional<ApplicationUser> found = applicationUserRepository.findUserByUsername(user.getUsername());
        // then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @Transactional
    public void whenExistsByEmailReturnTrue(){
        //given
        var user = user();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByEmail(user.getEmail());
        //then
        assertTrue(found);
    }

    @Test
    @Transactional
    public void whenExistsByUsernameReturnTrue(){
        //given
        var user = user();
        entityManager.merge(user);
        entityManager.flush();
        //when
        Boolean found = applicationUserRepository.existsByUsername((user.getUsername()));
        //then
        assertTrue(found);
    }

}
