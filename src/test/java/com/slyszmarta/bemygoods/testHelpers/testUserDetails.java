package com.slyszmarta.bemygoods.testHelpers;

import com.slyszmarta.bemygoods.security.user.ApplicationUserDetails;

public class testUserDetails {

    public static ApplicationUserDetails userDetails(){
        var user = testUser.user();
        ApplicationUserDetails userDetails = new ApplicationUserDetails(user);
        return userDetails;
    }
}
