package com.cartrawler.integration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cartrawler.core.engine.CartrawlerSDK;
import cartrawler.core.ui.views.partner.SingleCarView;

public class MainActivity extends Activity {

    boolean carAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(v -> new CartrawlerSDK.Builder("105614")// The builder takes a String clientID
                .setEnvironment(CartrawlerSDK.Environment.STAGING)
                .setLogging(true)
                .startStandalone(this, 123));
        findViewById(R.id.button2).setOnClickListener(v -> {
            try {
                GregorianCalendar pickupDateTime = new GregorianCalendar();
                pickupDateTime.add(Calendar.DATE, 1);
                new CartrawlerSDK.Builder("105614")// The builder takes a String clientID
                        .setEnvironment(CartrawlerSDK.Environment.STAGING)
                        .setLogging(true)
                        .setPickupLocation("LHR")
                        .setPickupTime(pickupDateTime)
                        .startInPath(this, 123);
            } catch (CartrawlerSDK.IncorrectArgument incorrectArgument) {
                Log.e("CartrawlerSDK", incorrectArgument.getMessage(), incorrectArgument);
                //Handle exception
            }
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            try {
                GregorianCalendar pickupDateTime = new GregorianCalendar();
                pickupDateTime.add(Calendar.DATE, 1);
                GregorianCalendar dropOffDateTime = new GregorianCalendar();
                dropOffDateTime.add(Calendar.DATE, 4);
                new CartrawlerSDK.Builder("105614")
                        .setEnvironment(CartrawlerSDK.Environment.STAGING)
                        .setLogging(true)
                        .setPickupLocation("LHR")
                        .setPickupTime(pickupDateTime)
                        .getBestDailyRate(this, new CartrawlerSDK.BestDailyRateListener() {
                            @Override
                            public void onReceiveBestDailyRate(double v, String s) {
                                ((TextView) findViewById(R.id.textView)).setText(String.format("%s %d", s, v));
                            }

                            @Override
                            public void onError(CartrawlerSDK.ConnectionError connectionError) {
                                Log.e("CartrawlerSDK", connectionError.getMessage(), connectionError);
                            }
                        });
            } catch (CartrawlerSDK.IncorrectArgument incorrectArgument) {
                Log.e("CartrawlerSDK", incorrectArgument.getMessage(), incorrectArgument);
            }
        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            carAdded = !carAdded;
            ((SingleCarView) findViewById(R.id.singleCarView)).carAdded(carAdded);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            //TODO
        }
    }
}
