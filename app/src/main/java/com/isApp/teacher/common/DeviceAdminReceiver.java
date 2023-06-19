package com.isApp.teacher.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.d("Device Admin Enabled", "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.d("Device Admin Disabled", "onDisabled: ");
    }

}
