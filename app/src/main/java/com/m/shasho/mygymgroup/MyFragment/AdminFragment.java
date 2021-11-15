package com.m.shasho.mygymgroup.MyFragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.shasho.mygymgroup.About_Coach;
import com.m.shasho.mygymgroup.AdminGymAdapter;
import com.m.shasho.mygymgroup.CheckInternetConnection;
import com.m.shasho.mygymgroup.PersonGym;
import com.m.shasho.mygymgroup.R;
import com.m.shasho.mygymgroup.database.MyDataBase;
import com.m.shasho.mygymgroup.onClickAdmin;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String INFO_COACH_CODE = "info";
    private  RecyclerView rv;
    private static AdminGymAdapter adminGymAdapter;
    private   ArrayList<PersonGym> adminGyms=new ArrayList<>();
    private  RecyclerView.LayoutManager lm;

    private SwipeRefreshLayout refreshLayout;
private MyDataBase dataBase;
private CheckInternetConnection ch;
    public AdminFragment() {
        // Required empty public constructor
    }
    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataBase= MyDataBase.newInstance(context);//حصرا هنا ليتم انشاء قاعدة
         ch=new CheckInternetConnection(context);

    }
    public static void SearchAdmin(ArrayList<PersonGym> p){
       adminGymAdapter.setAdminGyms(p);
       adminGymAdapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_tow, container, false);
        rv=view.findViewById(R.id.tow_fragment_recycle);
        lm=new GridLayoutManager(getContext(),1);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        boolean c=ch.isConnectingToInternet();

        if (c) {
            //dataBase.FillDatabaseAdmin();
            adminGyms = dataBase.getAllAdmin();
        }else {
            adminGyms=dataBase.getAllAdmin();
        }
        adminGymAdapter=new AdminGymAdapter(adminGyms, getContext(), new onClickAdmin() {
            @Override
            public void setOnClickAdmin(String name) {
                Intent intent=new Intent(getContext(), About_Coach.class);
                intent.putExtra(INFO_COACH_CODE,name);
                startActivity(intent);
            }
        });
        rv.setAdapter(adminGymAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout=view.findViewById(R.id.refreshLayoutTow);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.radiobutton_color));
    }

    @Override
    public void onResume() {
      onRefresh();
        super.onResume();

    }

    public ArrayList<PersonGym> getAdminGyms() {
        return adminGyms;
    }

    public void setAdminGyms(ArrayList<PersonGym> adminGyms) {
        this.adminGyms=adminGyms;
    }

    @Override
    public void onRefresh() {

        MyTask task=new MyTask();
        task.execute();

    }

    class MyTask extends AsyncTask<PersonGym,Integer,Void>
    {
        @Override
        protected void onPreExecute() {//قبل التنفيذ
            super.onPreExecute();
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {//بعد التنفيذ
            super.onPostExecute(aVoid);
            refreshLayout.setRefreshing(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {//التقدم

            super.onProgressUpdate(values);
            adminGymAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(PersonGym... p) {
            boolean c=ch.isConnectingToInternet();
            if (c) {dataBase.deleteAdminAll();
                dataBase.FillDatabaseAdmin();
                adminGyms = dataBase.getAllAdmin();
            }else {
                adminGyms=dataBase.getAllAdmin();
            }
            adminGymAdapter.setAdminGyms(adminGyms);
            publishProgress();
            return null;
        }
    }
}
