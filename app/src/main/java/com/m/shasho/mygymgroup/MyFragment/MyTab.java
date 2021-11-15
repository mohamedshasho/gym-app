package com.m.shasho.mygymgroup.MyFragment;

import androidx.fragment.app.Fragment;

import com.m.shasho.mygymgroup.CatalogTab;

public class MyTab {
    CatalogTab tabName;
    Fragment fragment;

    public MyTab(CatalogTab tabName, Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    public CatalogTab getTabName() {
        return tabName;
    }

    public void setTabName(CatalogTab tabName) {
        this.tabName = tabName;
    }

    public Fragment getFragment() {
        return fragment;
    }
    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}

