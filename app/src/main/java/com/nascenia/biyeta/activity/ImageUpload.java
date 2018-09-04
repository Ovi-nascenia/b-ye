package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nascenia.biyeta.NetWorkOperation.NetWorkOperation;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ImageUpload extends AppCompatActivity {
    OkHttpClient client;

    int numberOfImage = 0, numberOfImageAdded = 0;

    public static int beforeProPicUploadValue = 0, beforeBodyPicUploadValue = 0, beforeOtherPicUploadValue = 0;
    public static int afterProPicUploadValue = 0, afterBodyPicUploadValue = 0, afterOtherPicUploadValue = 0;

    LinearLayout beforeProPicUpload, beforeBodyPicUpload, beforeOtherPicUpload, proPicChange,
            bodyPicChange, otherPicChange, permissionLayout;
    FrameLayout afterProPicUpload, afterBodyPicUpload, afterOtherPicUpload;
    RelativeLayout otherImages;
    ImageView proPic, bodyPic, otherPic, back;
    public static Bitmap proPicBitmap, bodyPicBitmap, otherPicBitmap;
    Button yesButton, noButton;

    public static String proPicBase64, bodyPicBase64, otherPicBase64;
    public static String proPicFileName, bodyPicFileName, otherPicFileName;

    private ProgressDialog progress;
    private SharePref sharePref;

    private static final int CAMERA_REQUEST = 1888;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, picTypeSelected = -1;
    private boolean profilePicChanged = false, bodyPicChanged = false, otherPicChanged = false;
    private Uri fileUri;
    private boolean isSignUp = false;
    ArrayList<String> images_list;
    String encoded;
    Bitmap bitmap;
    String SERVER_URL = "";
    private HashMap<Integer, String> updatedImageList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        sharePref = new SharePref(ImageUpload.this);

        progress = new ProgressDialog(ImageUpload.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        isSignUp = getIntent().getBooleanExtra("isSignUp", false);
        images_list = getIntent().getStringArrayListExtra("images_list");
        if(images_list != null){
            SERVER_URL = Utils.PROFILE_UPDATE;
        }else{
            SERVER_URL = Utils.SEND_INFO;
        }

        client = new OkHttpClient();
        beforeProPicUpload = (LinearLayout) findViewById(R.id.before_pro_pic_upload);
        beforeBodyPicUpload = (LinearLayout) findViewById(R.id.before_body_pic_upload);
        beforeOtherPicUpload = (LinearLayout) findViewById(R.id.before_other_pic_upload);

        proPicChange = (LinearLayout) findViewById(R.id.pro_pic_image_change);
        bodyPicChange = (LinearLayout) findViewById(R.id.body_pic_image_change);
        otherPicChange = (LinearLayout) findViewById(R.id.other_pic_image_change);

        afterProPicUpload = (FrameLayout) findViewById(R.id.after_pro_pic_upload);
        afterBodyPicUpload = (FrameLayout) findViewById(R.id.after_body_pic_upload);
        afterOtherPicUpload = (FrameLayout) findViewById(R.id.after_other_pic_upload);

        otherImages = findViewById(R.id.other_images);

        proPic = (ImageView) findViewById(R.id.pro_pic);
        bodyPic = (ImageView) findViewById(R.id.body_pic);
        otherPic = (ImageView) findViewById(R.id.other_pic);

        if(images_list != null){
            setDataOnView();
        }

        permissionLayout = (LinearLayout) findViewById(R.id.permission);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(images_list != null)
                    finish();
                else
                    new GetPreviousStepFetchConstant().execute();
            }
        });

        beforeProPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                //  startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 1;
                }
                selectImage();
                //ImagePicker.pickImage(ImageUpload.this);
            }
        });

        beforeBodyPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBodyPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 2;
                }
                selectImage();
            }
        });

        beforeOtherPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 3;
                }
                selectImage();
            }
        });



      //  proPicChange.setOnClickListener(new View.OnClickListener() {
        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 1;
                }
               selectImage();
               // ImagePicker.pickImage(ImageUpload.this);
            }
        });
       // bodyPicChange.setOnClickListener(new View.OnClickListener() {
        bodyPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBodyPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 2;
                }
                selectImage();

            }
        });

      //  otherPicChange.setOnClickListener(new View.OnClickListener() {
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                if(images_list != null && images_list.size() > 0) {
                    picTypeSelected = 3;
                }
                selectImage();
            }
        });



        yesButton = (Button) findViewById(R.id.yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfImageAdded = 0;
                String proPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(proPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(1)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(1)
                        .append("\"")
                        .append("}").toString();

                String bodyPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(bodyPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(2)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(1)
                        .append("\"")
                        .append("}").toString();

                String otherPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(otherPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(3)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(1)
                        .append("\"")
                        .append("}").toString();
                Log.i("picdata", proPic);


                if (afterProPicUploadValue == 1) {
                    if(images_list != null && profilePicChanged){
                        JSONObject jsonProObject = new JSONObject();
                        JSONObject jsonProfileObject = new JSONObject();
                        JSONObject jsonPhotoNoObject = new JSONObject();
                        JSONObject jsonPhotoObject = new JSONObject();
                        try {
                            jsonPhotoObject.put("image", proPicBase64);
                            jsonPhotoObject.put("image_name", proPicFileName);
                            jsonPhotoObject.put("photo_type", 1);
                            jsonPhotoNoObject.put("0", jsonPhotoObject);
                            jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                            jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "1");
                            jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), proPicFileName, SERVER_URL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if(images_list == null){
                        numberOfImageAdded++;
                        new ImageUpload.SendPicture().execute(proPic, proPicFileName, SERVER_URL);
                    }

                    if(bodyPicBase64!=null) {
                        if(images_list != null && bodyPicChanged){
                            JSONObject jsonProObject = new JSONObject();
                            JSONObject jsonProfileObject = new JSONObject();
                            JSONObject jsonPhotoAttrObject = new JSONObject();
                            JSONObject jsonPhotoNoObject = new JSONObject();
                            JSONObject jsonPhotoObject = new JSONObject();
                            try {
                                jsonPhotoObject.put("image", bodyPicBase64);
                                jsonPhotoObject.put("image_name", bodyPicFileName);
                                jsonPhotoObject.put("photo_type", 2);
                                jsonPhotoNoObject.put("1", jsonPhotoObject);
                                jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                                jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "1");
                                jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                                numberOfImageAdded++;
                                new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), bodyPicFileName, SERVER_URL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(images_list == null){
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(bodyPic, bodyPicFileName,
                                    SERVER_URL);
                        }

                    }
                    if(otherPicBase64!=null){
                        if(images_list != null && otherPicChanged){
                            JSONObject jsonProObject = new JSONObject();
                            JSONObject jsonProfileObject = new JSONObject();
                            JSONObject jsonPhotoNoObject = new JSONObject();
                            JSONObject jsonPhotoObject = new JSONObject();
                            try {
                                jsonPhotoObject.put("image", otherPicBase64);
                                jsonPhotoObject.put("image_name", otherPicFileName);
                                jsonPhotoObject.put("photo_type", 3);
                                jsonPhotoNoObject.put("2", jsonPhotoObject);
                                jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                                jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "1");
                                jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                                numberOfImageAdded++;
                                new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), otherPicFileName, SERVER_URL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(images_list == null){
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(otherPic, otherPicFileName,
                                    SERVER_URL);
                        }

                     }
                    if(images_list != null && numberOfImageAdded == 0){
                        finish();
                    }
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.select_image_message), Toast.LENGTH_SHORT).show();
                }

                if (afterBodyPicUploadValue == 1) {

                }

                if (afterOtherPicUploadValue == 1) {

                }


            }
        });

        noButton = (Button) findViewById(R.id.no);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(proPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(1)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(0)
                        .append("\"")
                        .append("}").toString();

                String bodyPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(bodyPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(2)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(0)
                        .append("\"")
                        .append("}").toString();

                String otherPic = new StringBuilder().append("{")
                        .append("\"image\":")
                        .append("\"")
                        .append(otherPicBase64)
                        .append("\"")
                        .append(",")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(3)
                        .append(",")
                        .append("\"photo_type\":")
                        .append("\"")
                        .append(3)
                        .append("\"")
                        .append(",")
                        .append("\"photo_is_public\":")
                        .append("\"")
                        .append(0)
                        .append("\"")
                        .append("}").toString();

                if (afterProPicUploadValue == 1) {
                    if(images_list != null && profilePicChanged){
                        JSONObject jsonProObject = new JSONObject();
                        JSONObject jsonProfileObject = new JSONObject();
                        JSONObject jsonPhotoNoObject = new JSONObject();
                        JSONObject jsonPhotoObject = new JSONObject();
                        try {
                            jsonPhotoObject.put("image", proPicBase64);
                            jsonPhotoObject.put("image_name", proPicFileName);
                            jsonPhotoObject.put("photo_type", 1);
                            jsonPhotoNoObject.put("0", jsonPhotoObject);
                            jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                            jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "0");
                            jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), proPicFileName, SERVER_URL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if(images_list == null){
                        numberOfImageAdded++;
                        new ImageUpload.SendPicture().execute(proPic, proPicFileName, SERVER_URL);
                    }

                    if(bodyPicBase64!=null) {
                        if(images_list != null && bodyPicChanged){
                            JSONObject jsonProObject = new JSONObject();
                            JSONObject jsonProfileObject = new JSONObject();
                            JSONObject jsonPhotoNoObject = new JSONObject();
                            JSONObject jsonPhotoObject = new JSONObject();
                            try {
                                jsonPhotoObject.put("image", bodyPicBase64);
                                jsonPhotoObject.put("image_name", bodyPicFileName);
                                jsonPhotoObject.put("photo_type", 2);
                                jsonPhotoNoObject.put("1", jsonPhotoObject);
                                jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                                jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "0");
                                jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                                numberOfImageAdded++;
                                new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), bodyPicFileName, SERVER_URL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(images_list == null){
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(bodyPic, bodyPicFileName,
                                    SERVER_URL);
                        }

                    }
                    if(otherPicBase64!=null) {
                        if(images_list != null && otherPicChanged){
                            JSONObject jsonProObject = new JSONObject();
                            JSONObject jsonProfileObject = new JSONObject();
                            JSONObject jsonPhotoNoObject = new JSONObject();
                            JSONObject jsonPhotoObject = new JSONObject();
                            try {
                                jsonPhotoObject.put("image", otherPicBase64);
                                jsonPhotoObject.put("image_name", otherPicFileName);
                                jsonPhotoObject.put("photo_type", 3);
                                jsonPhotoNoObject.put("2", jsonPhotoObject);
                                jsonProfileObject.put("photos_attributes", jsonPhotoNoObject);
                                jsonProfileObject.put(Utils.IS_PUBLIC_PHOTO, "0");
                                jsonProObject.put(Utils.PROFILE, jsonProfileObject);
                                numberOfImageAdded++;
                                new ImageUpload.SendPicture().execute(jsonProObject.toString().replace("\\", ""), otherPicFileName, SERVER_URL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(images_list == null){
                            numberOfImageAdded++;
                            new ImageUpload.SendPicture().execute(otherPic, otherPicFileName,
                                    SERVER_URL);
                        }

                    }
                    if(images_list != null && numberOfImageAdded == 0){
                        finish();
                    }
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.select_image_message), Toast.LENGTH_SHORT).show();
                }

                if (afterBodyPicUploadValue == 1) {
                    //
                }

                if (afterOtherPicUploadValue == 1) {
                    //
                }

            }
        });

        if(images_list == null) {
            afterOtherPicUpload.setVisibility(View.GONE);
            afterBodyPicUpload.setVisibility(View.GONE);
            afterProPicUpload.setVisibility(View.GONE);
        }

        permissionLayout.setVisibility(View.VISIBLE);

    }

    private void setDataOnView() {
        new ImagesDownloadTask().execute(images_list);


//        Picasso.with(ImageUpload.this)
//                .load(Utils.Base_URL + images_list.get(0))
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                        proPic.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println(1);
//                                proPic.setImageBitmap(bitmap);
//                                beforeProPicUpload.setVisibility(View.GONE);
//                                afterProPicUpload.setVisibility(View.VISIBLE);
//                                Utils.scaleImage(ImageUpload.this, 2f, proPic);
//                                generateEncodedImages(bitmap, 1, images_list.get(0));
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
//
//        afterProPicUpload.setVisibility(View.VISIBLE);
//        afterOtherPicUpload.setVisibility(View.GONE);
//        beforeOtherPicUpload.setVisibility(View.VISIBLE);
//        afterBodyPicUpload.setVisibility(View.GONE);
//        beforeBodyPicUpload.setVisibility(View.VISIBLE);
//        otherImages.setVisibility(View.VISIBLE);
//
//        if(images_list.get(2) != null){
//            Picasso.with(ImageUpload.this)
//                    .load(Utils.Base_URL + images_list.get(2))
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                            otherPic.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    System.out.println(3);
//                                    otherPic.setImageBitmap(bitmap);
//                                    beforeOtherPicUpload.setVisibility(View.GONE);
//                                    afterOtherPicUpload.setVisibility(View.VISIBLE);
//                                    Utils.scaleImage(ImageUpload.this, 2f, otherPic);
//                                    generateEncodedImages(bitmap, 3, images_list.get(2));
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });
//        }
//
//        if(images_list.get(1) != null){
//            Picasso.with(ImageUpload.this)
//                    .load(Utils.Base_URL + images_list.get(1))
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                            bodyPic.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    System.out.println(2);
//                                    bodyPic.setImageBitmap(bitmap);
//                                    beforeBodyPicUpload.setVisibility(View.GONE);
//                                    afterBodyPicUpload.setVisibility(View.VISIBLE);
//                                    Utils.scaleImage(ImageUpload.this, 2f, bodyPic);
//                                    generateEncodedImages(bitmap, 2, images_list.get(1));
//
//                                }
//                            });
//
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });
//        }

//        if(images_list.get(2) != null){
//            Picasso.with(ImageUpload.this)
//                    .load(Utils.Base_URL + images_list.get(2))
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                            otherPic.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    System.out.println(3);
//                                    otherPic.setImageBitmap(bitmap);
//                                    beforeOtherPicUpload.setVisibility(View.GONE);
//                                    afterOtherPicUpload.setVisibility(View.VISIBLE);
//                                    Utils.scaleImage(ImageUpload.this, 2f, otherPic);
//                                    generateEncodedImages(bitmap, 3, images_list.get(2));
//                                }
//                            });
//
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });
//        }


    }

    private void generateEncodedImages(Bitmap bmp, int proPicType, String img_url){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        setBitmapData(bmp, proPicType, img_url);
    }

    private void setBitmapData(Bitmap bitmap, int proPicType, String img_url) {
        String[] strFileName = img_url.split("/");
        System.out.println("setBitmapData: " + proPicType);
        if (proPicType == 1) {
            afterProPicUploadValue = 1;
            proPicBitmap = bitmap;
            beforeProPicUploadValue = 0;
            proPicBase64 = "data:image/jpeg;base64," + encoded;
            proPicFileName = strFileName[strFileName.length-1];
        } else if (proPicType == 2) {
            afterBodyPicUploadValue = 1;
            bodyPicBitmap = bitmap;
            beforeBodyPicUploadValue = 0;
            bodyPicBase64 = "data:image/jpeg;base64," + encoded;
            bodyPicFileName = strFileName[strFileName.length-1];
        } else if (proPicType == 3) {
            afterOtherPicUploadValue = 1;
            otherPicBitmap = bitmap;
            beforeOtherPicUploadValue = 0;
            otherPicBase64 = "data:image/jpeg;base64," + encoded;
            otherPicFileName = strFileName[strFileName.length-1];
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ImageCrop.cropImage == 1) {
            ImageCrop.cropImage = 0;

            if (afterProPicUploadValue == 1) {
                proPic.setImageBitmap(proPicBitmap);
                if(proPicBitmap!=null) {
                    beforeProPicUpload.setVisibility(View.GONE);
                    afterProPicUpload.setVisibility(View.VISIBLE);
                }
            }
            if (afterBodyPicUploadValue == 1) {
                bodyPic.setImageBitmap(bodyPicBitmap);
                if(bodyPicBitmap!=null) {
                    beforeBodyPicUpload.setVisibility(View.GONE);
                    afterBodyPicUpload.setVisibility(View.VISIBLE);
                }
            }
            if (afterOtherPicUploadValue == 1) {
                otherPic.setImageBitmap(otherPicBitmap);
                if(otherPicBitmap!=null) {
                    beforeOtherPicUpload.setVisibility(View.GONE);
                    afterOtherPicUpload.setVisibility(View.VISIBLE);
                }
            }
        }
        if(proPic.getDrawable()!= null){
            afterProPicUploadValue = 1;
        }
        if(bodyPic.getDrawable()!= null){
            afterBodyPicUploadValue = 1;
        }
        if(otherPic.getDrawable()!= null){
            afterOtherPicUploadValue = 1;
        }

    }

    class LoadProfileImages extends AsyncTask<ImageView, String, Bitmap>{

        ImageView imageView = null;
        String url;
        int proPicType;

        public LoadProfileImages(ImageView imageView, String url, int proPicType){
            this.imageView = imageView;
            this.url = url;
            this.proPicType = proPicType;
        }

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
//            this.imageView = imageViews[0];
            return download_Image();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            String[] strFileName = this.url.split("/");
//            System.out.println("setBitmapData: " + proPicType);
            if (proPicType == 1) {
                afterProPicUploadValue = 1;
                proPicBitmap = bitmap;
                beforeProPicUploadValue = 0;
                proPicBase64 = "data:image/jpeg;base64," + encoded;
                proPicFileName = strFileName[strFileName.length-1];
            } else if (proPicType == 2) {
                afterBodyPicUploadValue = 1;
                bodyPicBitmap = bitmap;
                beforeBodyPicUploadValue = 0;
                bodyPicBase64 = "data:image/jpeg;base64," + encoded;
                bodyPicFileName = strFileName[strFileName.length-1];
            } else if (proPicType == 3) {
                afterOtherPicUploadValue = 1;
                otherPicBitmap = bitmap;
                beforeOtherPicUploadValue = 0;
                otherPicBase64 = "data:image/jpeg;base64," + encoded;
                otherPicFileName = strFileName[strFileName.length-1];
            }
        }


        private Bitmap download_Image() {
            return null;
        }
    }


    class SendPicture extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(ImageUpload.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            if (!progress.isShowing())
                progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (s == null) {

                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
//                        progress.cancel();
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(ImageUpload.this,
                                jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                    } else {
                        numberOfImage++;
                        if (jsonObject.has("success")) {
                            if(jsonObject.has("photo_information")){
                                JSONObject jsonPhotoInfo = jsonObject.getJSONObject("photo_information");
                                if(updatedImageList.containsKey(jsonPhotoInfo.getInt("photo_type")))
                                    updatedImageList.remove(jsonPhotoInfo.getInt("photo_type"));
                                updatedImageList.put(jsonPhotoInfo.getInt("photo_type"), jsonPhotoInfo.getString("image_url"));
                            }
                        }
                        if (numberOfImage == numberOfImageAdded) {
                            if(images_list != null) {
                                Intent intent = getIntent();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("image_list", updatedImageList);
                                intent.putExtras(bundle);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                new ImageUpload.FetchConstant().execute();
                                beforeProPicUploadValue = 0;
                                beforeBodyPicUploadValue = 0;
                                beforeOtherPicUploadValue = 0;
                                afterProPicUploadValue = 0;
                                afterBodyPicUploadValue = 0;
                                afterOtherPicUploadValue = 0;
                                numberOfImage = 0;
                                numberOfImageAdded = 0;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            SharePref sharePref = new SharePref(ImageUpload.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(300, TimeUnit.SECONDS);
            client.setReadTimeout(300, TimeUnit.SECONDS);
            client.setWriteTimeout(300, TimeUnit.SECONDS);

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = null;

            if(images_list != null) {
                request = new Request.Builder()
                        .url(strings[2])
                        .addHeader("Authorization", "Token token=" + token)
                        .post(body)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(strings[2])
                        .addHeader("Authorization", "Token token=" + token)
                        .addHeader("file_name", strings[1])
                        .post(body)
                        .build();
            }
            Response response = null;
            String responseString = null;
            try {
                response = client.newCall(request).execute();
                responseString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }


    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (s == null) {
//                progress.cancel();
                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                //clearBitmapData();
                Log.i("constantval", "ImageUploadNextfetchval: " + s);
                Intent signupIntent;
//                signupIntent = new Intent(ImageUpload.this, RegistrationChoiceSelectionFirstPage.class);
                signupIntent = new Intent(ImageUpload.this, RegistrationPersonalInformation.class);
                signupIntent.putExtra("constants", s);
                signupIntent.putExtra("isSignUp", true);
                startActivity(signupIntent);
//                finish();
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            // Login.currentMobileSignupStep += 1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
                    .url(Utils.STEP_CONSTANT_FETCH + 4)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(images_list != null)
            finish();
        else
            new GetPreviousStepFetchConstant().execute();
    }


    public class GetPreviousStepFetchConstant extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 2)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            Log.i("urldata", Utils.STEP_CONSTANT_FETCH + 2);
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            Log.i("urldata", s + "");

            if (s == null) {

                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                /*if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                //clearBitmapData();
                Log.i("constantval", "ImageUpdloadBackfetchval: " + s);
                if(isSignUp) {
                    finish();
                }else {
                    startActivity(new Intent(ImageUpload.this, RegistrationOwnInfo.class).
                            putExtra("constants", s));
                    finish();
                }
            }
        }
    }

    public static void clearImageUploadClassStaticData() {

        beforeProPicUploadValue = 0;
        beforeBodyPicUploadValue = 0;
        beforeOtherPicUploadValue = 0;
        afterProPicUploadValue = 0;
        afterBodyPicUploadValue = 0;
        afterOtherPicUploadValue = 0;

        if(proPicBitmap!=null) {
            proPicBitmap.recycle();
            proPicBitmap = null;
        }
        if(bodyPicBitmap!=null) {
            bodyPicBitmap.recycle();
            bodyPicBitmap = null;
        }
        if(otherPicBitmap!=null) {
            otherPicBitmap.recycle();
            otherPicBitmap = null;
        }

        proPicBase64 = "";
        bodyPicBase64 = "";
        otherPicBase64 = "";

        ImageCrop.cropImage=0;
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose From Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageUpload.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkExternalStoragePermission(ImageUpload.this);

                if (items[item].equals("Take Photo")) {
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose From Gallery")) {
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    private void galleryIntent() {
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);*/
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_FILE);
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        File mediaFile = null;
        try {
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            mediaFile = new File(Environment.getExternalStorageDirectory() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaFile;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            Log.i("imageurl", " onActivityResult");

            try {
                if (requestCode == REQUEST_CAMERA) {
                    Log.i("imageurl", " REQUEST_CAMERA");

                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // downsizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;


                    Log.i("imageurl", fileUri.getPath().toString());
                    Intent i = new Intent(ImageUpload.this, ImageCrop.class);
                    i.putExtra("image_url", fileUri.getPath());
                    i.putExtra("pic_type", picTypeSelected);
                    startActivityForResult(i, Utils.PIC_CHANGE_REQ);

                } else if (requestCode == SELECT_FILE) {
                    Log.i("imageurl", "SELECT_FILE");

                    //imageStream = getContentResolver().openInputStream(data.getData());
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.i("imageurl", picturePath);

                    Intent i = new Intent(ImageUpload.this, ImageCrop.class);
                    i.putExtra("image_url", picturePath);
                    i.putExtra("pic_type", picTypeSelected);
                    startActivityForResult(i, Utils.PIC_CHANGE_REQ);
                }else if(requestCode == Utils.PIC_CHANGE_REQ){
//                    boolean isPicChanged = data.getBooleanExtra("pic_changed", false);
                    int pic_type = data.getIntExtra("pic_type", -1);
                    if(pic_type == 1)
                        profilePicChanged = true;
                    else if(pic_type == 2)
                        bodyPicChanged = true;
                    else if(pic_type == 3)
                        otherPicChanged = true;
                }
                otherImages.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();

                Log.i("imageurl", "error " + e.getMessage());
            }
        } else {
            Log.i("imageurl", "can't complete");
        }
    }

    @Override
    public void finish() {
        super.finish();
        if(proPicBitmap!=null) {
//            proPicBitmap.recycle();
            proPic.setImageBitmap(null);
            proPicBitmap = null;
        }
        if(bodyPicBitmap!=null) {
//            bodyPicBitmap.recycle();
            bodyPic.setImageBitmap(null);
            bodyPicBitmap = null;
        }
        if(otherPicBitmap!=null) {
//            otherPicBitmap.recycle();
            otherPic.setImageBitmap(null);
            otherPicBitmap = null;
        }
    }

    private void updateDetails(JSONObject jsonObject) {
        new NetWorkOperation.updateProfileData(ImageUpload.this).execute(
                jsonObject.toString());
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_PICK) {
            ImagePicker.beginCrop(this, resultCode, data);
        } else if (requestCode == ImagePicker.REQUEST_CROP) {
            Bitmap bitmap = ImagePicker.getImageCropped(this, resultCode, data,
                    ImagePicker.ResizeType.FIXED_SIZE, 100);
            Log.d("bitmap", "bitmap picked: " + bitmap);

            proPic.setImageBitmap(bitmap);
            beforeProPicUpload.setVisibility(View.GONE);
            afterProPicUpload.setVisibility(View.VISIBLE);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    private class ImagesDownloadTask extends AsyncTask<List<String>,Integer,List<Bitmap>>{

        @Override
        protected List<Bitmap> doInBackground(List<String>... lists) {
            int count = lists[0].size();
            //URL url = urls[0];
            HttpURLConnection connection = null;
            List<Bitmap> bitmaps = new ArrayList<>();

            // Loop through the urls
            for(int i=0;i<count;i++){
                String url = Utils.Base_URL + lists[0].get(i);

                // So download the image from this url
                try{
                    URL currentURL = new URL(url);
                    // Initialize a new http url connection
                    connection = (HttpURLConnection) currentURL.openConnection();

                    // Connect the http url connection
                    connection.connect();

                    // Get the input stream from http url connection
                    InputStream inputStream = connection.getInputStream();

                    // Initialize a new BufferedInputStream from InputStream
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    // Convert BufferedInputStream to Bitmap object
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                    // Add the bitmap to list
                    bitmaps.add(bmp);

                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    // Disconnect the http url connection
                    connection.disconnect();
                }
            }
            // Return bitmap list
            return bitmaps;
        }

        protected void onPostExecute(List<Bitmap> result){

            for(int i=0;i<result.size();i++){
                Bitmap bitmap = result.get(i);
                if(i == 0) {
                    beforeProPicUpload.setVisibility(View.GONE);
                    afterProPicUpload.setVisibility(View.VISIBLE);
                    Utils.scaleImage(ImageUpload.this, 2f, proPic);
                    generateEncodedImages(bitmap, 1, images_list.get(0));
                    proPic.setImageBitmap(bitmap);
                    afterProPicUpload.setVisibility(View.VISIBLE);
                    afterOtherPicUpload.setVisibility(View.GONE);
                    beforeOtherPicUpload.setVisibility(View.VISIBLE);
                    afterBodyPicUpload.setVisibility(View.GONE);
                    beforeBodyPicUpload.setVisibility(View.VISIBLE);
                    otherImages.setVisibility(View.VISIBLE);
                } else if(i == 1) {
                    beforeBodyPicUpload.setVisibility(View.GONE);
                    afterBodyPicUpload.setVisibility(View.VISIBLE);
                    Utils.scaleImage(ImageUpload.this, 2f, bodyPic);
                    generateEncodedImages(bitmap, 2, images_list.get(1));
                    bodyPic.setImageBitmap(bitmap);
                } else {
                    beforeOtherPicUpload.setVisibility(View.GONE);
                    afterOtherPicUpload.setVisibility(View.VISIBLE);
                    Utils.scaleImage(ImageUpload.this, 2f, otherPic);
                    generateEncodedImages(bitmap, 3, images_list.get(2));
                    otherPic.setImageBitmap(bitmap);
                }
            }
        }
    }
}
