package com.lakeba.javasamples;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SubActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        try {
            doHandlerSample();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doHandlerSample() throws InterruptedException {

        Runnable runnable = new Runnable() {
            private volatile boolean isRunning = true;
            @Override
            public void run() {
                System.out.println("Inside topThread");

                for (int i = 0; i < 4; i++) {

                    if(isRunning){
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (i == 2) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Middle of background task!");
                                    Toast.makeText(SubActivity.this, "Middle of background task!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else{
                        break;
                    }
                }

                // Outside for loop
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Background task completed!");
                        Toast.makeText(SubActivity.this, "Background task completed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void stop(){
                isRunning = false;
            }
        };


        // Using Java Thread
        Thread topThread = new Thread(runnable);
        System.out.println("Before start -> "+topThread.getState());
        topThread.start();
        System.out.println("After start -> "+topThread.getState());
        //topThread.stop();
        topThread.interrupt();
        System.out.println("After stop -> "+topThread.getState());
       // topThread.start();//java.lang.IllegalThreadStateException


        //Using Android HandlerThread
        /*HandlerThread handlerThread = new HandlerThread("SampleHandlerThread"){};
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        handler.post(runnable);
        handler.post(runnable);*/

        //Message message = handler.obtainMessage();
        //handler.sendEmptyMessage(0);

       // handlerThread.quit();
    }
}
