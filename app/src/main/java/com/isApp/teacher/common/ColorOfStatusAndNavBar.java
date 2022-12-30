package com.isApp.teacher.common;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.isApp.teacher.R;


public class ColorOfStatusAndNavBar {

    public void colorOfStatusBar(Activity activity){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.invisible));
        window.setNavigationBarColor(activity.getColor(R.color.invisible));
        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
