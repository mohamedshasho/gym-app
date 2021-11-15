package com.m.shasho.mygymgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.m.shasho.mygymgroup.database.MyDataBase;
import com.parse.ParseException;


import dmax.dialog.SpotsDialog;

public class BeginHomePage extends AppCompatActivity {
private Button btn_search;
private EditText et_search;
    private TextView visit;
private MyDataBase dataBase;
private SharedPreferences sp;
private SharedPreferences.Editor editor;
private CheckInternetConnection ch;
private String name;
private String username;
    AlertDialog dialog;
    private ProgressDialog progressDialog;
private Intent intent;
public static final String HOME="home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_home_page);

        btn_search=findViewById(R.id.btn_search);
//        visit=findViewById(R.id.visit_tv);
//        visit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(getBaseContext(),HomeActivity.class);
//                startActivity(intent);
//            }
//        });
        et_search=findViewById(R.id.et_search_username);
        sp= getSharedPreferences(HOME,MODE_PRIVATE);
        progressDialog=new ProgressDialog(this);
        dialog= new SpotsDialog.Builder()
                .setTheme(R.style.Custom)//الستايل يدوي من غرايدل
                //.setMessage(getResources().getString(R.string.loading))//او وضعها في التصميم في ملف ستايل
                .setContext(this).build();

        ch=new CheckInternetConnection(this);
        dataBase=MyDataBase.newInstance(this);
        intent=new Intent(this,HomeActivity.class);
        editor=sp.edit();
        name=sp.getString(HOME,"not");
        if(!name.equals("not")){
            intent.putExtra(HOME,name);
            startActivity(intent);
            finish();}
        else {
        MyTask task=new MyTask();
        task.execute();}


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_search.getText().toString().isEmpty())
                    et_search.setError(getResources().getString(R.string.error_name));
                else {
                    username = et_search.getText().toString();
                    PersonGym p = dataBase.getPersonByName(username);
                    String Uname = "";
                    try {
                        Uname = p.getName();
                        editor.putString(HOME, Uname);
                        editor.apply();
                        isEmpty(Uname);
                    } catch (Exception e) {
                        Toast.makeText(BeginHomePage.this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public void isEmpty(String username){
        name=sp.getString(HOME,"not");
        if(name.equals(username)){
            intent.putExtra(HOME,name);
            startActivity(intent);
            finish();
        }else {
        }
    }
    class MyTask extends AsyncTask<String,Integer,Void>
    {
        @Override
        protected void onPreExecute() {//قبل التنفيذ
            super.onPreExecute();
            dialog.show();
//            progressDialog.setMessage(getResources().getString(R.string.loading));
//            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {//بعد التنفيذ
            super.onPostExecute(aVoid);
            dialog.dismiss();
            //progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {//التقدم

            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(String... strings) {
            boolean c=ch.isConnectingToInternet();
            if (c) {
              dataBase.FillDatabaseAdmin();
              dataBase.FillDatabasePerson();
            }else {
                Toast.makeText(getBaseContext(), "no internet", Toast.LENGTH_SHORT).show();
            }
            publishProgress();
            return null;
        }
    }
}
