package com.m.shasho.mygymgroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.m.shasho.mygymgroup.MyFragment.PersonsFragment;
import com.m.shasho.mygymgroup.database.MyDataBase;
import com.m.shasho.mygymgroup.typePerson.TypeGyms;

public class InformationProfile extends AppCompatActivity {
private TextInputEditText et_name,et_date_start,et_propriety,et_tel,et_age,et_price,et_number_month,et_id;
private Button btn_add;
private Button btn_edit;
private RadioButton male,female;
private RadioGroup radioGroup;
private MenuItem edit;
private MenuItem delete;
private String add;
private String checkName;
private Toolbar toolbar;

 private MyDataBase dataBase;
    public static final int ADD_PERSON_RESULT_CODE = 2;
    public static final int EDIT_PERSON_RESULT_CODE = 3;
    public static final int DELETE_PERSON_RESULT_CODE = 4;
    public static final String DELETE_REQ_CODE = "delete";
    public static final String EDIT_REQ_CODE = "edit";
  private   String gen="male";//افتراضي هوة المحدد في حال ما غير شي ولا كبس شي لا تنفذ الدالة اذشيكيد
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_profile);
        toolbar=findViewById(R.id.toolbar_information);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        dataBase=MyDataBase.newInstance(this);
        et_id=findViewById(R.id.ev_add_id);
        et_name=findViewById(R.id.ev_add_name);
        et_age=findViewById(R.id.ev_add_age);
        et_tel=findViewById(R.id.ev_add_tel);
        et_date_start=findViewById(R.id.ev_add_date_start);
        et_number_month=findViewById(R.id.ev_add_number_month);
        et_propriety=findViewById(R.id.ev_add_propriety);
        et_price=findViewById(R.id.ev_add_price);
        radioGroup=findViewById(R.id.radio_group);
        male=findViewById(R.id.rb_male);
        female=findViewById(R.id.rb_female);
        btn_add=findViewById(R.id.btn_add);
        btn_edit=findViewById(R.id.btn_edit);
        btn_edit.setVisibility(View.GONE);
        Intent intent=getIntent();
        checkName=intent.getStringExtra(PersonsFragment.EDIT_ONEFRAGMENT);
        add=intent.getStringExtra(HomeActivity.ADD_REQ_CODE_HOME);

        if(checkName!=null)
        {
            PersonGym p;
//            try {
//                p = db.getPerson(checkName);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            p=dataBase.getPerson(checkName);
            if(p!=null)
            fillPersonToFields(p);

            disablefields();
        }else {


        if(male.isChecked()){
            male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        gen="male";
                        Toast.makeText(InformationProfile.this, "male", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InformationProfile.this, "female", Toast.LENGTH_SHORT).show();
                        gen="female";
                    }
                }
            }); }
       // Calendar calendar=Calendar.getInstance();
            final Intent i=getIntent();
final String a=i.getStringExtra(TypeGyms.NEXT_ADD_CODE);
final String stat=i.getStringExtra(TypeGyms.REQ_CODE);
et_propriety.setText(stat);
if(stat.equals(TypeGyms.REQ_CODE_PERSON_VALUE))
{ et_tel.setVisibility(View.GONE);
}
else {et_tel.setVisibility(View.VISIBLE);et_date_start.setVisibility(View.GONE);
    et_price.setVisibility(View.GONE);
    et_number_month.setVisibility(View.GONE); }
       // et_date_start.setText(calendar.get(Calendar.YEAR)+":"+calendar.get(Calendar.MONTH)+":"+calendar.get(Calendar.DATE));
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                if(a.equals("add")){
                    if(stat.equals(TypeGyms.REQ_CODE_PERSON_VALUE))
                    {
                        if( TextUtils.isEmpty(et_id.getText())){
                        et_id.setError( getResources().getString(R.string.error_id)); }
                    else
                    if( TextUtils.isEmpty(et_name.getText())){
                        et_name.setError( getResources().getString(R.string.error_name)); }
                    else if( TextUtils.isEmpty(et_age.getText())){
                        et_age.setError( getResources().getString(R.string.error_age)); }
                    else if( TextUtils.isEmpty(et_date_start.getText())){
                        et_date_start.setError( getResources().getString(R.string.error_propriety));}
                      else if( TextUtils.isEmpty(et_number_month.getText())){
                            et_number_month.setError( getResources().getString(R.string.error_date_start)); }
                        else if( TextUtils.isEmpty(et_price.getText())){
                            et_price.setError( getResources().getString(R.string.error_number_month)); }
                        else if( TextUtils.isEmpty(et_propriety.getText())){
                            et_propriety.setError( getResources().getString(R.string.error_price)); }
                        else
                        {
                            intent.putExtra(Key.ID, Integer.parseInt(et_id.getText().toString()));
                            intent.putExtra(Key.NAME, et_name.getText().toString());
                            intent.putExtra(Key.AGE, et_age.getText().toString());
                            intent.putExtra(Key.DATE_START, et_date_start.getText().toString());
                            intent.putExtra(Key.MONTH_NUMBER, et_number_month.getText().toString());
                            if(stat.equals(TypeGyms.REQ_CODE_ADMIN_VALUE))
                                intent.putExtra(Key.TEL, et_tel.getText().toString());

                            intent.putExtra(Key.PROPRIETY, i.getStringExtra(TypeGyms.REQ_CODE));
                            intent.putExtra(Key.PRICE, et_price.getText().toString());
                            intent.putExtra(Key.GENDER, gen);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }else {
                        if (TextUtils.isEmpty(et_id.getText())) {
                            et_id.setError(getResources().getString(R.string.error_id));
                        } else if (TextUtils.isEmpty(et_name.getText())) {
                            et_name.setError(getResources().getString(R.string.error_name));
                        } else if (TextUtils.isEmpty(et_age.getText())) {
                            et_age.setError(getResources().getString(R.string.error_age));
                        } else if (TextUtils.isEmpty(et_propriety.getText())) {
                            et_propriety.setError(getResources().getString(R.string.error_propriety));
                        } else {
                            intent.putExtra(Key.ID, Integer.parseInt(et_id.getText().toString()));
                            intent.putExtra(Key.NAME, et_name.getText().toString());
                            intent.putExtra(Key.AGE, et_age.getText().toString());

                            intent.putExtra(Key.TEL, et_tel.getText().toString());
                            intent.putExtra(Key.PROPRIETY, i.getStringExtra(TypeGyms.REQ_CODE));

                            intent.putExtra(Key.GENDER, gen);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }
        }});
    }}
    private void disablefields(){
        et_id.setEnabled(false);
        et_name.setEnabled(false);
        et_age.setEnabled(false);
        et_date_start.setEnabled(false);
        et_number_month.setEnabled(false);
        et_propriety.setEnabled(false);
        et_price.setEnabled(false);
        radioGroup.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_edit.setVisibility(View.GONE);
    }
    private void enabledfields(){
        et_id.setEnabled(true);
        et_name.setEnabled(true);
        et_age.setEnabled(true);
        et_date_start.setEnabled(true);
        et_number_month.setEnabled(true);
        et_propriety.setEnabled(true);
        et_price.setEnabled(true);
        btn_edit.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
    }
    private void clearfields(){
     et_id.setText("");
     et_name.setText("");
     et_age.setText("");
     et_date_start.setText("");
     et_number_month.setText("");
     et_price.setText("");
     et_tel.setText("");
    }
    private void fillPersonToFields(PersonGym p) {

    et_id.setText(p.getId()+"");
    et_name.setText(p.getName());
    et_age.setText(p.getAge());
    et_date_start.setText(p.getDate_start());
    et_tel.setText(p.getTel());
    et_number_month.setText(p.getMonth_number());
    et_propriety.setText(p.getPropriety());
    et_price.setText(p.getPrice());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.one_menu,menu);

        edit=menu.findItem(R.id.menu_edit);
        delete=menu.findItem(R.id.menu_delete);
        Intent intent=getIntent();
        String add=intent.getStringExtra(HomeActivity.ADD_REQ_CODE_HOME);
        if (add!=null)
        {
             edit.setVisible(false);
            delete.setVisible(false);
        }
  return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final Intent intent=getIntent();
        switch (item.getItemId()){
            case R.id.menu_delete:
            {

              if(dataBase.deletePerson(Integer.parseInt(et_id.getText().toString())))
                  Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                intent.putExtra(DELETE_REQ_CODE,"delete");
                setResult(DELETE_PERSON_RESULT_CODE,intent);
                finish();
                return true;
            }
            case R.id.menu_edit:
                {
                if(checkName!=null) {
                    enabledfields();
                    btn_add.setText("edit");
                    btn_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(et_id.getText())) {
                                et_id.setError(getResources().getString(R.string.error_id));
                            } else
                                if (TextUtils.isEmpty(et_name.getText())) {
                                et_name.setError(getResources().getString(R.string.error_name));
                            } else if (TextUtils.isEmpty(et_age.getText())) {
                                et_age.setError(getResources().getString(R.string.error_age));
                            } else if (TextUtils.isEmpty(et_date_start.getText())) {
                                et_date_start.setError(getResources().getString(R.string.error_date_start));
                            } else if (TextUtils.isEmpty(et_number_month.getText())) {
                                et_number_month.setError(getResources().getString(R.string.error_number_month));
                            } else if (TextUtils.isEmpty(et_price.getText())) {
                                et_price.setError(getResources().getString(R.string.error_price));
                            } else if (TextUtils.isEmpty(et_propriety.getText())) {
                                et_propriety.setError(getResources().getString(R.string.error_propriety));
                            } else if(et_propriety.getText().toString().equals("ادمن") | et_propriety.getText().toString().equals("لاعب"))
                            { if(female.isChecked()){
                                    gen="female";
                                     }else gen="male";
                                PersonGym p = new PersonGym();

                                p.setName(et_name.getText().toString());
                                p.setAge(et_age.getText().toString());
                                p.setMonth_number(et_number_month.getText().toString());
                                p.setPrice(et_price.getText().toString());
                                p.setPropriety(et_propriety.getText().toString());
                                p.setGender(gen);
                                p.setDate_start(et_date_start.getText().toString());
                                p.setId(Integer.parseInt(et_id.getText().toString()));
                                if(dataBase.upDatePerson(p,checkName)) {

                                    Toast.makeText(InformationProfile.this, "Edit Successfully", Toast.LENGTH_SHORT).show();
                                }

                                intent.putExtra(EDIT_REQ_CODE,"edit");
                                setResult(EDIT_PERSON_RESULT_CODE,intent);
                                finish();

                            }else  {
                                Toast.makeText(InformationProfile.this, "يجب ان تحوي الصلاحية", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    }return true;
                }
            }

        return false;
    }
}
