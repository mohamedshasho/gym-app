package com.m.shasho.mygymgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import com.m.shasho.mygymgroup.MyFragment.AdminFragment;
import com.m.shasho.mygymgroup.database.MyDataBase;
public class About_Coach extends AppCompatActivity {
Toolbar toolbar;
private MyDataBase dataBase;
private String name;
private TextView txt_name;
private TextView txt_age;
private TextView txt_gender;
private TextView txt_tel;
private TextView txt_message;
private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_coach);
        toolbar=findViewById(R.id.toolCoach);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        dataBase=MyDataBase.newInstance(this);
        Intent intent=getIntent();
        name=intent.getStringExtra(AdminFragment.INFO_COACH_CODE);
        PersonGym p=dataBase.getAdmin(name);
        if(p!=null){
        txt_name=findViewById(R.id.nameCoach);
        txt_age=findViewById(R.id.ageCoach);
        txt_gender=findViewById(R.id.genderCoach);
        txt_tel=findViewById(R.id.telCoach);
        txt_message=findViewById(R.id.infoCoach);
        image=findViewById(R.id.imageCoach);

        txt_name.setText(p.getName());
        txt_age.setText(p.getAge());
        txt_gender.setText(p.getGender());
        txt_tel.setText(p.getTel());
        txt_message.setText(p.getMessage());
        String img=p.getImage();
        if(img.equals(""))image.setImageResource(R.drawable.defult_iv_profile);
        else {
                byte[]  imageBytes = Base64.decode(img, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                image.setImageBitmap(decodedImage);
        }
        }

    }
}
