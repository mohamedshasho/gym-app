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

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

public class AdminGymAdapter extends RecyclerView.Adapter<AdminGymAdapter.AdminHolderGym> {
    private ArrayList<PersonGym> adminGyms=new ArrayList<>();
    private Context context;

    private onClickAdmin onClickAdmin;

    public AdminGymAdapter(ArrayList<PersonGym> adminGyms,Context context,onClickAdmin onClickAdmin) {
        this.context=context;

        this.adminGyms = adminGyms;
        this.onClickAdmin=onClickAdmin;
    }

    public ArrayList<PersonGym> getAdminGyms() {
        return adminGyms;
    }

    public void setAdminGyms(ArrayList<PersonGym> adminGyms) {
        this.adminGyms = adminGyms;
    }

    @NonNull
    @Override
    public AdminHolderGym onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_admin_gym,null,false);
        AdminHolderGym viewHolder=new AdminHolderGym(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHolderGym holder, int position) {

        PersonGym personGym=adminGyms.get(position);
        holder.bind(personGym.getId(),personGym.getName(),personGym.getAge(),personGym.getImage(),personGym.getPropriety(),personGym.getGender(),personGym.getTel());
        holder.name=personGym.getName();
    }

    @Override
    public int getItemCount() {
        return adminGyms.size();
    }

    class AdminHolderGym extends RecyclerView.ViewHolder {
        String  name;
        ImageView imageprofile;
        ImageView imagegender;
        TextView tv_id;
        ImageView imageback;
        TextView tv_name,tv_age,tv_propriety,tv_tel;
        public AdminHolderGym(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.custom_tv_id_admin);
            imageprofile=itemView.findViewById(R.id.costom_iv_profile_admin);
            imagegender=itemView.findViewById(R.id.costom_iv_gender_admin);
            imageback=itemView.findViewById(R.id.custom_iv_back_admin);
            tv_tel=itemView.findViewById(R.id.custom_tv_tel_admin);
            tv_name=itemView.findViewById(R.id.costom_tv_name_admin);
            tv_age=itemView.findViewById(R.id.costom_tv_age_admin);
            tv_propriety=itemView.findViewById(R.id.costom_tv_propriety_admin);
            imageprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickAdmin.setOnClickAdmin(name);
                    }
            });
            imageback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickAdmin.setOnClickAdmin(name);
                }
            });
        }
        public void bind(int id,String name,String age,String img,String propriety,String gender,String tel){
            if(!img.equals("")){
                byte[]  imageBytes = Base64.decode(img, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageprofile.setImageBitmap(decodedImage);}
            else imageprofile.setImageResource(R.drawable.defult_iv_profile);

            if (gender.equals("male"))
                imagegender.setImageResource(R.drawable.ic_male);
            else imagegender.setImageResource(R.drawable.ic_female);
            tv_id.setText(id+"");
            tv_name.setText(name);
            tv_age.setText(age);
            tv_tel.setText(tel);
            tv_propriety.setText(propriety);
        }
    }

}
