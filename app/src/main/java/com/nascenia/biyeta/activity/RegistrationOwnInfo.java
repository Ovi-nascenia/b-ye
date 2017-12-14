package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.model.newuserprofile.Image;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class RegistrationOwnInfo extends AppCompatActivity{
    LinearLayout castReligion;
    public static TextView castReligionText;
    public EditText details;
    public Button next;
    public TextView noNumberEmail;
    public EditText editTextOwn;
    ImageView back;
    public static String castValue = "",religionValue = "",otherReligion = "",otherCast = "";
    public int flag=0;
    OkHttpClient client;

    public static int castReligionOwn = 0;
    private ProgressDialog progress;
    private SharePref sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_own_info);
        sharePref = new SharePref(RegistrationOwnInfo.this);

        castValue = "";
        religionValue = "";
        otherReligion = "";
        otherCast = "";

        progress = new ProgressDialog(RegistrationOwnInfo.this);
        progress.setMessage(getResources().getString(R.string.progress_dialog_message));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        final Intent intent = getIntent();
        client = new OkHttpClient();
        final String constants = intent.getStringExtra("constants");
        try {
            JSONObject jsonObject = new JSONObject(constants);
            //Toast.makeText(RegistrationOwnInfo.this,constants,Toast.LENGTH_LONG).show();
            Log.i("constantdata: ",constants);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       Log.i("classnames",getClass().getSimpleName());

        castReligion = (LinearLayout) findViewById(R.id.castReligion);
        details = (EditText) findViewById(R.id.edit_text_own);
        if(Login.gender.equals("male")){
            details.setHint("আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্রী-পক্ষ আগ্রহী হয়।");
        }
        else if(Login.gender.equals("female")){
            details.setHint("আপনার সম্পর্কে বিস্তারিত লিখুন যাতে পাত্র-পক্ষ আগ্রহী হয়।");
        }

        details.addTextChangedListener(mTextEditorWatcher);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePref.set_data("token", "key");
                startActivity(new Intent(RegistrationOwnInfo.this,Login.class));
                finish();
            }
        });

        next = (Button) findViewById(R.id.next);
        noNumberEmail = (TextView) findViewById(R.id.no_number_email);
        editTextOwn = (EditText)findViewById(R.id.edit_text_own);


        castReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){



                castReligionOwn = 1;
                Intent intent = new Intent(RegistrationOwnInfo.this, PopUpCastReligion.class );
                intent.putExtra("constants",constants);
                startActivity(intent);
            }
        });

        castReligionText = (TextView) findViewById(R.id.religion_cast_text_view);

       /* details.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String response = new StringBuilder().append("{")
                            .append("\"current_mobile_sign_up_step\":")
                            .append(Login.currentMobileSignupStep)
                            .append(",")
                            .append("\"religion\":")
                            .append(religionValue)
                            .append(",")
                            .append("\"cast\":")
                            .append(castValue)
                            .append(",")
                            .append("\"other_cast\":")
                            .append("\"")
                            .append(otherCast)
                            .append("\"")
                            .append(",")
                            .append("\"other_religion\":")
                            .append("\"")
                            .append(otherReligion)
                            .append("\"")
                            .append(",")
                            .append("\"about_yourself\":")
                            .append("\"")
                            .append(details.getText().toString())
                            .append("\"")
                            .append("}")
                            .toString();
                    new RegistrationOwnInfo.SendOwnInfo().execute(response,Utils.SEND_INFO);
                    return true;
                }
                return false;
            }
        });*/

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("values ","religionValue: "+religionValue);
                Log.i("values ","castValue: "+castValue);
                Log.i("values ","otherCast: "+otherCast);
                Log.i("values ","otherReligion: "+otherReligion);


                if(castReligionText.getText().toString().equalsIgnoreCase(getString(R.string.religion_pick_textview_title)))
                {
                    Toast.makeText(getBaseContext(),getString(R.string.religion_pick_message),Toast.LENGTH_LONG).show();
                    return;
                }

                if(castReligionText.getText().toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(),getString(R.string.religion_pick_message),Toast.LENGTH_LONG).show();
                    return;
                }

                String response = new StringBuilder().append("{")
                        .append("\"current_mobile_sign_up_step\":")
                        .append(Login.currentMobileSignupStep)
                        .append(",")
                        .append("\"religion\":")
                        .append(religionValue)
                        .append(",")
                        .append("\"cast\":")
                        .append(castValue)
                        .append(",")
                        .append("\"other_cast\":")
                        .append("\"")
                        .append(otherCast)
                        .append("\"")
                        .append(",")
                        .append("\"other_religion\":")
                        .append("\"")
                        .append(otherReligion)
                        .append("\"")
                        .append(",")
                        .append("\"about_yourself\":")
                        .append("\"")
                        .append(details.getText().toString())
                        .append("\"")
                        .append("}")
                        .toString();
               new SendOwnInfo().execute(response,Utils.SEND_INFO);
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if(s.length()>= 50 && s.length()<=4000 ){
                next.setVisibility(View.VISIBLE);
                noNumberEmail.setVisibility(View.INVISIBLE);
                details.setImeOptions(EditorInfo.IME_ACTION_DONE);
                if(flag==1)
                {
                    noNumberEmail.setVisibility(View.VISIBLE);
                    flag=0;
                }
                editTextOwn.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editTextOwn.setSelection(editTextOwn.getText().length());
            }
            else if(s.length()>=4&&Character.isDigit(s.charAt(s.length()-1))&&Character.isDigit(s.charAt(s.length()-2))&&Character.isDigit(s.charAt(s.length()-3))&&Character.isDigit(s.charAt(s.length()-4))){
                flag = 1;
                next.setVisibility(View.INVISIBLE);
                noNumberEmail.setVisibility(View.VISIBLE);
                String text = editTextOwn.getText().toString();
                editTextOwn.setText(text.substring(0, text.length() - 1));
                editTextOwn.setSelection(editTextOwn.getText().length());
                details.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            }

            else if(s.length()>=1&&s.charAt(s.length()-1)=='@'){
                flag = 1;
                next.setVisibility(View.INVISIBLE);
                noNumberEmail.setVisibility(View.VISIBLE);
                String text = editTextOwn.getText().toString();
                editTextOwn.setText(text.substring(0, text.length() - 1));
                editTextOwn.setSelection(editTextOwn.getText().length());
                details.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            }

            else{
                next.setVisibility(View.INVISIBLE);
                noNumberEmail.setVisibility(View.INVISIBLE);
                if(flag==1)
                {
                    noNumberEmail.setVisibility(View.VISIBLE);
                    flag=0;
                }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    class SendOwnInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
                progress.show();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                if(progress.isShowing())
                    progress.dismiss();
                Utils.ShowAlert(RegistrationOwnInfo.this, getString(R.string.no_internet_connection));
            }
            else{
                try {
                   // progress.cancel();
                    JSONObject jsonObject=new JSONObject(s);
                    Log.e("Response",s);

                    if(jsonObject.has("errors"))
                    {
                        if(progress.isShowing())
                            progress.dismiss();

                        Toast.makeText(RegistrationOwnInfo.this,
                                jsonObject.getJSONObject("errors").getString("detail"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Intent intent = new Intent(RegistrationFamilyInfoFirstPage.this,Login.class);
                        //startActivity(intent);
                        new  FetchConstant().execute();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(String... strings){
            SharePref sharePref = new SharePref(RegistrationOwnInfo.this);
            final String token = sharePref.get_data("token");

            Log.e("Test", strings[0]+" "+strings[1]);

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,strings[0]);
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
            }catch(IOException e){
                e.printStackTrace();
//                application.trackEception(e, "GetResult/doInBackground", "Search_Filter", e.getMessage().toString(), mTracker);
            }
            return responseString;
        }
    }

    public class FetchConstant extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s == null){
                if(progress.isShowing())
                    progress.dismiss();
                Utils.ShowAlert(RegistrationOwnInfo.this, getString(R.string.no_internet_connection));
            }
            else
            {
                if(progress.isShowing())
                    progress.dismiss();
                Log.i("constantval","Regisowninfofetchval: "+s);

                Intent signupIntent;
                signupIntent = new Intent(RegistrationOwnInfo.this, ImageUpload.class);
                signupIntent.putExtra("constant",s);
                startActivity(signupIntent);
            }
        }

        @Override
        protected String doInBackground(String... parameters){
          //  Login.currentMobileSignupStep+=1;
            final String token = sharePref.get_data("token");
            Request request = new Request.Builder()
                    //.url(Utils.STEP_CONSTANT_FETCH + Login.currentMobileSignupStep)
                    .url(Utils.STEP_CONSTANT_FETCH + 3)
                    .addHeader("Authorization", "Token token=" + token)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.e(Utils.LOGIN_DEBUG, responseString);
                response.body().close();
                return responseString;
            } catch (Exception e){
                e.printStackTrace();
//                application.trackEception(e, "LoginRequest/doInBackground", "Login", e.getMessage().toString(), mTracker);
                return null;
            }
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharePref.set_data("token", "key");
        startActivity(new Intent(RegistrationOwnInfo.this,Login.class));
        finish();
    }
}
