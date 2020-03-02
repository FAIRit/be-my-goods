package com.slyszmarta.bemygoods.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    @Override
    Optional<User> findById(Long id);
}
