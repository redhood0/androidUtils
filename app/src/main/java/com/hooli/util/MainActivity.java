package com.hooli.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hooli.util.lostSearch.SearchTask;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    public static Context MainActivityContext;
    Button button;
    TextView tv_showMsg;
    //权限工具
    final RxPermissions rxPermissions = new RxPermissions(this);

    ImageView imageView;

    //拍照相关代码------------------------------
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//获取外部存储的图片路径
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.i("FileProvider.getUriForFile(this, \"com.hooli.util\", photoFile)-----",this + " ," + photoFile );

                Uri photoURI = FileProvider.getUriForFile(this, "com.hooli.util", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void getPermission(){
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            Log.i("PERMISSION","get permission success!");
                        } else {
                            Log.i("PERMISSION","get permission fale");
                            //Toast.makeText(getApplicationContext(),"拒绝将无法使用相机进行拍照",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//获得多个权限
    public void getMorePErmission(){
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                    }
                });
    }
    //more detial
    public void getMoreDetial(){
        rxPermissions
                .requestEach(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                        Toast.makeText(getApplicationContext(),permission.name + "获得权限",Toast.LENGTH_LONG).show();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        Toast.makeText(getApplicationContext(),permission.name +"拒绝将无法使用相机进行拍照",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),permission.name +"请去设置-应用-自行打开文件访问权限，傻逼！",Toast.LENGTH_LONG).show();
                        // Denied permission with ask never again
                        // Need to go to the settings
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        CameraUtil cameraUtil = new CameraUtil();
        cameraUtil.setPic(imageView);
        try {
            cameraUtil.makeImgSmall(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityContext = MainActivity.this;
        tv_showMsg = findViewById(R.id.tv_showMsg);

        imageView = findViewById(R.id.iv_photo);
        button = findViewById(R.id.button);
        button.setOnClickListener(n -> {
            getPermission();//获取权限
            //getMorePErmission();
            //getMoreDetial();
            //dispatchTakePictureIntent();
            CameraUtil cameraUtil = new CameraUtil();
            cameraUtil.dispatchTakePictureIntent(this,"com.hooli.util");

        });


    }

//点击事件
    public void searchLost(View view) throws IOException, JSONException {
        Log.i("Search","start search");
        //Log.i("Search","start search:" + new File(CameraUtil.currentPhotoPath));
        //LikePIcUtil.searchLost(CameraUtil.currentPhotoPath);
        new SearchTask().execute(tv_showMsg);
        Log.i("Search","end search");
    }


}
