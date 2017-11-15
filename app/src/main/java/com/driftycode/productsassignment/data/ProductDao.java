package com.driftycode.productsassignment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by nagendra on 12/11/17.
 */

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<ProductTableModel>> getProducts();

    @Insert(onConflict = REPLACE)
    void insertProduct(ProductTableModel productItem);

    @Delete
    void deleteProduct(ProductTableModel product);

    @Update
    void updateProduct(ProductTableModel product);
}
