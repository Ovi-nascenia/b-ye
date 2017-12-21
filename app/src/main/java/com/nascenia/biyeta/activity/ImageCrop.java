package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nascenia.biyeta.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageCrop extends Activity {
    String encoded;
    Bitmap bitmap;
    public static int cropImage = 0;
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;
    PhotoViewAttacher mAttacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_url");
        setContentView(R.layout.activity_image_crop);
        final ImageView view = (ImageView) findViewById(R.id.imageView1);
        File imgFile = new File(imageUrl);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenWidth = metrics.widthPixels;
            if (width >= height) {
                bitmap = Bitmap.createScaledBitmap(myBitmap, (screenWidth * 10) / 16, ((height * ((screenWidth * 10) / 16)) / width), true);
            } else {
                bitmap = Bitmap.createScaledBitmap(myBitmap, ((width * ((screenWidth * 10) / 16)) / height), (screenWidth * 10) / 16, true);
            }


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            Log.i("bitmap", "original: " + encoded);

            byte[] decodedString = Base64.decode(encoded, Base64.NO_WRAP);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

           /* if (ImageUpload.beforeProPicUploadValue == 1) {
                ImageUpload.afterProPicUploadValue = 1;
                ImageUpload.proPicBitmap = bitmap;
                // ImageUpload.proPicBitmap = modifiedBitmap;
                ImageUpload.beforeProPicUploadValue = 0;

                ImageUpload.proPicBase64 = "data:image/jpeg;base64," + encoded;
            } else if (ImageUpload.beforeBodyPicUploadValue == 1) {
                ImageUpload.afterBodyPicUploadValue = 1;
                ImageUpload.bodyPicBitmap = bitmap;
                ImageUpload.beforeBodyPicUploadValue = 0;

                ImageUpload.bodyPicBase64 = "data:image/jpeg;base64," + encoded;
            } else if (ImageUpload.beforeOtherPicUploadValue == 1) {
                ImageUpload.afterOtherPicUploadValue = 1;
                ImageUpload.otherPicBitmap = bitmap;
                ImageUpload.beforeOtherPicUploadValue = 0;

                ImageUpload.otherPicBase64 = "data:image/jpeg;base64," + encoded;
            }*/

            view.setImageBitmap(decodedByte);
            view.setBackgroundColor(Color.rgb(0, 0, 0));
        }

        final Button crop = (Button) findViewById(R.id.crop);
        final Button discart = (Button) findViewById(R.id.discart);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setDrawingCacheEnabled(true);
                Bitmap bmap = view.getDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(bmap, view.getLeft() + 50, view.getTop(),
                        view.getWidth() - 100, view.getHeight());
                //modifiedBitmap = bitmap;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                cropImage = 1;
                setBitmapdata(bitmap);

                Log.i("bitmap", "change: " + encoded);
                finish();
                //view.setImageBitmap(viewCapture);
                //view.setBackgroundColor(Color.BLUE);


            }
        });
        discart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAttacher = new PhotoViewAttacher(view);
    }

    private void setBitmapdata(Bitmap bitmap) {

        if (ImageUpload.beforeProPicUploadValue == 1) {
            ImageUpload.afterProPicUploadValue = 1;
            ImageUpload.proPicBitmap = bitmap;
            ImageUpload.beforeProPicUploadValue = 0;

            ImageUpload.proPicBase64 = "data:image/jpeg;base64," + encoded;
        } else if (ImageUpload.beforeBodyPicUploadValue == 1) {
            ImageUpload.afterBodyPicUploadValue = 1;
            ImageUpload.bodyPicBitmap = bitmap;
            ImageUpload.beforeBodyPicUploadValue = 0;

            ImageUpload.bodyPicBase64 = "data:image/jpeg;base64," + encoded;
        } else if (ImageUpload.beforeOtherPicUploadValue == 1) {
            ImageUpload.afterOtherPicUploadValue = 1;
            ImageUpload.otherPicBitmap = bitmap;
            ImageUpload.beforeOtherPicUploadValue = 0;

            ImageUpload.otherPicBase64 = "data:image/jpeg;base64," + encoded;
        }

    }


}
