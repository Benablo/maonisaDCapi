package com.example.truemidtermdc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truemidtermdc.adapters.ShoesAdapter;
import com.example.truemidtermdc.api.RequestPlaceholder;
import com.example.truemidtermdc.api.RetrofitBuilder;
import com.example.truemidtermdc.pojos.Shoes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public RecyclerView shoesRecyclerView;
    public List<Shoes> shoesList;
    public ShoesAdapter shoesAdapter;

    private SwipeRefreshLayout swipeRefresh;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        swipeRefresh = findViewById(R.id.swipeRefresh);

        shoesRecyclerView = findViewById(R.id.shoesRecycleView);
        shoesList = new ArrayList<>();
        shoesAdapter = new ShoesAdapter(shoesList, this);
        shoesRecyclerView.setAdapter(shoesAdapter);
        shoesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateShoes();
            }
        });

        populateShoes();
    }

    public void populateShoes() {
        try {
            shoesList.clear();

            Call<List<Shoes>> shoesCall = requestPlaceholder.getAllPosts("OupPKM230rFl4rvt1UXKc52EfmVyUMhsm2y2taGg9tsVbGbbkMAstx7Ea1KFSmWt", "1");

            shoesCall.enqueue(new Callback<List<Shoes>>() {
                @Override
                public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            shoesList.addAll(response.body());

                            shoesAdapter.notifyDataSetChanged();

                            swipeRefresh.setRefreshing(false);
                        } else {
                            Log.e("ERROR_GET_POSTS", response.message() + "");
                            Toast.makeText(HomeActivity.this, "Error getting posts madapaka!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("ERROR_GET_POSTS", response.message() + "");
                        Toast.makeText(HomeActivity.this, "Error getting posts madapaka!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Shoes>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.e("ERROR_GET_POSTS", e.getMessage());
        }
    }
}