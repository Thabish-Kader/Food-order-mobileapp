package com.android.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.foodorderapp.model.CategoryModel;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);


        CategoryModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurantModel.getName());
        actionBar.setSubtitle(restaurantModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.successBtn);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}