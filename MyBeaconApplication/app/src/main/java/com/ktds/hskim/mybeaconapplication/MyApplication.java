package com.ktds.hskim.mybeaconapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by MinChang Jang on 2016-06-23.
 */
public class MyApplication extends Application {

    private BeaconManager beaconManager;

    /**
     * Application을 설치할 때 실행됨.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        // Application 설치가 끝나면 Beacon Monitoring Service를 시작한다.
        // Application을 종료하더라도 Service가 계속 실행된다.
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
                        0, 0));
            }
        });

        // Android 단말이 Beacon 의 송신 범위에 들어가거나, 나왔을 때를 체크한다.
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("들어옴", "비콘 연결됨" + list.get(0).getRssi());

                // 동일 엑티비티가 이미 실행되어 있는  상태라면, Beacon 신호가 들어오더라도
                // 실행시키지 않는다.
                if(!isAlreadyRunActivity()){
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("executeType","beacon");
                    getApplicationContext().startActivity(new Intent(intent));
                }
                else {
                    showNotification("title", "message");
                }


            }
            @Override
            public void onExitedRegion(Region region) {
                showNotification("나감", "비콘 연결끊김");
            }
        });
    }

    /**
     * Notification으로 Beacon 의 신호가 연결되거나 끊겼음을 알림.
     * @param title
     * @param message
     */
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private boolean isAlreadyRunActivity(){
        ActivityManager activity_manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> task_info =
                activity_manager.getRunningTasks(9999);

        String activityName="";
        for(int i=0; i<task_info.size(); i++){
            activityName = task_info.get(i).topActivity.getPackageName();

            if(activityName.startsWith("com.ktds.hskim.mybeanconapplication")){
                return true;
            }
        }
        return false;
    }
}