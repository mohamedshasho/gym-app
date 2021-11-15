package com.m.shasho.mygymgroup.MyFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.m.shasho.mygymgroup.CatalogTab;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<MyTab> tabs=new ArrayList<>();
    ArrayList<CatalogTab> catalogTabs=new ArrayList<>();
    private FragmentManager myPageradapter;
    Context context;
    ArrayList<Fragment> fragments;
private String name;
    public ArrayList<CatalogTab> getCatalogTabs() {
        return catalogTabs;
    }
public void setCatalogTab(CatalogTab c){
        catalogTabs.add(c);
}
    public CatalogTab getCatalogTab(int i){
      return   catalogTabs.get(i);
    }
    public void setCatalogTabs(ArrayList<CatalogTab> catalogTabs) {
        this.catalogTabs = catalogTabs;
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior,String name) {
        super(fm, behavior);
        this.name=name;
        this.myPageradapter=fm;
        this.fragments = fragments;

    }

    public void setTabs(ArrayList<MyTab> tabs) {
        this.tabs = tabs;
    }

    public void addTab(MyTab tab){
        tabs.add(tab);

    }
public void ClearTabs(){
       tabs.removeAll(tabs);
       fragments.removeAll(fragments);

}
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabs.get(position).getTabName().getName(); //ط2
    }

    public Fragment getfragments(int position) {
        switch (position) {
            case 0:
                return new PersonsFragment();
            case 1:
                return new AdminFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return ProfileFragment.newInstance(name);
            default:
                return null;
        }
    }
    public ArrayList<MyTab> getTabs() {
        return tabs;
    }
public void setpageIcon(int icon){

}

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Fragment> getFragments() {
//        this.notifyDataSetChanged();
        return fragments;
    }

    public void setFragments(ArrayList<Fragment> fragments) {

        this.fragments = fragments;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//تعيد فراغمينت التاب للموقع المحدد
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PersonsFragment();
            case 2:
                return new AdminFragment();
            case 3:
                return ProfileFragment.newInstance(name);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

}
