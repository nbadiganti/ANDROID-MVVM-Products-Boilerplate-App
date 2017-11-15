package com.driftycode.productsassignment.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.driftycode.productsassignment.base.ProductsApplication;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;

import java.util.List;

/**
 * Created by nagendra on 11/15/2017.
 */

public class DataViewModel extends AndroidViewModel {
    private final LiveData<List<ProductTableModel>> productsItem;
    private ProductsDatabase appDatabase;

    /*
    * constructor for the dataviewmodel
    */
    public DataViewModel(Application application) {
        super(application);
        appDatabase = ProductsApplication.getRoomInstance(application.getApplicationContext());
        productsItem = appDatabase.productDao().getProducts();
    }

    /*
    * Method: void - Get All the Products from the local database
    */
    public LiveData<List<ProductTableModel>> getProducts() {
        return productsItem;
    }

    /*
    * Method: void - insert the item from local database
    */
    public void insertProducts(ProductTableModel productItem) {
        new insertProductAsyncTask(appDatabase).execute(productItem);
    }

    /*
     * Method: void - delete the item from local database
     */
    public void deleteProducts(ProductTableModel productItem) {
        new deleteProductAsyncTask(appDatabase).execute(productItem);
    }


    /*
     * Method: AsyncTask to perform insert item into the local database
     */
    private static class insertProductAsyncTask extends AsyncTask<ProductTableModel, Void, Void> {

        private ProductsDatabase db;

        insertProductAsyncTask(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(ProductTableModel... productTableModels) {
            db.productDao().insertProduct(productTableModels[0]);
            return null;
        }
    }

    /*
    * Method: AsyncTask to perform delete item into the local database
    */
    private static class deleteProductAsyncTask extends AsyncTask<ProductTableModel, Void, Void> {

        private ProductsDatabase db;

        deleteProductAsyncTask(ProductsDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(ProductTableModel... productTableModels) {
            db.productDao().deleteProduct(productTableModels[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
