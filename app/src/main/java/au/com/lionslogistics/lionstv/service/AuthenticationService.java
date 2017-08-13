package au.com.lionslogistics.lionstv.service;

import android.app.Activity;

/**
 * Created by alex-daphne on 5/08/2017.
 * All rights reserved
 */

public interface AuthenticationService {
    void onAuthenticationSuccessful();
    void onAuthenticationFailed();
    void onNetworkError();
}
