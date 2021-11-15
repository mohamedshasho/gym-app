package com.m.shasho.mygymgroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.m.shasho.mygymgroup.Dialogs.PicFragment;
import com.m.shasho.mygymgroup.Dialogs.TimerFragment;
import com.m.shasho.mygymgroup.MyFragment.MyTab;
import com.m.shasho.mygymgroup.MyFragment.PersonsFragment;
import com.m.shasho.mygymgroup.MyFragment.PagerAdapter;
import com.m.shasho.mygymgroup.MyFragment.ProfileFragment;
import com.m.shasho.mygymgroup.MyFragment.HomeFragment;
import com.m.shasho.mygymgroup.MyFragment.AdminFragment;

import com.m.shasho.mygymgroup.database.MyDataBase;
import com.m.shasho.mygymgroup.typePerson.TypeGyms;
import com.parse.ParseException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  implements ProfileFragment.onFragmentListener, HomeFragment.setOnClickTimer
,TimerFragment.OnFragmentStart,PicFragment.OnFragmentPictureDeleteListener,PicFragment.OnFragmentPictureGalleryListener{
    public static final int ADD_REQ_CODE_HOME_Res =2 ;
    public static final String ADD_REQ_CODE_HOME ="add" ;
    public static final int ADD_IMAGE_PROFILE_CODE = 5;
    Toolbar toolbar;
TabLayout tabLayout;
ViewPager viewPager;
FloatingActionButton fab;
FragmentManager personGymAdapter;
 ArrayList<PersonGym> personGyms ;
 ArrayList<PersonGym> adminGyms ;
    ArrayList<Fragment> fragments;
  private   SearchView searchView;
  private MenuItem menuItem;
  private PersonGym p;
  private DatabaseParse databaseParse;
private PagerAdapter adapter;
   private CatalogTab catalogTab;
    private  CatalogTab catalogTab2;
    private CatalogTab catalogTab3;
    private CatalogTab catalogTab4;
   private ArrayList<MyTab> tabs;
private Intent intent;
private String userName;
private MyDataBase dataBase;
 private CheckInternetConnection ch;
 private PicFragment picFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));


        setSupportActionBar(toolbar);
        dataBase=MyDataBase.newInstance(this);
        databaseParse=DatabaseParse.getInstance(this);
        ch=new CheckInternetConnection(this);
        
        intent=getIntent();
        userName=intent.getStringExtra(BeginHomePage.HOME);
        tabLayout=findViewById(R.id.home_tabLayout);
        viewPager=findViewById(R.id.home_viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);//لتسريع التنقل ؟ ابحث عنها الرقم يكون نص عدد تاب؟

         catalogTab=new CatalogTab("",R.drawable.ic_home);
         catalogTab2=new CatalogTab("",R.drawable.ic_gavel24dp);
         catalogTab3=new CatalogTab("",R.drawable.ic_tools);
         catalogTab4=new CatalogTab("",R.drawable.ic_profile);

        personGymAdapter=getSupportFragmentManager();
        tabs=new ArrayList<>();
        tabs.add(new MyTab(catalogTab3,new HomeFragment()));
        tabs.add(new MyTab(catalogTab,new PersonsFragment()));
        tabs.add(new MyTab(catalogTab2,new AdminFragment()));
        tabs.add(new MyTab(catalogTab4,ProfileFragment.newInstance(userName)));

        adapter=new PagerAdapter(personGymAdapter,tabLayout.getTabCount(),userName);


        adapter.setTabs(tabs);
        viewPager.setAdapter(adapter);


fab=findViewById(R.id.home_fab);

fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent intent=new Intent(HomeActivity.this, TypeGyms.class);
       intent.putExtra(ADD_REQ_CODE_HOME,"add");

       // Toast.makeText(HomeActivity.this,ParseUser.getCurrentUser() , Toast.LENGTH_SHORT).show();
       startActivityForResult(intent,ADD_REQ_CODE_HOME_Res);
    }
});

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_gym_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_coach_gym);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tools);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_profile);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                 if( tab.getPosition()==1||tab.getPosition()==2)
                 {menuItem.setVisible(true);
                     fab.setVisibility(View.VISIBLE);

                 }else {menuItem.setVisible(false);
                     fab.setVisibility(View.GONE);
                 }
              }

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==ADD_REQ_CODE_HOME_Res){
            if (data != null) {
                //Intent i=data.
//                Resources res = getResources();
//                Drawable drawable = res.getDrawable(R.drawable.fitness);
//                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] bitMapData = stream.toByteArray();
//                ParseFile file=new ParseFile("p.png",bitMapData);
                int id=data.getIntExtra(Key.ID,0);
                String name= data.getStringExtra(Key.NAME);
                String age=data.getStringExtra(Key.AGE);
                String dateStart=data.getStringExtra(Key.DATE_START);
                String numMonth=data.getStringExtra(Key.MONTH_NUMBER);
                String propriety=data.getStringExtra(Key.PROPRIETY);
                String price=data.getStringExtra(Key.PRICE);
                String tel=data.getStringExtra(Key.TEL);
                String Gen=data.getStringExtra(Key.GENDER);

                    if(propriety.equals("ادمن")) {
                        p = new PersonGym(id, name, age, "",propriety,Gen,tel);
                        databaseParse.addAdminGyms(p);
                    }else {p = new PersonGym(id, name, age, "", dateStart, numMonth, price, propriety, Gen);
                        databaseParse.addPersonGyms(p);}

            }
        }
        if(resultCode==InformationProfile.DELETE_PERSON_RESULT_CODE||resultCode==InformationProfile.EDIT_PERSON_RESULT_CODE){


        }
        if(requestCode==ADD_IMAGE_PROFILE_CODE) {
            if (data != null) {
                Uri uri = data.getData();
                boolean c = ch.isConnectingToInternet();
                if (c) {
                    try {

                        ProfileFragment.imageprofile.setImageURI(uri);

                        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), uri);
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 220, 220, true);
                        //Bitmap bb=  Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false);
                        // ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        // resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        //byte[] bitMapData = stream.toByteArray();

                        String img = encodeImage(resized);
                        MyTask task =new MyTask();
                        task.execute(img);

                    } catch (Exception e) {
                    }
                }else Toast.makeText(getBaseContext(), "no internet", Toast.LENGTH_SHORT).show();

            }
        }
    }
    ArrayList<PersonGym> person;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tow_menu,menu);
        //عمل مؤشر على العنصر الموجود في مينيو
        menuItem=menu.findItem(R.id.main_search);
        menuItem.setVisible(false);
        MenuItem logout=menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences sp =getSharedPreferences(BeginHomePage.HOME,MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString(BeginHomePage.HOME, "not");
                editor.apply();
                Intent intent=new Intent(getBaseContext(),BeginHomePage.class);
                startActivity(intent);
                return false;
            }
        });
        searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        //لتفعيل زر البحث بجانب حقل البحث

        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(tabLayout.getSelectedTabPosition()==1) {
                    try {
                        int id= Integer.parseInt(query);
                        person=dataBase.getPersons(id);
                        PersonsFragment.SearchPerson(person);
                    }catch (Exception e){
                        person=dataBase.getPersons(query);
                        PersonsFragment.SearchPerson(person);
                    }

                }else {
                    try {

                        int id= Integer.parseInt(query);
                        person=dataBase.getAdmins(id);
                        AdminFragment.SearchAdmin(person);
                    }catch (Exception e){
                        person=dataBase.getAdmins(query);
                        AdminFragment.SearchAdmin(person);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                person=dataBase.getAllPersons();
                PersonsFragment.SearchPerson(person);
                person=dataBase.getAllAdmin();
                AdminFragment.SearchAdmin(person);
                return false;
            }
        });
        MenuItem timer=menu.findItem(R.id.menu_Timer);
timer.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TimerFragment timerFragment=TimerFragment.newInstance();
        timerFragment.show(getSupportFragmentManager(),null);
        return false;
    }
});
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
//          if(item.getItemId()==R.id.logout){
//    Intent intent=new Intent(this,BeginHomePage.class);
//    startActivity(intent);
//}


    }

    @Override
    public void onfragmentIntraction(String name) {
         picFragment=PicFragment.newInstance();
         picFragment.show(getSupportFragmentManager(),null);


    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void OnClickTimer() {
        Toast.makeText(this, "Timer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTimer() {
        Toast.makeText(this, "onStartTimer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnPictureGalleryListener() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,ADD_IMAGE_PROFILE_CODE);
        picFragment.dismiss();
    }

    @Override
    public void setOnPictureDeleteListener() {
        boolean c=ch.isConnectingToInternet();
        if (c) {
            try {
                databaseParse.setPersonImage(userName, "");
                ProfileFragment.imageprofile.setImageDrawable(getResources().getDrawable(R.drawable.defult_iv_profile));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show();
         picFragment.dismiss();
    }

//    @Override
//    public void onStopTimer() {
//
//    }
//
//    @Override
//    public void onCancelTimer() {
//        Toast.makeText(this, "onCancelTimer", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPauseTimer() {
//
//    }

    class MyTask extends AsyncTask<String,Integer,Void>
    {
        @Override
        protected void onPreExecute() {//قبل التنفيذ
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {//بعد التنفيذ
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {//التقدم

            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(String... strings) {
            boolean c=ch.isConnectingToInternet();
            if (c) {

                try {
                    databaseParse.setPersonImage(userName, strings[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PersonGym p = null;
                try {
                    p = databaseParse.getPerson(userName);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dataBase.upDatePerson(p, userName);
            }else {
   Toast.makeText(getBaseContext(), "no internet", Toast.LENGTH_SHORT).show();
            }

            publishProgress();
            return null;
        }
    }
}
