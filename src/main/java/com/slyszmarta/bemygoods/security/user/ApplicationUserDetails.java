package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class ApplicationUserDetails extends User {

    private Long id;

    public String getEmail() {
        return email;
    }

    private String email;

    public Long getId() {
        return id;
    }

    public ApplicationUserDetails(ApplicationUser user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        id = user.getId();
        email = user.getEmail();
    }

    @Override
    public boolean equals(Object rhs) {
        return super.equals(rhs);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
