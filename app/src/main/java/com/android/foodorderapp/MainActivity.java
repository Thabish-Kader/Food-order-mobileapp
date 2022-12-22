package com.android.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.foodorderapp.adapters.CategoryListAdapter;
import com.android.foodorderapp.model.CategoryModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryListAdapter.CategoryListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categories");

        List<CategoryModel> categoryModelList =  getCategoryData();

        initRecyclerView(categoryModelList);
    }

    private void initRecyclerView(List<CategoryModel> restaurantModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryListAdapter adapter = new CategoryListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<CategoryModel> getCategoryData() {
        InputStream is = getResources().openRawResource(R.raw.menu);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        CategoryModel[] restaurantModels =  gson.fromJson(jsonStr, CategoryModel[].class);
        List<CategoryModel> restList = Arrays.asList(restaurantModels);

        return  restList;

    }

    @Override
    public void onItemClick(CategoryModel restaurantModel) {
        Intent intent = new Intent(MainActivity.this, CategoryMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);

    }
}