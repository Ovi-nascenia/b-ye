package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ImageUpload extends AppCompatActivity {
    OkHttpClient client;

    int numberOfImage = 0;

    public static int beforeProPicUploadValue = 0, beforeBodyPicUploadValue = 0, beforeOtherPicUploadValue = 0;
    public static int afterProPicUploadValue = 0, afterBodyPicUploadValue = 0, afterOtherPicUploadValue = 0;

    LinearLayout beforeProPicUpload, beforeBodyPicUpload, beforeOtherPicUpload, proPicChange, bodyPicChange, otherPicChange, permissionLayout;
    FrameLayout afterProPicUpload, afterBodyPicUpload, afterOtherPicUpload;
    ImageView proPic, bodyPic, otherPic, back;
    public static Bitmap proPicBitmap, bodyPicBitmap, otherPicBitmap;
    Button yesButton, noButton;

    public static String proPicBase64, bodyPicBase64, otherPicBase64;

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

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

        beforeBodyPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBodyPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });

        beforeProPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });

        beforeOtherPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });


        bodyPicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBodyPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });
        proPicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeProPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });
        otherPicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeOtherPicUploadValue = 1;
                startActivity(new Intent(ImageUpload.this, ImageChoose.class));
            }
        });

        afterProPicUpload = (FrameLayout) findViewById(R.id.after_pro_pic_upload);
        afterBodyPicUpload = (FrameLayout) findViewById(R.id.after_body_pic_upload);
        afterOtherPicUpload = (FrameLayout) findViewById(R.id.after_other_pic_upload);

        proPic = (ImageView) findViewById(R.id.pro_pic);
        bodyPic = (ImageView) findViewById(R.id.body_pic);
        otherPic = (ImageView) findViewById(R.id.other_pic);

        permissionLayout = (LinearLayout) findViewById(R.id.permission);

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
                Log.i("picdata",proPic);

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
        if (afterProPicUploadValue == 1) {
            proPic.setImageBitmap(proPicBitmap);
            beforeProPicUpload.setVisibility(View.GONE);
            afterProPicUpload.setVisibility(View.VISIBLE);
        }
        if (afterBodyPicUploadValue == 1) {
            bodyPic.setImageBitmap(bodyPicBitmap);
            beforeBodyPicUpload.setVisibility(View.GONE);
            afterBodyPicUpload.setVisibility(View.VISIBLE);
        }
        if (afterOtherPicUploadValue == 1) {
            otherPic.setImageBitmap(otherPicBitmap);
            beforeOtherPicUpload.setVisibility(View.GONE);
            afterOtherPicUpload.setVisibility(View.VISIBLE);
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
                    progress.cancel();
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("Response", s);
                    if (jsonObject.has("errors")) {
                        jsonObject.getJSONObject("errors").getString("detail");
                        Toast.makeText(ImageUpload.this,
                                jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                    } else {
                        numberOfImage++;
                        if (numberOfImage == 3) {
                            new ImageUpload.FetchConstant().execute();

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
                Utils.ShowAlert(ImageUpload.this, getString(R.string.no_internet_connection));
            } else {
                Intent signupIntent;
                signupIntent = new Intent(ImageUpload.this, RegistrationChoiceSelectionFirstPage.class);
                signupIntent.putExtra("constants", s);
                startActivity(signupIntent);
                finish();
            }
        }

        @Override
        protected String doInBackground(String... parameters) {
            Login.currentMobileSignupStep += 1;
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
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
            Request request = new Request.Builder()
                    .url(Utils.STEP_CONSTANT_FETCH + 2)
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
                if (progress.isShowing()) {
                    progress.dismiss();
                }

                startActivity(new Intent(ImageUpload.this, RegistrationOwnInfo.class).
                        putExtra("constants", s));
                finish();
            }
        }
    }
}
