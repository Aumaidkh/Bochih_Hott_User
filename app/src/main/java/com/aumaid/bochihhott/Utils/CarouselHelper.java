/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

public class CarouselHelper {

    public static void slide(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager,RecyclerView.Adapter adapter){
        final int time = 4000;
        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {

                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }

                else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {

                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), 0);
                }
            }
        }, 0, time);
    }

}
