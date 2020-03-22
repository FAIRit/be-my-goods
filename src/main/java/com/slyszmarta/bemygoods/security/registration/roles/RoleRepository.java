package com.slyszmarta.bemygoods.security.registration.roles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);

    @Override
    void delete(Role role);
}
