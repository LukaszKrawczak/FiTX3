package com.brus5.lukaszkrawczak.fitx;

import com.facebook.FacebookSdk;

import org.junit.Test;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by lukaszkrawczak on 05.03.2018.
 */
public class UserLoginActivityTest {
    @Test
    public void onCreate() throws Exception {
        FacebookSdk.sdkInitialize(getApplicationContext());
        com.facebook.Profile.getCurrentProfile().getId();
    }

    @Test
    public void onActivityResult() throws Exception {
    }

}