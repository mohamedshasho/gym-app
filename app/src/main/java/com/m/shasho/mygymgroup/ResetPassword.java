package com.m.shasho.mygymgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ResetPassword extends AppCompatActivity {
Button reset_password_btn;
EditText reset_password_ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        reset_password_ed=findViewById(R.id.reset_password_ed);
        reset_password_btn=findViewById(R.id.reset_password_btn);
        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( TextUtils.isEmpty(reset_password_ed.getText()))
                    reset_password_ed.setError( getResources().getString(R.string.error_email));
                else {
                    ParseUser.requestPasswordResetInBackground(reset_password_ed.getText().toString(), new RequestPasswordResetCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // An email was successfully sent with reset instructions.
                                Toast.makeText(ResetPassword.this, getResources().getString(R.string.send_reset_email), Toast.LENGTH_SHORT).show();
                            } else {
                                // Something went wrong. Look at the ParseException to see what's up.
                                Toast.makeText(ResetPassword.this, getResources().getString(R.string.send_reset_email_error), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
