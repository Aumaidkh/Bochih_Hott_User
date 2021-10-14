package com.aumaid.bochihhott.Utils;

import android.view.View;

import java.util.ArrayList;

public class NullChecker {

    public static int checkViews(ArrayList<View> views){
        for(int i=0; i<views.size(); i++){
            if(views.get(i)==null){
                return i;
            }
        }
        return 100;
    }
}
