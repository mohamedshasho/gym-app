package com.m.shasho.mygymgroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.m.shasho.mygymgroup.MyFragment.PagerAdapter;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;


public class PersonGymAdapter extends RecyclerView.Adapter<PersonGymAdapter.HolderGym> {
    private  ArrayList<PersonGym> personGyms=new ArrayList<>();
     private Context context;

   private onClickPerson onClickPerson;

    public PersonGymAdapter(ArrayList<PersonGym> personGyms,Context context,onClickPerson onClickPerson) {
        this.context=context;
        //this.personGyms=new ArrayList<>();
        this.personGyms = personGyms;
        this.onClickPerson=onClickPerson;
    }
    public void clear() {
        personGyms.clear();
        notifyDataSetChanged();
    }
    public void addAll(ArrayList<PersonGym> p) {
        personGyms=p;
        notifyDataSetChanged();
    }
    public  ArrayList<PersonGym> getPersonGyms(Context context) {
            return personGyms;
    }

    public void setPersonGyms(ArrayList<PersonGym> personGyms) {
        this.personGyms = personGyms;

    }

    @NonNull
    @Override
    public HolderGym onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_person_gym,null,false);
        HolderGym viewHolder=new HolderGym(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  HolderGym holder, int position) {
      PersonGym personGym=personGyms.get(position);
      //holder.imagegender.setImageResource();//لاضافة الصورة المختارة هامة
      holder.bind(personGym.getId(),personGym.getName(),personGym.getAge(),personGym.getDate_start(),personGym.getMonth_number(),personGym.getPropriety(),personGym.getPrice(),personGym.getGender(),personGym.getImage());
      holder.name=personGym.getName();

    }

    @Override
    public int getItemCount() {
            return personGyms.size();
    }

    class HolderGym extends RecyclerView.ViewHolder {
        String  name;
ImageView imageprofile;
ImageView imagegender;
TextView tv_id;
ImageView imageback;
TextView tv_name,tv_age,tv_dateStart,tv_monthNumber,tv_propriety,tv_price;
        public HolderGym(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.custom_tv_id);
            imageprofile=itemView.findViewById(R.id.costom_iv_profile);
            imagegender=itemView.findViewById(R.id.costom_iv_gender);
            imageback=itemView.findViewById(R.id.custom_iv_back);
            tv_name=itemView.findViewById(R.id.costom_tv_name);
            tv_age=itemView.findViewById(R.id.costom_tv_age);
            tv_dateStart=itemView.findViewById(R.id.costom_tv_start_date);
            tv_monthNumber=itemView.findViewById(R.id.costom_tv_number_month);
            tv_propriety=itemView.findViewById(R.id.costom_tv_propriety);
            tv_price=itemView.findViewById(R.id.costom_tv_price);
            imageprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPerson.setOnClickPerson(name);
                }
            });
            imageback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPerson.setOnClickPerson(name);
                }
            });
        }
        public void bind(int id,String name,String age ,String dateStart,String monthNumber,String propriety,String price,String gender,String img){
       tv_id.setText(id+"");
            //Toast.makeText(context, img, Toast.LENGTH_LONG).show();
            if(!img.equals("")){
            byte[]  imageBytes = Base64.decode(img, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageprofile.setImageBitmap(decodedImage);}
            else imageprofile.setImageResource(R.drawable.defult_iv_profile);

       if (gender.equals("male"))
           imagegender.setImageResource(R.drawable.ic_male);
       else imagegender.setImageResource(R.drawable.ic_female);
       tv_name.setText(name);
       tv_age.setText(age);
       tv_dateStart.setText(dateStart);
       tv_monthNumber.setText(monthNumber);
       tv_propriety.setText(propriety);
       tv_price.setText(price);
        }

    }
}
