package com.lakeba.javasamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThreadSample threadSample = new ThreadSample(MainActivity.this);
        threadSample.doThreading();

        try {
            threadSample.doHandlerSample();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
