package com.driftycode.productsassignment.base;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.driftycode.productsassignment.data.ProductsDatabase;

/**
 * Created by nagendra on 12/11/17.
 */

public class ProductsApplication extends Application {

    private static ProductsDatabase database;
    private ProductsApplication applicationContext;

    // Database initialization for the application
    public static ProductsDatabase getRoomInstance(Context applicationContext) {
        database = Room.databaseBuilder(applicationContext,
                ProductsDatabase.class, "Products.db")
                .build();
        return database;
    }


}
