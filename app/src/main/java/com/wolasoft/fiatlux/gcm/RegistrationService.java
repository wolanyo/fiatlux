package com.wolasoft.fiatlux.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.wolasoft.fiatlux.R;

import java.io.IOException;

/**
 * Created by osiris on 19/06/16.
 */
public class RegistrationService extends IntentService {
    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);

        try {
            String registrationToken = myID.getToken(
                    Config.SENDER_ID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );

            Log.d("Registration Token", registrationToken);

            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/article", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}