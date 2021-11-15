package com.m.shasho.mygymgroup;

import android.widget.ImageView;

import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class PersonGym{
    private int id;
    private String name;
    private  String age;
    private String image;
    private  String date_start;
    private  String month_number;
    private  String price;
    private   String propriety;
    private   String gender;
    private   String tel;
    private  String message;

    public PersonGym() {
    }

    public PersonGym(int id, String name, String age, String image, String propriety, String gender, String tel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.propriety = propriety;
        this.gender = gender;
        this.tel = tel;
    }

    public PersonGym(int id, String name, String age, String date_start, String month_number, String price, String propriety, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.date_start = date_start;
        this.month_number = month_number;
        this.price = price;
        this.propriety = propriety;
        this.gender = gender;
    }

    public PersonGym(int id, String name, String age, String image, String date_start, String month_number, String price, String propriety, String gender) {
        this.id=id;
        this.name = name;
        this.age = age;
        this.image = image;

        this.date_start = date_start;
        this.month_number = month_number;
        this.price = price;
        this.propriety = propriety;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getMonth_number() {
        return month_number;
    }

    public void setMonth_number(String month_number) {
        this.month_number = month_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPropriety() {
        return propriety;
    }

    public void setPropriety(String propriety) {
        this.propriety = propriety;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
