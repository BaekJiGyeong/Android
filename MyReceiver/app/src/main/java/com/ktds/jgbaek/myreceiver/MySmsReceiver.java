package com.ktds.jgbaek.myreceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MySmsReceiver extends BroadcastReceiver {
    public MySmsReceiver() {
    }

    /**
     * Android에 문자메시지가 도착할 경우, 실행된다.
     * @param context
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {



        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(context);
        Drawable d = context.getDrawable(R.mipmap.ic_launcher);

        iv.setImageDrawable(d);

        TextView tv = new TextView(context);
        tv.setText("문자메시지가 도착했습니다.");
        tv.setTextColor(Color.BLACK);


        ll.addView(iv);
        ll.addView(tv);

        Toast toast = Toast.makeText(context, "문자메시지가 도착했습니다.", Toast.LENGTH_SHORT);
        toast.setView(ll);
        toast.show();

        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[])bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        Date currentDate = null;
        String incommingNumber = "", message="", formattedMessge="";

        for(int i=0; i < messages.length; i++){
            smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);

            // 수신 시간 확인
            currentDate = new Date(smsMessage[i].getTimestampMillis());

            // 발신 번호 확인
            incommingNumber = smsMessage[i].getOriginatingAddress();

            // 메세지 확인
            message = smsMessage[i].getMessageBody().toString();

            formattedMessge = String.format("%s, %s, %s", currentDate.toString(), incommingNumber, message);
            Toast.makeText(context, formattedMessge, Toast.LENGTH_SHORT).show();
        }

    }
}
