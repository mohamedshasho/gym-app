package com.m.shasho.mygymgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {
    EditText ed_Email,ed_Password,ed_username,ed_confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ed_username=findViewById(R.id.signup_name);
        ed_Password=findViewById(R.id.signup_password);
        ed_confirm_password=findViewById(R.id.signup_confirm_password);
        ed_Email=findViewById(R.id.signup_email);
    }

    public void signup_login(View view) {
        if( TextUtils.isEmpty(ed_username.getText())){
            ed_username.setError(getResources().getString(R.string.error_name));
        }else
        if( TextUtils.isEmpty(ed_Email.getText())){
            ed_Email.setError(getResources().getString(R.string.error_email));
        }else if( TextUtils.isEmpty(ed_Password.getText())){
            ed_Password.setError(getResources().getString(R.string.error_password));
        }else  if( TextUtils.isEmpty(ed_confirm_password.getText())){
            ed_confirm_password.setError( getResources().getString(R.string.error_con_password));
        } else  if(TextUtils.isEmpty(ed_Password.getText().toString()) || ed_Password.length() < 6)
        {
            ed_Password.setError(getResources().getString(R.string.error_password_length));
            return;
        }
        else//هام
            if(!ed_Password.getText().toString().equals(ed_confirm_password.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.error_password_same), Toast.LENGTH_LONG).show();
            }
            else
            {  final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage(getResources().getString(R.string.loading));
                progressDialog.show();
                final ParseUser user = new ParseUser();
// Set the user's username and password, which can be obtained by a forms
                user.setUsername(ed_username.getText().toString());
                user.setEmail(ed_Email.getText().toString());
                user.setPassword(ed_Password.getText().toString());
                //شان يمسح الفراغات اذا وجد وتخزينها بدون فراغات في البداية والنهاية
                user.put(Key.NAME,ed_username.getText().toString().trim());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        progressDialog.dismiss();
                        if (e == null) {
                            Intent intent=new Intent(SignUp.this,HomeActivity.class);
                            intent.putExtra(MainActivity.USERNAME,user.getUsername());
                            startActivity(intent);
                            finish();
                        } else {
                            ParseUser.logOut();
                            Toast.makeText(SignUp.this,e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

    }
}
