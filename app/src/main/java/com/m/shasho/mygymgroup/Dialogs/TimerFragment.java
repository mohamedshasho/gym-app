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

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.m.shasho.mygymgroup.R;


public class TimerFragment extends DialogFragment {

    private OnFragmentStart startListener;
//    private OnFragmentStop stopListener;
//    private OnFragmentCancel cancelListener;
//    private OnFragmentPause pauseListener;
   private TextView timer;
   private Message message;
private Button start_timer;
   private myHandell handell;
    int hour=00;
    int min=00;

    int second=00;
    public TimerFragment() {

    }

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_timer, container);
        // start_timer=view.findViewById(R.id.start_timer);
       // Button cancel_timer=view.findViewById(R.id.timer_cancel);
         timer=view.findViewById(R.id.timer_time);
          handell = new myHandell();
          final myThreed threed=new myThreed();
        // start_timer.setTag("stop");
threed.start();
//        start_timer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if ( start_timer.getTag().equals("play")) {
//
//                    start_timer.setBackground(getResources().getDrawable(R.drawable.ic_play_button));
//                    start_timer.setTag("stop");
//
//                } else {
//
//                    start_timer.setTag("play");
//                    start_timer.setBackground(getResources().getDrawable(R.drawable.ic_stop_button));
//                }
//
//            }
//        });
//        cancel_timer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                cancelListener.onCancelTimer();
//            }
//        });
        return view;
    }
    class myThreed extends Thread {
        @Override
       synchronized public void run() {
            super.run();

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                second++;
                message = new Message();
                message.arg1 = second;
                handell.sendEmptyMessage(second);
            }
        }
    }
    class myHandell extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==60){
                min++;
                second=0;
                msg.what=0;
            }
            if (min==60){
                second=0;
                hour++;
                min=0;
            }
            timer.setText(hour+":"+min+":"+msg.what);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//بدون عنوان
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//خلفية شفافة
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentStart) {
            startListener = (OnFragmentStart) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentStart");
        }
//        if (context instanceof OnFragmentStop) {
//            stopListener = (OnFragmentStop) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentStop");
//        }
//        if (context instanceof OnFragmentCancel) {
//            cancelListener = (OnFragmentCancel) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentCancel");
//        }
//        if (context instanceof OnFragmentPause) {
//            pauseListener = (OnFragmentPause) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentPause");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startListener = null;
//        stopListener = null;
//        cancelListener = null;
//        pauseListener = null;
    }

    public interface OnFragmentStart {
        void onStartTimer();
    }
//    public interface OnFragmentStop {
//        void onStopTimer();
//    }
//    public interface OnFragmentCancel {
//        void onCancelTimer();
//    }
//    public interface OnFragmentPause {
//        void onPauseTimer();
//    }

}
