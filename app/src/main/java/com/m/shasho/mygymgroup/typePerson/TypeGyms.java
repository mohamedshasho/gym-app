package com.m.shasho.mygymgroup.typePerson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.m.shasho.mygymgroup.HomeActivity;
import com.m.shasho.mygymgroup.InformationProfile;
import com.m.shasho.mygymgroup.Key;
import com.m.shasho.mygymgroup.PersonGym;
import com.m.shasho.mygymgroup.R;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

public class TypeGyms extends AppCompatActivity {
    public static final String REQ_CODE="admin";
    public static final String REQ_CODE_ADMIN_VALUE="ادمن";
    public static final String REQ_CODE_PERSON_VALUE="لاعب";
    public static final String NEXT_ADD_CODE="add";
    public static final int REQ_CODE_RESULRE=2;
RadioButton rb_admin,rb_person;
Button btn_next;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_gyms);
        rb_admin=findViewById(R.id.rb_type_gym_admin);
        rb_person=findViewById(R.id.rb_type_gym_person);
        btn_next=findViewById(R.id.btn_next);
        Intent i=getIntent();

         intent=new Intent(getBaseContext(), InformationProfile.class);
        intent.putExtra(NEXT_ADD_CODE,"add");
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(rb_admin.isChecked()){
                    intent.putExtra(REQ_CODE,REQ_CODE_ADMIN_VALUE);
                    startActivityForResult(intent,REQ_CODE_RESULRE);

                }
                else if (rb_person.isChecked()){
                    intent.putExtra(REQ_CODE,REQ_CODE_PERSON_VALUE);
                    startActivityForResult(intent,REQ_CODE_RESULRE);

                }else Toast.makeText(TypeGyms.this, "You are need choose one!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_RESULRE && data!=null){
           setResult(RESULT_OK,data);
           finish();

        }
    }


}
