package com.slyszmarta.bemygoods.security.registration;

import com.slyszmarta.bemygoods.user.ApplicationUser;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private ApplicationUser user;

    public OnRegistrationCompleteEvent(ApplicationUser user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
