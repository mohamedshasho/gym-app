package com.m.shasho.mygymgroup.MyFragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.m.shasho.mygymgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
private setOnClickTimer listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        if(context instanceof setOnClickTimer)
            listener= (setOnClickTimer) context;
        else throw new ClassCastException("You activity does not implement Timer");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_three, container, false);
//        ImageButton imageButton=view.findViewById(R.id.btn_timer);
//        imageButton.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(getContext(), "long click", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        return view;
    }
public interface setOnClickTimer{
       void  OnClickTimer();
}
}
