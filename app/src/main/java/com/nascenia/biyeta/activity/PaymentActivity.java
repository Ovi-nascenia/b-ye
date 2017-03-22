package com.nascenia.biyeta.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

public class PaymentActivity extends CustomActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account_recharge);
        setUpToolBar(getString(R.string.account_recharge_title),this);

        WebView mWebView = (WebView) findViewById(R.id.money_send_confirmation);

        mWebView.loadUrl("file:///android_asset/webtext.html");
        mWebView.getSettings().setDefaultFontSize(20);

    }

    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }
}
