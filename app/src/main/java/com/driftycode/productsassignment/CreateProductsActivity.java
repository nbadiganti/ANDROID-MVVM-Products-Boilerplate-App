package com.driftycode.productsassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.driftycode.productsassignment.base.ProductsApplication;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;
import com.driftycode.productsassignment.models.Products;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActivity = this;

        // Application context - For room instance
        ProductsApplication productsApplication = (ProductsApplication) getApplication();
        database = productsApplication.getRoomInstance();

        // Loading productions from JSON dynamically
        loadProductsInfoFromJSON();

        // Click listeners inline - since it does`t have too many click listeners to handle, i didn`t added any dependency Injection libs
        findViewById(R.id.btn_create_product_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecordsToDb();
            }
        });
    }


    /*
     * Method: To read JSON file from assets
     */
    private void loadProductsInfoFromJSON() {
        String json = null;
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

    private void insertRecordsToDb() {
        new InsertRecordsToDb().execute();
    }

    /*
     * Method: To insert records into database (Room)
     */
    @SuppressLint("StaticFieldLeak")
    private class InsertRecordsToDb extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            if (productsArray != null && productsArray.data.length > 0) {
                // insert into to room db
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

                Date myDate = new Date();
                String insertedProductDate = timeStampFormat.format(myDate);

                for (Products.Product productItem :
                        productsArray.data) {
                    String colorsArrayString = convertListToString(productItem.getColors());
                    ProductTableModel productTableModel = new ProductTableModel(productItem.getName(), productItem.getDescription(), productItem.getRegular_price(), productItem.getSale_price(), productItem.getProduct_photo(), colorsArrayString, insertedProductDate);
                    database.productDao().insertProduct(productTableModel);
                }
                if (database != null) {
                    List<ProductTableModel> products = database.productDao().getProducts();
                    Log.d(TAG, "*** Products Length " + products.size());
                }
            }
            assert productsArray != null;
            return productsArray.data.length;
        }

        protected void onPostExecute(Integer result) {
            Toast.makeText(mActivity, "Added 3 products from JSON successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
