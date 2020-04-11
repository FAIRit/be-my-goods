package com.slyszmarta.bemygoods.security.user;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class ApplicationUserDetails extends User {

    private final ApplicationUser user;
    private Long id;

    public Long getId() {
        return id;
    }

    public ApplicationUserDetails(ApplicationUser user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) );
        id = user.getId();
        this.user = user;
    }
}
