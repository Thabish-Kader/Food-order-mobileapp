package com.android.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodorderapp.adapters.ItemListAdapter;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryMenuActivity extends AppCompatActivity implements ItemListAdapter.MenuListClickListener {
    private List<Menu> menuList = null;
    private ItemListAdapter menuListAdapter;
    private List<Menu> cartItems;
    private int totalcartItems = 0;
    private TextView checkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);

        CategoryModel categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(categoryModel.getName());
        actionBar.setSubtitle(categoryModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menuList = categoryModel.getMenus();
        initRecyclerView();

        FirebaseHandler fb = new FirebaseHandler();
         checkoutBtn = findViewById(R.id.buttonCheckout);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItems != null && cartItems.size() <= 0) {
                    Toast.makeText(CategoryMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                categoryModel.setMenus(cartItems);
                fb.add(cartItems);
                Intent i = new Intent(CategoryMenuActivity.this, OrderSuccessActivity.class);
                i.putExtra("RestaurantModel", categoryModel);
                startActivityForResult(i, 1000);

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new ItemListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(menu);
        totalcartItems = 0;

        for (Menu m : cartItems) {
            totalcartItems = totalcartItems + m.getTotalInCart();
        }
        checkoutBtn.setText("Checkout (" + totalcartItems + ") items");
    }



    @Override
    public void onUpdateCartClick(Menu menu) {
        if (cartItems.contains(menu)) {
            int index = cartItems.indexOf(menu);
            cartItems.remove(index);
            cartItems.add(index, menu);

            totalcartItems = 0;

            for (Menu m : cartItems) {
                totalcartItems = totalcartItems + m.getTotalInCart();
            }
            checkoutBtn.setText("Checkout (" + totalcartItems + ") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if (cartItems.contains(menu)) {
            cartItems.remove(menu);
            totalcartItems = 0;

            for (Menu m : cartItems) {
                totalcartItems = totalcartItems + m.getTotalInCart();
            }
            checkoutBtn.setText("Checkout (" + totalcartItems + ") items");
        }
    }
}
