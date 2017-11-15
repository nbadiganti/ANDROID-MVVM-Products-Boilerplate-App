package com.driftycode.productsassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;
import com.driftycode.productsassignment.models.Product;
import com.driftycode.productsassignment.models.Products;
import com.driftycode.productsassignment.viewmodels.DataViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.driftycode.productsassignment.utils.Common.convertListToString;

public class CreateProductsActivity extends AppCompatActivity {

    private static final String TAG = "CreateProducts";
    Products productsArray;
    private Activity mActivity;
    private ProductsDatabase database;
    private DataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActivity = this;
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        // Loading productions from JSON dynamically
        loadProductsInfoFromJSON();

        // Click listeners inline - since it does`t have too many click listeners to handle, i didn`t added any dependency Injection libs
        findViewById(R.id.btn_create_product_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecordsUsingViewModel();
            }
        });
    }

    /*
     * Method: To read JSON file from assets
     */
    private void loadProductsInfoFromJSON() {
        String json;
        try {
            InputStream is = getAssets().open("products.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            productsArray = new Gson().fromJson(json, Products.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void insertRecordsUsingViewModel() {
        if (productsArray != null && productsArray.data.length > 0) {
            // insert into to room db
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

            Date myDate = new Date();
            String insertedProductDate = timeStampFormat.format(myDate);

            for (Product productItem : productsArray.data) {
                String colorsArrayString = convertListToString(productItem.getColors());
                ProductTableModel productTableModel = new ProductTableModel(productItem.getName(), productItem.getDescription(), productItem.getRegular_price(), productItem.getSale_price(), productItem.getProduct_photo(), colorsArrayString, insertedProductDate);
                viewModel.insertProducts(productTableModel);
            }
            viewModel.getProducts().observe(CreateProductsActivity.this, new Observer<List<ProductTableModel>>() {
                @Override
                public void onChanged(@Nullable List<ProductTableModel> productTableModels) {
                    Log.d(TAG, "*** Products Length " + ((productTableModels != null) ? productTableModels.size() : "0"));
                }
            });
            Toast.makeText(this, "Inserted 3 Items successfully to Database", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            loadProductsInfoFromJSON();
        }
    }
}
