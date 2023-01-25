package com.isApp.teacher.common;

import com.isApp.teacher.Model.LogoutModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutDone {


    public Boolean logout(Integer platformId, int userId, String usermail) {

        MobileModel mobileModel = new MobileModel();
        String model = mobileModel.getDeviceName();
        final Integer[] i = {0};
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        final LogoutModel logoutModel = new LogoutModel(platformId, userId, model, 1, usermail);
        Call<LogoutModel> call = apiInterface.logoutPostData(logoutModel);

        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                if (response.body().getStatus().equals("success")) {
                    i[0] = 0;
                } else {
                    i[0] = 1;
                }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                i[0] = 2;
            }

        });
        if(i[0]==0){
            return  true;
        }
        else {
            return  false;
        }


    }
}
