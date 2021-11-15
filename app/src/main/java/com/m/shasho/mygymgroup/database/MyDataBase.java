package com.m.shasho.mygymgroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.m.shasho.mygymgroup.CheckInternetConnection;
import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.m.shasho.mygymgroup.PersonGym;
import com.parse.ParseException;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {
   private ArrayList<PersonGym> personGyms=new ArrayList<>();
   private ArrayList<PersonGym> adminGyms=new ArrayList<>();
    private DatabaseParse dbparse;
    public static final String DB_NAME="gyms_db";
    public static final int DB_VERSION=7;
    public static final String GYM_TABLE_NAME="gyms";
    public static final String GYM_ID="id";
    public static final String GYM_NAME="name";
    public static final String GYM_AGE="age";
    public static final String GYM_DATESTART="datestart";
    public static final String GYM_NUMMONTH="nummonth";
    public static final String GYM_PROPRIETY="propriety";
    public static final String GYM_PRICE="price";
    public static final String GYM_TEL="tel";
    public static final String GYM_MESSAGE="message";
    public static final String GYM_IMAGE="image";
    public static final String GYM_GENDER="gender";
    private String img;
    private Context context;
    private CheckInternetConnection ch;
private static MyDataBase instance;
    public MyDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        dbparse=DatabaseParse.getInstance(context);
        ch=new CheckInternetConnection(context);
        this.context=context;
    }
public static MyDataBase newInstance(Context context){

        if(instance==null){
            instance=new MyDataBase(context);
        }
        return instance;
}
    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL("CREATE TABLE gyms(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , age TEXT, datestart TEXT, nummonth TEXT, propriety TEXT, price TEXT, gender TEXT, image TEXT, tel TEXT, message TEXT)");
        db.execSQL("CREATE TABLE gyms("+GYM_ID+" INTEGER primary key, "+GYM_NAME+" TEXT not null, "+GYM_AGE+" TEXT not null, "+GYM_DATESTART+" TEXT, "
        +GYM_NUMMONTH+" TEXT, "+GYM_PROPRIETY+" TEXT not null, "+GYM_PRICE+" TEXT, "+GYM_IMAGE+" TEXT, "+GYM_GENDER+" TEXT not null, "
        +GYM_TEL+" TEXT, "+GYM_MESSAGE+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS gyms");
        onCreate(db);

    }
    public void FillDatabasePerson(){
        personGyms.clear();

        try {
            dbparse.RefreshPerson();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        personGyms=dbparse.getAllPersonGyms();
        for(PersonGym p : personGyms){
            insertPerson(p);
        }

    }
    public void insertPerson(PersonGym p){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GYM_ID,p.getId());
        values.put(GYM_NAME,p.getName());
        values.put(GYM_AGE,p.getAge());
        values.put(GYM_DATESTART,p.getDate_start());

        values.put(GYM_NUMMONTH,p.getMonth_number());
        values.put(GYM_PROPRIETY,p.getPropriety());
        values.put(GYM_PRICE,p.getPrice());
        String img=p.getImage();

        if(!img.equals(""))
        values.put(GYM_IMAGE,img);
       else values.put(GYM_IMAGE,"");
        values.put(GYM_GENDER,p.getGender());
        //values.put(GYM_TEL,p.getTel());
       // values.put(GYM_MESSAGE,p.getMessage());
        db.insert(GYM_TABLE_NAME,null,values);

    }
    public void FillDatabaseAdmin(){
        adminGyms.clear();
        try {
            dbparse.RefreshAdmin();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adminGyms=dbparse.getAdminGyms();
        for(PersonGym p : adminGyms){
            insertAdmin(p);
        }
    }
public void deletePersonAll(){
    SQLiteDatabase db= this.getWritableDatabase();
    String args[]={"لاعب"};
    long res=db.delete(GYM_TABLE_NAME,GYM_PROPRIETY+"=?",args);
}
    public void deleteAdminAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        String args[]={"ادمن"};
        long res=db.delete(GYM_TABLE_NAME,GYM_PROPRIETY+"=?",args);
    }
    public void insertAdmin(PersonGym p){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GYM_ID,p.getId());
        values.put(GYM_NAME,p.getName());
        values.put(GYM_AGE,p.getAge());

        values.put(GYM_PROPRIETY,p.getPropriety());

        String img=p.getImage();
        if(!img.equals(""))
            values.put(GYM_IMAGE,img);
        else values.put(GYM_IMAGE,"");
        values.put(GYM_GENDER,p.getGender());
        values.put(GYM_TEL,p.getTel());
         values.put(GYM_MESSAGE,p.getMessage());
        db.insert(GYM_TABLE_NAME,null,values);

    }
    public long getPersonCount(){
        SQLiteDatabase db=getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db,GYM_TABLE_NAME);
    }
    public ArrayList<PersonGym> getAllPersons(){
        ArrayList<PersonGym> personGyms=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_PROPRIETY+" like ?",new String[]{"%"+"لاعب"+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String startdate=cursor.getString(cursor.getColumnIndex(GYM_DATESTART));
                String numMonth=cursor.getString(cursor.getColumnIndex(GYM_NUMMONTH));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                String price=cursor.getString(cursor.getColumnIndex(GYM_PRICE));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym p=new PersonGym(id,name,age,img,startdate,numMonth,price,propriety,gender);
                personGyms.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return personGyms;
    }
    public ArrayList<PersonGym> getAllAdmin(){
        ArrayList<PersonGym> AdminGyms=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_PROPRIETY+" like ?",new String[]{"%"+"ادمن"+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String tel=cursor.getString(cursor.getColumnIndex(GYM_TEL));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym p=new PersonGym(id,name,age,img,propriety,gender,tel);
                AdminGyms.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return AdminGyms;
    }
    //لازم الحذف والتغديل بلنسبة لل رقم
    public boolean upDatePerson(PersonGym p,String name){
       // dbparse=DatabaseParse.getInstance(context);
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GYM_ID,p.getId());
        values.put(GYM_NAME,p.getName());
        values.put(GYM_AGE,p.getAge());
        values.put(GYM_DATESTART,p.getDate_start());

        values.put(GYM_NUMMONTH,p.getMonth_number());
        values.put(GYM_PROPRIETY,p.getPropriety());
        values.put(GYM_PRICE,p.getPrice());
        boolean c=ch.isConnectingToInternet();
        if (c) {
            try {
                 img=dbparse.getImageString(p.getId());

                if(!img.equals(""))
                values.put(GYM_IMAGE,img);
                else  values.put(GYM_IMAGE,"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
        }

        values.put(GYM_GENDER,p.getGender());
        String args[]={p.getId()+""};
        int res=db.update(GYM_TABLE_NAME,values,"id=?",args);
        try {
            dbparse.EditPerson(name,p);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res>0;
    } //لازم الحذف والتعديل بلنسبة لل رقم  اغيرن يعدين
    public  boolean deletePerson(int  id){
        boolean c=ch.isConnectingToInternet();

        if (c) {
            try {
                dbparse.deletePerson(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(context, "no Internet", Toast.LENGTH_SHORT).show();
            return false;
        }

        SQLiteDatabase db=getWritableDatabase();
        String [] args={String.valueOf(id)};
        long res=db.delete(GYM_TABLE_NAME,"id=?",args);
        return res>0;

    }
    public ArrayList<PersonGym> getPersons(String namep){
        ArrayList<PersonGym> personGyms=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_NAME+" like ?",new String[]{"%"+namep+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String startdate=cursor.getString(cursor.getColumnIndex(GYM_DATESTART));
                String numMonth=cursor.getString(cursor.getColumnIndex(GYM_NUMMONTH));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                if(propriety.equals("ادمن"))continue;
                String price=cursor.getString(cursor.getColumnIndex(GYM_PRICE));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym p=new PersonGym(id,name,age,img,startdate,numMonth,price,propriety,gender);
                personGyms.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return personGyms;
    }
    public ArrayList<PersonGym> getPersons(int searchID){
        ArrayList<PersonGym> personGyms=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_ID+"=?",new String[]{searchID+""});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String startdate=cursor.getString(cursor.getColumnIndex(GYM_DATESTART));
                String numMonth=cursor.getString(cursor.getColumnIndex(GYM_NUMMONTH));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                if(propriety.equals("ادمن"))continue;
                String price=cursor.getString(cursor.getColumnIndex(GYM_PRICE));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym p=new PersonGym(id,name,age,img,startdate,numMonth,price,propriety,gender);
                personGyms.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return personGyms;
    }
    public PersonGym getPerson(String namep){
        PersonGym p = null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_NAME+" like ?",new String[]{"%"+namep+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String startdate=cursor.getString(cursor.getColumnIndex(GYM_DATESTART));
                String numMonth=cursor.getString(cursor.getColumnIndex(GYM_NUMMONTH));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                String price=cursor.getString(cursor.getColumnIndex(GYM_PRICE));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                 p=new PersonGym(id,name,age,img,startdate,numMonth,price,propriety,gender);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return p;
    }
    public PersonGym getPersonByName(String i){
        PersonGym p = null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_NAME+"=?",new String[]{i});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String startdate=cursor.getString(cursor.getColumnIndex(GYM_DATESTART));
                String numMonth=cursor.getString(cursor.getColumnIndex(GYM_NUMMONTH));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                String price=cursor.getString(cursor.getColumnIndex(GYM_PRICE));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                p=new PersonGym(id,name,age,img,startdate,numMonth,price,propriety,gender);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return p;
    }
    public PersonGym getAdmin(String namep){
        PersonGym p = null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_NAME+" like ?",new String[]{"%"+namep+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));

                String tel=cursor.getString(cursor.getColumnIndex(GYM_TEL));
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                p=new PersonGym(id,name,age,img,propriety,gender,tel);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return p;
    }
    public ArrayList<PersonGym> getAdmins(int searchID){
        ArrayList<PersonGym> Admin=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_ID+"=?",new String[]{searchID+""});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String tel=cursor.getString(cursor.getColumnIndex(GYM_TEL));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                if(propriety.equals("لاعب"))continue;

                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym  p=new PersonGym(id,name,age,img,propriety,gender,tel);
                Admin.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return Admin;
    }
    public ArrayList<PersonGym> getAdmins(String searchName){
        ArrayList<PersonGym> Admin=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+GYM_TABLE_NAME+" where "+GYM_NAME+" Like ?",new String[]{"%"+searchName+"%"});
        if(cursor!=null &&cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(GYM_ID));
                String name=cursor.getString(cursor.getColumnIndex(GYM_NAME));
                String age=cursor.getString(cursor.getColumnIndex(GYM_AGE));
                String tel=cursor.getString(cursor.getColumnIndex(GYM_TEL));
                String propriety=cursor.getString(cursor.getColumnIndex(GYM_PROPRIETY));
                if(propriety.equals("لاعب"))continue;
                String img=cursor.getString(cursor.getColumnIndex(GYM_IMAGE));
                String gender=cursor.getString(cursor.getColumnIndex(GYM_GENDER));
                PersonGym  p=new PersonGym(id,name,age,img,propriety,gender,tel);
                Admin.add(p);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return Admin;
    }
}
