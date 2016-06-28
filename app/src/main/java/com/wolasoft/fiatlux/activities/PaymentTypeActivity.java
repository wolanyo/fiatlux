package com.wolasoft.fiatlux.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wolasoft.fiatlux.R;

public class PaymentTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
