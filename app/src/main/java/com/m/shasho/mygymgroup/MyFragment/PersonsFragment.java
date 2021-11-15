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
import android.widget.Toast;

import com.m.shasho.mygymgroup.CheckInternetConnection;
import com.m.shasho.mygymgroup.InformationProfile;
import com.m.shasho.mygymgroup.PersonGym;
import com.m.shasho.mygymgroup.PersonGymAdapter;
import com.m.shasho.mygymgroup.R;

import com.m.shasho.mygymgroup.database.MyDataBase;
import com.m.shasho.mygymgroup.onClickPerson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    public static final String EDIT_ONEFRAGMENT="edit";
    private static final int REQ_CODE=1;
  private static   RecyclerView rv;
  private  static PersonGymAdapter personGymAdapter;
   public static ArrayList<PersonGym> personGyms;
   private PersonGym p;
   private MyDataBase dataBase;

private  RecyclerView.LayoutManager lm;
private SwipeRefreshLayout refreshLayout;
private CheckInternetConnection ch;
//    private int id;
//    private String name;

    public PersonsFragment() {

        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataBase=MyDataBase.newInstance(context);//حصرا هنا ليتم انشاء قاعدة
        ch=new CheckInternetConnection(context);

    }

    public static PersonsFragment newInstance() {
        PersonsFragment fragment = new PersonsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_one, container, false);
        // Inflate the layout for this fragment

        rv=view.findViewById(R.id.one_fragment_recycle);
        lm=new GridLayoutManager(getContext(),1);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(lm);
        boolean c=ch.isConnectingToInternet();

        if (c) {
            //dataBase.FillDatabasePerson();
            personGyms = dataBase.getAllPersons();
        }else {
            personGyms=dataBase.getAllPersons();
        }
        personGymAdapter=new PersonGymAdapter(personGyms,getContext(), new onClickPerson() {
            @Override
            public void setOnClickPerson(String name) {
                Intent intent=new Intent(getContext(), InformationProfile.class);
                intent.putExtra(EDIT_ONEFRAGMENT,name);
                startActivityForResult(intent,REQ_CODE);
            }
        });
        rv.setAdapter(personGymAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout=view.findViewById(R.id.refreshLayoutOne);
        refreshLayout.setOnRefreshListener(this);
refreshLayout.setColorSchemeColors(getResources().getColor(R.color.radiobutton_color));
    }
public ArrayList<PersonGym> getPersonGyms(){
        return personGyms;
}
public static void SearchPerson(ArrayList<PersonGym> p){
        personGymAdapter.setPersonGyms(p);
        personGymAdapter.notifyDataSetChanged();
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

             if (data != null) {

             }
    }

    @Override
    public void onResume() {
        onRefresh();
        super.onResume();
    }
public  void searchPerson(ArrayList<PersonGym> p){
        personGymAdapter.setPersonGyms(p);
        personGymAdapter.notifyDataSetChanged();
}
public void setPersonGyms(ArrayList<PersonGym> p){
    personGymAdapter.setPersonGyms(p);
    personGymAdapter.notifyDataSetChanged();
}



    @Override
    public  void onRefresh() {
        boolean c=ch.isConnectingToInternet();
        if (c) {

        }else {
            Toast.makeText(getContext(), "no internet", Toast.LENGTH_SHORT).show();
        }

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
            personGymAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(PersonGym... p) {

            boolean c=ch.isConnectingToInternet();
            if (c) {dataBase.deletePersonAll();
                dataBase.FillDatabasePerson();
                personGyms = dataBase.getAllPersons();
            }else {
                personGyms=dataBase.getAllPersons();
            }
            personGymAdapter.setPersonGyms(personGyms);
            publishProgress();
            return null;
        }
    }

}
