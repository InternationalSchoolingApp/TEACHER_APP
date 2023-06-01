package com.isApp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.isApp.teacher.Adapter.AssignStudentAdapter;
import com.isApp.teacher.Model.AssignStudentModel;
import com.isApp.teacher.Network.ApiInterface;
import com.isApp.teacher.Network.NetworkChangeListener;
import com.isApp.teacher.Network.Retrofit.RetroFitClient;
import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.common.Constants;
import com.isApp.teacher.databinding.ActivityAssignedStudentBinding;
import com.isApp.teacher.sharedPreference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignedStudent extends AppCompatActivity {

    private ActivityAssignedStudentBinding binding;
    PreferenceManager preferenceManager;

    List<AssignStudentModel.YeLo> assignStudentModelList= new ArrayList<AssignStudentModel.YeLo>();
    AssignStudentAdapter assignStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignedStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.assignStudentRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.assignStudentRV.setLayoutManager(llm);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        preferenceManager = new PreferenceManager(getApplicationContext());
        binding.backButton.setOnClickListener(v->onBackPressed());

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        AssignStudentModel assignStudentModel = new AssignStudentModel(preferenceManager.getInt(Constants.TEACHER_ID));

        Call<AssignStudentModel> call = apiInterface.assignStudentModelCall(assignStudentModel);
//        assignStudentModelList = new ArrayList<AssignStudentModel.YeLo>();
        call.enqueue(new Callback<AssignStudentModel>() {
            @Override
            public void onResponse(Call<AssignStudentModel> call, Response<AssignStudentModel> response) {
                if (response.body().getStatus().equals("success")){
                    assignStudentModelList = response.body().getYeLoList();
                    assignStudentAdapter = new AssignStudentAdapter(assignStudentModelList);
                    binding.assignStudentRV.setAdapter(assignStudentAdapter);
                }else{
                    Toast.makeText(AssignedStudent.this, "No Student Assigned", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AssignStudentModel> call, Throwable t) {

            }
        });

         }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<AssignStudentModel.YeLo> filteredlist = new ArrayList<AssignStudentModel.YeLo>();

        // running a for loop to compare elements.
        for (AssignStudentModel.YeLo item : assignStudentModelList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStudentName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            assignStudentAdapter.filterList(filteredlist);
        }
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

}