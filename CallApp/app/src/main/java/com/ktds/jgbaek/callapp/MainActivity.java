package com.ktds.jgbaek.callapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 *  현재 사용자의 OS버전이 마시멜로우 인지 체크한다.
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    /**
                     *  사용자 단말기의 권한 중 전화걸기 권한이 허용되어 있는지 체크한다.
                     */
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

                    // call_phong의 권한이 없을 떄
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        //  Package는 Android Application의 ID이다.
                        /**
                         *  사용자가 CALL_PHONE 권한을 한번이라도 거부한 적이 있는지 조사한다.
                         *  거부한 이력이 한번이라도 있다면, true를 리턴한다.
                         *  거부한 이력이 없다면 false를 리턴한다.
                         */
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\"권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 위 리스너랑 다른 범위여서 마쉬멜로우인지 또 체크해주어야 한다.
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MainActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();

                        }
                        // 최초로 권한을 요청 할 때
                        else {
                            // CALL_PHONE 권한을 안드로이드 OS에 요청합니다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    }
                    // call_phonne의 권한이 있을 떄
                    else {

                    }

                }
                // 사용자의 버전이 마시멜로우 이하일때
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                    startActivity(intent);
                }

                int result = new PermissionRequester.Builder(MainActivity.this)
                        .create()
                        .request(Manifest.permission.CALL_PHONE, 1000 , new PermissionRequester.OnClickDenyButtonListener() {
                            @Override
                            public void onClick(Activity activity) {
                                Log.d("xxx", "취소함.");
                            }
                        });

                if (result == PermissionRequester.ALREADY_GRANTED) {
                    Log.d("RESULT", "권한이 이미 존재함.");
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
                        startActivity(intent);
                    }
                }
                else if(result == PermissionRequester.NOT_SUPPORT_VERSION)
                    Log.d("RESULT", "마쉬멜로우 이상 버젼 아님.");
                else if(result == PermissionRequester.REQUEST_PERMISSION)
                    Log.d("RESULT", "요청함. 응답을 기다림.");

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 사용자 요청, 요청한 권한들, 응답들

        if (requestCode == 1000) {
            // 요청한 권한을 사용자가 허용했다면
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(MainActivity.this, "권한요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
