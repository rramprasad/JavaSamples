package com.lakeba.javasamples;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Lakeba.
 */

public class ThreadSample {

    final CountDownLatch countDownLatch = new CountDownLatch(3);
    private final Context mContext;
    private Handler mHandler = new Handler();


    public ThreadSample(Context context) {
        this.mContext = context;
    }

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
                                    Toast.makeText(mContext, "Middle of background task!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "Background task completed!", Toast.LENGTH_SHORT).show();
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


