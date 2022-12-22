package com.android.foodorderapp;


import com.android.foodorderapp.model.Menu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseHandler {
    private DatabaseReference databaseReference;
    public FirebaseHandler(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(CategoryMenuActivity.class.getSimpleName());
    }

    public void add(List<Menu> itemsInCartList){
        databaseReference.push().setValue(itemsInCartList);
    }
}
