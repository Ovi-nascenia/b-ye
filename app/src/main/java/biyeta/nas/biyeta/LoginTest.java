package biyeta.nas.biyeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by user on 1/5/2017.
 */

public class LoginTest extends AppCompatActivity implements View.OnClickListener {


    ImageView icon;
    LinearLayout new_account;
    EditText et_password,et_user_name;
    Button b_submit,b_facebook_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login);

        //set all id//
        set_id();

        ///load large icon with glide
        Glide.with(this)
                .load(R.drawable.icon)
                .into(icon);

    }

    void set_id()
    {


        new_account=(LinearLayout)findViewById(R.id.new_accunt_test);
        new_account.setOnClickListener(this);

        et_user_name=(EditText)findViewById(R.id.login_email);
        et_password=(EditText)findViewById(R.id.login_password);

        b_submit=(Button)findViewById(R.id.login_submit);
        b_submit.setOnClickListener(this);

        b_facebook_login=(Button)findViewById(R.id.fb_button);
        b_facebook_login.setOnClickListener(this);


        icon=(ImageView)findViewById(R.id.icon_view) ;
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        switch (id)
        {
            case R.id.fb_button:

                ///call facebook api for login
                break;
            case R.id.login_submit:
                ///connect the server for checking username and password
                //call a asytask for network operation
                Toast.makeText(LoginTest.this,"Cool",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginTest.this,HomeScreen.class));

                break;
            case R.id.new_accunt_test:
                //open a link in a brawer
                break;

        }

    }
}
