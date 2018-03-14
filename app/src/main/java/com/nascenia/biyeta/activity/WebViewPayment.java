package com.nascenia.biyeta.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.appdata.SharePref;
import com.nascenia.biyeta.utils.Utils;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class WebViewPayment extends AppCompatActivity {
    private WebView recharge_webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_payment);

        //initiate webview with browser client
        recharge_webview=(WebView)findViewById(R.id.webview_recharge);
        recharge_webview.setWebViewClient(new MyBrowser());
        recharge_webview.getSettings().setLoadsImagesAutomatically(true);
        recharge_webview.getSettings().setJavaScriptEnabled(true);
        recharge_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //Get user authentication token and generate headr
        SharePref sharePref = new SharePref(WebViewPayment.this);
        String token=sharePref.get_data("token");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("authorization", token);
        headers.put("authorization", "Token " + "token="+token);
        //Get request by web view
        recharge_webview.loadUrl(Utils.Base_URL+"/api/v1/sign_in_from_app",headers);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("/mobile_payment_complete"))
            {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(WebViewPayment.this);
//                alertBuilder.setCancelable(true);
//                alertBuilder.setTitle("Upgrade successful");
//                alertBuilder.setMessage("You have upgraded your account successfully");
//                alertBuilder.setPositiveButton(android.R.string.yes,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                            Log.i("web view closed", "PaymentPage: broadcast webview closed");
//                            sendBroadcast(new Intent(PaymentActivity.ACTION_WEBVIEW_CLOSED));
//                        }
//                    });
//                AlertDialog alert = alertBuilder.create();
//                alert.show();
//                sendBroadcast(new Intent(PaymentActivity.ACTION_WEBVIEW_CLOSED));
                Intent intent = getIntent();
//                intent.putExtra("payment", true);
                setResult(RESULT_OK,intent);
                finish();  // close activity
                // Send broadcast to service
//                Log.i("web view closed", "PaymentPage: broadcast webview closed");

            }
            else
                view.loadUrl(url);
            return true;
        }
    }
}
