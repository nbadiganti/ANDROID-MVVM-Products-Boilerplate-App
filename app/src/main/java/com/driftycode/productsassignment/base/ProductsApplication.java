package com.driftycode.productsassignment.base;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.driftycode.productsassignment.data.ProductsDatabase;


import static android.arch.persistence.room.Room.databaseBuilder;

/**
 * Created by nagendra on 12/11/17.
 */

public class ProductsApplication extends Application {

    private ProductsApplication applicationContext;
    private ProductsDatabase database;

    // Database initialization for the application
    public ProductsDatabase getRoomInstance() {
        database = Room.databaseBuilder(getApplicationContext(),
                ProductsDatabase.class, "Products.db")
                .build();
        return database;
    }


}
