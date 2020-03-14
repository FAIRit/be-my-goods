package com.slyszmarta.bemygoods.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    List<ApplicationUser> findAll();

    Optional<ApplicationUser> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<ApplicationUser> findUserByUsername(String username);

    Optional<ApplicationUser> findUserById(Long id);

}
