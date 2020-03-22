package com.slyszmarta.bemygoods.security.registration.roles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);
}
