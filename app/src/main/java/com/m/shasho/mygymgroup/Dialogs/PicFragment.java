package com.m.shasho.mygymgroup.Dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.m.shasho.mygymgroup.R;

public class PicFragment extends DialogFragment {

    private OnFragmentPictureGalleryListener galleryListener;
    private OnFragmentPictureDeleteListener deleteListener;

    public PicFragment() {

    }
    public static PicFragment newInstance() {
        PicFragment fragment = new PicFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_pic, container, false);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.BOTTOM|Gravity.CENTER);
        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();
     //   params.x = 300;
        params.y = 300;
        window.setAttributes(params);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         ImageButton img_galary=view.findViewById(R.id.image_galary);
        ImageButton img_delete=view.findViewById(R.id.image_delete);
        img_galary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryListener.setOnPictureGalleryListener();
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.setOnPictureDeleteListener();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (galleryListener != null) {
            galleryListener.setOnPictureGalleryListener();
        }
        if (deleteListener != null) {
            deleteListener.setOnPictureDeleteListener();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentPictureGalleryListener) {
            galleryListener = (OnFragmentPictureGalleryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentPictureGalleryListener");
        }
        if (context instanceof OnFragmentPictureDeleteListener) {
            deleteListener = (OnFragmentPictureDeleteListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentPictureDeleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        galleryListener = null;
        deleteListener = null;
    }


    public interface OnFragmentPictureGalleryListener {

        void setOnPictureGalleryListener();
    }
    public interface OnFragmentPictureDeleteListener {

        void setOnPictureDeleteListener();
    }
}
