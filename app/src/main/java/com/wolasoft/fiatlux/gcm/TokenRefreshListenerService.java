package com.wolasoft.fiatlux.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by osiris on 19/06/16.
 */
public class TokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
    }
}
