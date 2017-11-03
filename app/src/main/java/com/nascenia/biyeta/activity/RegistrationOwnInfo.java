package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.model.newuserprofile.Image;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationOwnInfo extends AppCompatActivity {
    LinearLayout castReligion;
    public static TextView castReligionText;
    public EditText details;
    public Button next;
    public TextView noNumberEmail;
    public EditText editTextOwn;
    ImageView back;
    public static String castValue,religionValue,otherReligion,otherCast;
    public int flag=0;

    public static int castReligionOwn = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        castValue = "";
        religionValue = "";
        otherReligion = "";
        otherCast = "";
        final Intent intent = getIntent();
        final String constants = intent.getStringExtra("constants");
        try {
            JSONObject jsonObject = new JSONObject(constants);
            Toast.makeText(RegistrationOwnInfo.this,constants,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_registration_own_info);
        castReligion = (LinearLayout) findViewById(R.id.castReligion);
        details = (EditText) findViewById(R.id.edit_text_own);

        details.addTextChangedListener(mTextEditorWatcher);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next = (Button) findViewById(R.id.next);
        noNumberEmail = (TextView) findViewById(R.id.no_number_email);
        editTextOwn = (EditText)findViewById(R.id.edit_text_own);


        castReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                castReligionOwn = 1;
                Intent intent = new Intent(RegistrationOwnInfo.this, PopUpCastReligion.class );
                intent.putExtra("constants",constants);
                startActivity(intent);
            }
        });

        castReligionText = (TextView) findViewById(R.id.religion_cast_text_view);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationOwnInfo.this, RegistrationChoiceSelectionFirstPage.class ));
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
                if(flag==1)
                {
                    noNumberEmail.setVisibility(View.VISIBLE);
                    flag=0;
                }
                editTextOwn.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editTextOwn.setSingleLine();
            }
            else if(s.length()>=4&&Character.isDigit(s.charAt(s.length()-1))&&Character.isDigit(s.charAt(s.length()-2))&&Character.isDigit(s.charAt(s.length()-3))&&Character.isDigit(s.charAt(s.length()-4))){
                flag = 1;
                next.setVisibility(View.INVISIBLE);
                noNumberEmail.setVisibility(View.VISIBLE);
                String text = editTextOwn.getText().toString();
                editTextOwn.setText(text.substring(0, text.length() - 1));
                editTextOwn.setSelection(editTextOwn.getText().length());
            }

            else if(s.length()>=1&&s.charAt(s.length()-1)=='@'){
                flag = 1;
                next.setVisibility(View.INVISIBLE);
                noNumberEmail.setVisibility(View.VISIBLE);
                String text = editTextOwn.getText().toString();
                editTextOwn.setText(text.substring(0, text.length() - 1));
                editTextOwn.setSelection(editTextOwn.getText().length());
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
}
