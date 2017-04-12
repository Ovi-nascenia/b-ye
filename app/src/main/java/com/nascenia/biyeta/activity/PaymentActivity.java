package com.nascenia.biyeta.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PaymentActivity extends CustomActionBarActivity {

    TextView balanceAmountTextView;
    TextView profileVisitNumberTextView;
    TextView detailsPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_recharge);
        setUpToolBar(getString(R.string.account_recharge_title), this);


        balanceAmountTextView = (TextView) findViewById(R.id.current_balance);
        profileVisitNumberTextView = (TextView) findViewById(R.id.account_recharge_descrip1);
        detailsPayment = (TextView) findViewById(R.id.recharge_amountTV);
        detailsPayment.setText(" কার্ড-এর মাধ্যমে টাকা প্রদানের জন্য  বিয়েটার ওয়েবসাইটে (www.biyeta.com) লগইন করা অবস্থায় এই লিঙ্ক-এ ক্লিক করুন- http://biyeta.com/payments/new ");

// Makes the textView's Phone and URL (hyperlink) select and go.
        Linkify.addLinks(detailsPayment, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS);


        WebView mWebView = (WebView) findViewById(R.id.money_send_confirmation);

        mWebView.loadUrl("file:///android_asset/webtext.html");
        mWebView.getSettings().setDefaultFontSize(20);

        new LoadAccoutBalance().execute("");

    }

    OkHttpClient client = new OkHttpClient();

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }


    public class LoadAccoutBalance extends AsyncTask<String, String, String> {
        ProgressDialog progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(PaymentActivity.this);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("testtt", s);
            if (progressBar.isShowing()) {

                progressBar.dismiss();
            }


            if (s == null) {
                Utils.ShowAlert(PaymentActivity.this, getString(R.string.no_internet_connection));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("balance")) {

                        balanceAmountTextView.setText("বর্তমান ব্যালেন্স " + Utils.convertEnglishYearDigittoBangla(jsonObject.getInt("balance")) + " টাকা");
                        profileVisitNumberTextView.setText(jsonObject.getString("total_request"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Response response;
            SharePref sharePref = new SharePref(PaymentActivity.this);
            String token = sharePref.get_data("token");
            Request request = null;

            request = new Request.Builder()
                    .url("http://test.biyeta.com/api/v1/payments/balance")
                    .addHeader("Authorization", "Token token=" + token)
                    .build();

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                return Jobject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
