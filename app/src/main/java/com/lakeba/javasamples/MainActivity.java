package com.lakeba.javasamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doThreading();
    }

    final CountDownLatch countDownLatch = new CountDownLatch(3);

    public void doThreading(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside new thread");
                System.out.println("active thread ->"+Thread.activeCount());
                    for (int i = 0; i < 20; i++) {
                        System.out.println("NewThread-> " + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                countDownLatch.countDown();
            }
        }).start();

        HomeThread homeThread = new HomeThread();
        homeThread.setPriority(Thread.MAX_PRIORITY);
        new Thread(homeThread).start();

        SubThread subThreadObject = new SubThread();
        Thread subThread = new Thread(subThreadObject);
        subThread.setPriority(Thread.MAX_PRIORITY);
        subThread.setDaemon(true);
        subThread.start();
        new Thread(new SubThread()).start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //new HomeThread().start();
    }

    private class SubThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Inside SubThread");

                for (int i = 0; i < 20; i++) {
                    System.out.println("SubThread->" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            countDownLatch.countDown();

        }
    }

    private class HomeThread extends Thread {
        @Override
        public void run() {
            System.out.println("Inside HomeThread");
                for (int i = 0; i < 20; i++) {
                    System.out.println("HomeThread->" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

            countDownLatch.countDown();
        }
    }
}
