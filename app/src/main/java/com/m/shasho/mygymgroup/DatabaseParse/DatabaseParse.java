package com.m.shasho.mygymgroup.DatabaseParse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.m.shasho.mygymgroup.Key;
import com.m.shasho.mygymgroup.PersonGym;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.m.shasho.mygymgroup.MyFragment.ProfileFragment.imageprofile;

public class DatabaseParse {
    public static final String NAME_DATABASE="GameScore";
  private ArrayList<PersonGym> personGyms=new ArrayList<>();
  private ArrayList<PersonGym> adminGyms=new ArrayList<>();
  private   PersonGym p;
  Context context;
  private int id;
  private String name;
  private String age;
  private String numMonth;
  private String startDate;
  private String price;
  private String propriety;
  private String tel;
  private String gender;
  private String img;

   private String image;
  private static DatabaseParse databaseParse;
    public DatabaseParse(Context context) {
        this.context=context;
    }
    public static DatabaseParse getInstance(Context context) {
        if (databaseParse == null) {//انتبه يساوي يساوي لاني اول مرة بكون فارغ شان ينشئ اول مرة فقط
            databaseParse = new DatabaseParse(context);
        }
        return databaseParse;
    }

    public ArrayList<PersonGym> getAllPersonGyms() {
        return personGyms;
    }

    public void setPersonGyms(ArrayList<PersonGym> personGyms) {

        this.personGyms = personGyms;
    }

    public ArrayList<PersonGym> getAdminGyms() {
        return adminGyms;
    }

    public void setAdminGyms(ArrayList<PersonGym> adminGyms) {
        this.adminGyms = adminGyms;
    }

    public void addPersonGyms(PersonGym personGym) {
        personGyms.add(personGym);

        int new_id = personGym.getId();
        String new_name=personGym.getName();
        String new_age=personGym.getAge();
        String new_month=personGym.getMonth_number();
        String new_startDate=personGym.getDate_start();
        String new_gender=personGym.getGender();
        String new_img = personGym.getImage();
        String new_propriety=personGym.getPropriety();
        String new_price=personGym.getPrice();
        addNewPerson(new_id,new_name,new_age,new_month,new_startDate,new_gender,new_propriety,new_price,new_img);
    }

    public int getSizePerson(){
       return personGyms.size();
    }

    public void addNewPerson(int id,String name,String age,String month,String startDate,String gender,String propriety,String price,String img){
        ParseObject gameScore = new ParseObject(NAME_DATABASE);
        gameScore.put(Key.ID, id);
        gameScore.put(Key.AGE, age);
        gameScore.put(Key.NAME, name);
        gameScore.put(Key.MONTH_NUMBER, month);
        gameScore.put(Key.DATE_START, startDate);
        gameScore.put(Key.PROPRIETY, propriety);
        gameScore.put(Key.IMG, img);
        gameScore.put(Key.GENDER, gender);
        gameScore.put(Key.PRICE, price);

        gameScore.saveInBackground();
    }
    public void RefreshPerson() throws ParseException{
        personGyms.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(NAME_DATABASE);
        query.include(Key.ID);
        query.whereEqualTo(Key.PROPRIETY,"لاعب");
        List<ParseObject> objects = query.find();
        setListPerson(objects);
        query.cancel();
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList,ParseException e) {
//                if (e == null) {
//                   // for (int i=0;i<scoreList.size();i++){
//                        setList(scoreList);
////                        p.setId(scoreList.get(i).getInt(Key.ID));
////                        p.setName(scoreList.get(i).getString(Key.NAME));
////                        p.setAge(scoreList.get(i).getString(Key.AGE));
////                        p.setDate_start(scoreList.get(i).getString(Key.DATE_START));
////                        p.setMonth_number(scoreList.get(i).getString(Key.MONTH_NUMBER));
////                        p.setPropriety(scoreList.get(i).getString(Key.PROPRIETY));
////                        p.setPrice(scoreList.get(i).getString(Key.PRICE));
////                        p.setGender(scoreList.get(i).getString(Key.GENDER));
////                        // personGyms.add(p);
////                       // addP(p);
////                        personGyms.add(p);
////                        id= (int) scoreList.get(i).getNumber(Key.ID);
////                        name=scoreList.get(i).getString(Key.NAME);
////                        age=scoreList.get(i).getString(Key.AGE);
////                        startDate=scoreList.get(i).getString(Key.DATE_START);
////                        numMonth=scoreList.get(i).getString(Key.MONTH_NUMBER);
////                        propriety=scoreList.get(i).getString(Key.PROPRIETY);
////                        price=scoreList.get(i).getString(Key.PRICE);
////                        gender= scoreList.get(i).getString(Key.GENDER);
////                        p=new PersonGym(id,name,age,0,startDate,numMonth,price,propriety,gender);
//
//                 //   }
//                }
//                else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
    }
    public void RefreshAdmin() throws ParseException {
        adminGyms.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(NAME_DATABASE);
        query.include(Key.ID);
        query.whereEqualTo(Key.PROPRIETY, "ادمن");
        List<ParseObject> objects = query.find();
        setListAdmin(objects);
        query.cancel();
    }

    public void setListAdmin(List<ParseObject> parseObjects) {

        for (ParseObject pa : parseObjects) {

            id= (int) pa.getNumber(Key.ID);
            name=pa.getString(Key.NAME);
            age=pa.getString(Key.AGE);
            img=pa.getString(Key.IMG);
            propriety=pa.getString(Key.PROPRIETY);
            gender= pa.getString(Key.GENDER);
            tel=pa.getString(Key.TEL);
            p=new PersonGym(id, name, age, img, propriety, gender,tel);
            adminGyms.add(p);
        }
    }
public void setListPerson(List<ParseObject> parseObjects) {

    for (ParseObject pa : parseObjects) {

        id= (int) pa.getNumber(Key.ID);
        name=pa.getString(Key.NAME);
        age=pa.getString(Key.AGE);
        startDate=pa.getString(Key.DATE_START);
        numMonth=pa.getString(Key.MONTH_NUMBER);
        img=pa.getString(Key.IMG);
        propriety=pa.getString(Key.PROPRIETY);
        price=pa.getString(Key.PRICE);
        gender= pa.getString(Key.GENDER);
        p=new PersonGym(id,name,age,img,startDate,numMonth,price,propriety,gender);
        personGyms.add(p);
    }
}
public ArrayList<PersonGym> getPersons(String name) throws ParseException {
        personGyms.clear();
    ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
    query.whereEqualTo(Key.NAME,name);
    query.include(Key.ID);
    List<ParseObject> objects = query.find();

    setListPerson(objects);
    query.cancel();
    return personGyms;
}
    public PersonGym getPerson(String name) throws ParseException {
        PersonGym person;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.NAME, name);

       // query.include(Key.ID);
        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {

            id = (int) pa.getNumber(Key.ID);
            this.name = pa.getString(Key.NAME);
            age = pa.getString(Key.AGE);
            startDate = pa.getString(Key.DATE_START);
            numMonth = pa.getString(Key.MONTH_NUMBER);
            img=pa.getString(Key.IMG);
            propriety = pa.getString(Key.PROPRIETY);
            price = pa.getString(Key.PRICE);
            gender = pa.getString(Key.GENDER);
             person = new PersonGym(id, this.name, age, img, startDate, numMonth, price, propriety, gender);
            return person;
        }
        query.cancel();
        return null;
    }
    //يحذف كل الاسماء لازم اساويه عل رقم
    public void deletePerson(int id) throws ParseException {
       // personGyms.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.ID, id);

        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {
            pa.deleteEventually();
            pa.deleteInBackground();

        }
        query.cancel();
    }
    public void EditPerson(String name,PersonGym p) throws ParseException {
        //personGyms.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.NAME, name);

        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {
            pa.put(Key.ID, p.getId());
            pa.put(Key.AGE, p.getAge());
            pa.put(Key.NAME, p.getName());
            pa.put(Key.MONTH_NUMBER, p.getMonth_number());
            pa.put(Key.DATE_START, p.getDate_start());
            pa.put(Key.PROPRIETY, p.getPropriety());
            pa.put(Key.GENDER, p.getGender());
            pa.put(Key.PRICE, p.getPrice());
            pa.saveEventually();
            pa.saveInBackground();

        }
        query.cancel();
    }
    public ArrayList<PersonGym> getAdminsGyms() throws ParseException {
        adminGyms.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.PROPRIETY,"ادمن");
        query.include(Key.ID);
        List<ParseObject> objects = query.find();
            PersonGym admin;
        for (ParseObject pa : objects) {

            id = (int) pa.getNumber(Key.ID);
            name = pa.getString(Key.NAME);
            age = pa.getString(Key.AGE);
            tel  =  pa.getString(Key.TEL);
            img=pa.getString(Key.IMG);
            propriety = pa.getString(Key.PROPRIETY);

            gender = pa.getString(Key.GENDER);
            admin = new PersonGym(id, name, age, img, propriety, gender,tel);
            adminGyms.add(admin);

        }
        query.cancel();
        return adminGyms;
    }


    public void addAdminGyms(PersonGym adminGym) {
        adminGyms.add(adminGym);

        int new_id = adminGym.getId();
        String new_name=adminGym.getName();
        String new_age=adminGym.getAge();
        String propriety=adminGym.getPropriety();
        String new_gender=adminGym.getGender();
        String tel=adminGym.getTel();
        String img=adminGym.getImage();
        addNewAdmin(new_id,new_name,new_age,new_gender,propriety,img,tel);
    }
    public void addNewAdmin(int id,String name,String age,String gender,String propriety,String img,String tel){
        ParseObject gameScore = new ParseObject(NAME_DATABASE);
        gameScore.put(Key.ID, id);
        gameScore.put(Key.AGE, age);
        gameScore.put(Key.TEL,tel);
        gameScore.put(Key.NAME, name);
        gameScore.put(Key.GENDER, gender);
        gameScore.put(Key.PRICE, "");
        gameScore.put(Key.MONTH_NUMBER, "");
        gameScore.put(Key.DATE_START, "");
        if(img.equals(""))
        gameScore.put(Key.IMG, "");
        else gameScore.put(Key.IMG, img);
        gameScore.put(Key.PROPRIETY, propriety);
        gameScore.saveInBackground();


    }
    public Bitmap getPersonImage(String name) throws ParseException {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.NAME, name);
        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {
            String img=pa.getString(Key.IMG);
            //decode base64 string to image
          byte[]  imageBytes = Base64.decode(img, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageprofile.setImageBitmap(decodedImage);
//            ParseFile file = (ParseFile) pa.get(Key.IMAGE);
//            file.getDataInBackground(new GetDataCallback() {
//                @Override
//                public void done(byte[] data, ParseException e) {
//                    if(data.length>0 &&e==null){
//                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    if(imageprofile.getWidth()>0&&imageprofile.getHeight()>0){
//                    imageprofile.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageprofile.getWidth(),
//                            imageprofile.getHeight(), false));}
//                    }
//                }
//            });

            return decodedImage;
        }
        query.cancel();
        return null;
    }
    public void setPersonImage(String name,String img) throws ParseException {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.NAME, name);
        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bitMapData = stream.toByteArray();
//            Toast.makeText(context, bitMapData.length+"", Toast.LENGTH_SHORT).show();
//            ParseFile file=new ParseFile("new.png",bitMapData);
            pa.put(Key.IMG,img);
            pa.saveInBackground();

        }
        query.cancel();
    }
    public String getImageString(int id) throws ParseException{

        ParseQuery<ParseObject> query = ParseQuery.getQuery(DatabaseParse.NAME_DATABASE);
        query.whereEqualTo(Key.ID, id);
        List<ParseObject> objects = query.find();
        for (ParseObject pa : objects) {
            image=pa.getString(Key.IMG);
            return image;

        }
        return image;
    }
}
