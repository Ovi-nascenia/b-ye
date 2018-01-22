package com.nascenia.biyeta.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.ImagePicker;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUpload extends AppCompatActivity {
    OkHttpClient client;

    int numberOfImage = 0;

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

    private ProgressDialog progress;
    private SharePref sharePref;

    private static final int CAMERA_REQUEST = 1888;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        sharePref = new SharePref(ImageUpload.this);

        progress = new ProgressDialog(ImageUpload.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

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

        permissionLayout = (LinearLayout) findViewById(R.id.permission);


        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new Intent(ImageUpload.this,Login.class);
                finish();*/
                new GetPreviousStepFetchConstant().execute();

                /*startActivity(new Intent(ImageUpload.this,RegistrationOwnInfo.class));
                finish();*/
            }
        });

        beforeProPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                //  startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                selectImage();

                //ImagePicker.pickImage(ImageUpload.this);
            }
        });

        beforeBodyPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBodyPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                selectImage();
            }
        });

        beforeOtherPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                selectImage();
            }
        });



      //  proPicChange.setOnClickListener(new View.OnClickListener() {
        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
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
                selectImage();

            }
        });

      //  otherPicChange.setOnClickListener(new View.OnClickListener() {
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                //startActivity(new Intent(ImageUpload.this, ImageChoose.class));
                selectImage();
            }
        });



        yesButton = (Button) findViewById(R.id.yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
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
                    new ImageUpload.SendPicture().execute(proPic, Utils.SEND_INFO);
                    new ImageUpload.SendPicture().execute(bodyPic, Utils.SEND_INFO);
                    new ImageUpload.SendPicture().execute(otherPic, Utils.SEND_INFO);
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
                    new ImageUpload.SendPicture().execute(proPic, Utils.SEND_INFO);
                    new ImageUpload.SendPicture().execute(bodyPic, Utils.SEND_INFO);
                    new ImageUpload.SendPicture().execute(otherPic, Utils.SEND_INFO);
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

        afterOtherPicUpload.setVisibility(View.GONE);
        afterBodyPicUpload.setVisibility(View.GONE);
        afterProPicUpload.setVisibility(View.GONE);

        permissionLayout.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ImageCrop.cropImage == 1) {
            ImageCrop.cropImage = 0;
            //finish();

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

    }


    class SendPicture extends AsyncTask<String, String, String> {
        ProgressDialog progress = new ProgressDialog(ImageUpload.this);
        ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setMessage(getResources().getString(R.string.progress_dialog_message));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            if (!progress.isShowing())
                progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                progress.cancel();
                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        progress.cancel();
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(ImageUpload.this,
                                jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                    } else {
                        numberOfImage++;
                        if (numberOfImage == 3) {
                            new ImageUpload.FetchConstant().execute();
                            beforeProPicUploadValue = 0;
                            beforeBodyPicUploadValue = 0;
                            beforeOtherPicUploadValue = 0;
                            afterProPicUploadValue = 0;
                            afterBodyPicUploadValue = 0;
                            afterOtherPicUploadValue = 0;
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

            RequestBody body = RequestBody.create(JSON, strings[0]);
            Request request = new Request.Builder()
                    .url(strings[1])
                    .addHeader("Authorization", "Token token=" + token)
                    .post(body)
                    .build();
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
            if (s == null) {
                progress.cancel();
                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                //clearBitmapData();
                Log.i("constantval", "ImageUploadNextfetchval: " + s);
                Intent signupIntent;
//                signupIntent = new Intent(ImageUpload.this, RegistrationChoiceSelectionFirstPage.class);
                signupIntent = new Intent(ImageUpload.this, RegistrationPersonalInformation.class);
                signupIntent.putExtra("constants", s);
                startActivity(signupIntent);
                finish();
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
            Log.i("urldata", s + "");
            if (s == null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                /*if (progress.isShowing()) {
                    progress.dismiss();
                }*/
                //clearBitmapData();
                Log.i("constantval", "ImageUpdloadBackfetchval: " + s);
                startActivity(new Intent(ImageUpload.this, RegistrationOwnInfo.class).
                        putExtra("constants", s));
                finish();
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

        proPicBitmap = null;
        bodyPicBitmap = null;
        otherPicBitmap = null;

        proPicBase64 = "";
        bodyPicBase64 = "";
        otherPicBase64 = "";

        ImageCrop.cropImage=0;
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallary",
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

                } else if (items[item].equals("Choose from Gallary")) {
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
                    startActivity(i);

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
                    startActivity(i);

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
        proPicBitmap = null;
        bodyPicBitmap = null;
        otherPicBitmap = null;
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

}
