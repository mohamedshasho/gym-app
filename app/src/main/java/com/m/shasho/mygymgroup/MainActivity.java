package com.m.shasho.mygymgroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    EditText ed_Username,ed_Password;
    public static final String USERNAME="username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_Username=findViewById(R.id.main_username);
        ed_Password=findViewById(R.id.main_password);

        if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            intent.putExtra(USERNAME,ParseUser.getCurrentUser().getUsername());
            startActivity(intent);
            finish();
        }
    }
    public void main_login(View view) {
        if( TextUtils.isEmpty(ed_Username.getText())){
            ed_Username.setError( getResources().getString(R.string.error_name));
        }else if( TextUtils.isEmpty(ed_Password.getText())){
            ed_Password.setError(getResources().getString(R.string.error_password));
        }else {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            ParseUser.logInInBackground(ed_Username.getText().toString(),ed_Password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    progressDialog.dismiss();
                    if (parseUser != null) {

                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        intent.putExtra(USERNAME,parseUser.getUsername());
                        startActivity(intent);
                        finish();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
    public void signup(View view) {
        Intent intent=new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }

    public void forget(View view) {
        Intent intent=new Intent(MainActivity.this, ResetPassword.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "تم تغيير اللغة", Toast.LENGTH_SHORT).show();
    }
}
