package com.ktds.jgbaek.mycameraapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnTakePicture;
    private ImageView ivPicture;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        ivPicture = (ImageView) findViewById(R.id.ivPicture);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Camera Application 이 있는지 조사한다.
                if( isExistsCameraApplication() ){
                    //2. Camera Application을 실행한다.
                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // 3. 찍은 사진을 보관할 파일 객체를 만들어서 보낸다.
                    File picture = savePictureFile();
                    if(picture != null ) {
                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                        startActivityForResult(cameraApp, 10000);
                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10000 && resultCode == RESULT_OK){
            //사진을 IMAGE_VIEW에 보여준다.

            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(imagePath);

            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, factory);
            ivPicture.setImageBitmap(bitmap);

        }
    }

    /**
     * Android에 Camera Application이 설치되어 있는지 확인한다.
     * @return
     */
    private boolean isExistsCameraApplication() {

        /**
         * Android의 모든 Application을 얻어온다.
         */
        PackageManager packageManager = getPackageManager();
        // Camera Application
        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // MediaStore.ACTION_IMAGE_CAPTURE Intent를 처리할 수 있는 Application 정보를 가져온다.
        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(cameraApp, PackageManager.MATCH_DEFAULT_ONLY);

        return cameraApps.size()>0;
    }

    /**
     * 카메라에서 찍은 사진을 외부 저장소에 저장한다.
     * @return
     */
    private File savePictureFile() {

       // 외부 저장소 쓰기 권한을 얻어온다.
        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);
        int result = requester.create().request(Manifest.permission.WRITE_EXTERNAL_STORAGE, 20000
                , new PermissionRequester.OnClickDenyButtonListener() {
            @Override
            public void onClick(Activity activity) {

            }
        });

        if ( result == PermissionRequester.ALREADY_GRANTED || result == PermissionRequester.REQUEST_PERMISSION ) {

            // 사진 파일의 이름을 만든다.
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String fileName = "IMG_" + timestamp;

            // 사진 파일이 저장될 장소를 구한다.
            File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MYAPP/");

            // 사진 파일이 저장될 폴더가 존재하지 않는다면, 폴더를 새로 만들어준다.
            if ( !pictureStorage.exists() ){
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);

                // 사진 파일의 절대 경로를 얻어옵니다.
                // 나중에 ImageView에 보여줄 때 필요하다.
                imagePath = file.getAbsolutePath();

                // 찍힌 사진을 "갤러리" 앱에 추가한다.
                Intent mediaScaIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                mediaScaIntent.setData(contentUri);
                this.sendBroadcast(mediaScaIntent);

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
