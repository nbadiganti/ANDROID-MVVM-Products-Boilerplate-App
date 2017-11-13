package com.driftycode.productsassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.driftycode.productsassignment.adapters.ProductsListAdapter;
import com.driftycode.productsassignment.base.ProductsApplication;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;
import com.driftycode.productsassignment.models.Products;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.driftycode.productsassignment.utils.Common.convertListToString;

public class ShowProductsActivity extends AppCompatActivity {

    private ProductsDatabase database;
    private final String TAG = "ShowProducts";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        activity = this;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Application context to perform Room DB calls
        ProductsApplication productsApplication = (ProductsApplication) getApplication();
        database = productsApplication.getRoomInstance();
        new AsycRecordsFromDB().execute();
    }

    /*
     * Method: To fetch records from ROOM database
     */
    @SuppressLint("StaticFieldLeak")
    private class AsycRecordsFromDB extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            int productsLength = 0;
            if (database != null) {
                List<ProductTableModel> products = database.productDao().getProducts();
                Log.d(TAG, "*** Products Length " + products.size());
                productsLength = products.size();
                for (ProductTableModel productItem : products) {
                    Log.d(TAG, "Product item " + productItem.getName());
                    Log.d(TAG, "Product item " + productItem.getDescription());
                }

                // specify an adapter (see also next example)
                if (productsLength > 0) {
                    mAdapter = new ProductsListAdapter(activity, products);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "No items found");
                }
            }
            return productsLength;
        }

        protected void onPostExecute(Integer result) {

        }
    }



    /*
     * Method: To load photo into the imageview dynamically and show in the alert dialog
     */
    public void loadPhoto(ImageView imageView, int width, int height) {

        ImageView tempImageView = imageView;
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.image_large_view,
                (ViewGroup) findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        imageDialog.create();
        imageDialog.show();
    }

}
