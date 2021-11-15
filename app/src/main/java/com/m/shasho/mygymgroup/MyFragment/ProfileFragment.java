package com.m.shasho.mygymgroup.MyFragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.m.shasho.mygymgroup.PersonGym;
import com.m.shasho.mygymgroup.R;
import com.m.shasho.mygymgroup.database.MyDataBase;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
private  PersonGym p ;
private ImageView iv;
private String name;
private onFragmentListener listener;
private MyDataBase dataBase;
public static ImageView imageprofile;
    public ProfileFragment() {

    }
public static ProfileFragment newInstance(String name){
        Bundle b=new Bundle();
        b.putString("name",name);

        ProfileFragment fragment=new ProfileFragment();
        fragment.setArguments(b);
        return fragment;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // db=DatabaseParse.getInstance(getContext());
        Bundle bundle=getArguments();
        if(bundle!=null){
            name=bundle.getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_profile, container, false);
        p=dataBase.getPerson(name);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataBase=MyDataBase.newInstance(context);
        if(context instanceof onFragmentListener){
            listener= (onFragmentListener) context;
        }else throw new ClassCastException("You activity does not implement onFragmentClickListener");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv=view.findViewById(R.id.edit_iv_profile);
        imageprofile=view.findViewById(R.id.iv_profile);
        TextView id=view.findViewById(R.id.tv_id_profile);
        TextView username=view.findViewById(R.id.tv_name_profile);
        TextView age=view.findViewById(R.id.tv_age_profile);
        TextView startdate=view.findViewById(R.id.tv_date_start_profile);
        TextView numMonth=view.findViewById(R.id.tv_number_month_profile);
        TextView price=view.findViewById(R.id.tv_price_profile);
        TextView propriety=view.findViewById(R.id.tv_propriety_profile);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onfragmentIntraction(name);
            }
        });

        if(p!=null){ //نحطها لاني لازم يكون اسم تسجيل الدخول نفس اسم اللاعب لحتا يلاقي بيانات عند الدخول ما يوقف البرنامج
        id.setText(p.getId()+"");
            username.setText(p.getName());
        age.setText(p.getAge());
        startdate.setText(p.getDate_start());
        numMonth.setText(p.getMonth_number());
        price.setText(p.getPrice());
        propriety.setText(p.getPropriety());
        if (!p.getImage().equals("")){
            byte[]  imageBytes = Base64.decode(p.getImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageprofile.setImageBitmap(decodedImage);}
        }
    }
    public interface onFragmentListener{
        void onfragmentIntraction(String name);
    }

}
