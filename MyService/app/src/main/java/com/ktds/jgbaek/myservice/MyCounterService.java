package com.ktds.jgbaek.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyCounterService extends Service {

    private TextToSpeech tts;

    public MyCounterService() {
    }
    private int count;

    /**
     * Service와 Activity가 통신하기 위한 바인더 객체
     * Activity에게 getCount() 메소드를 제공해 Service의 count값을 전달한다.
     */
    IMyCounterService.Stub binder = new IMyCounterService.Stub() {
        @Override
        public int getCount() throws RemoteException {
            return count;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        Thread counter = new Thread(new Counter());
        counter.start();
    }

    private boolean isStop;

    /**
     * stopService가 실행될 때 호출됨
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }

    private class Counter implements Runnable {


        private Handler handler = new Handler();
        @Override
        public void run() {

            for( count = 0; count < 10; count ++ ) {

                if ( isStop ){
                    break;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), count+"", Toast.LENGTH_SHORT).show();
                        Log.d("COUNT", count+"");
                    }
                });


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "서비스가 종료되었습니다.", Toast.LENGTH_SHORT).show();
                    tts.speak("라면이 다 익었습니다..", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }
}
