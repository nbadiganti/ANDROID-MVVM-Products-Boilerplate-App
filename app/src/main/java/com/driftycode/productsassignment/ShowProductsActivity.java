package com.driftycode.productsassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.driftycode.productsassignment.adapters.ProductsListAdapter;
import com.driftycode.productsassignment.base.ProductsApplication;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;

import java.util.List;

public class ShowProductsActivity extends AppCompatActivity {

    private final String TAG = "ShowProducts";
    private ProductsDatabase database;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity activity;
    private TextView tv_no_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        activity = this;

        tv_no_products = findViewById(R.id.tv_no_products_available);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Application context to perform Room DB calls
        ProductsApplication productsApplication = (ProductsApplication) getApplication();
        database = productsApplication.getRoomInstance();
        new AsycRecordsFromDB().execute();
    }

    /*
     * Method: To load photo into the imageview dynamically and show in the alert dialog
     */
    public void loadPhoto(ImageView imageView) {

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

    public void loadPhotoToFullView(ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();
    }

    /*
     * Method: To fetch records from ROOM database
     */
    @SuppressLint("StaticFieldLeak")
    private class AsycRecordsFromDB extends AsyncTask<Void, Integer, Integer> {
        List<ProductTableModel> products;
        @Override
        protected Integer doInBackground(Void... voids) {
            int productsLength = 0;
            if (database != null) {
                products = database.productDao().getProducts();
                Log.d(TAG, "*** Products Length " + products.size());
                productsLength = products.size();
                for (ProductTableModel productItem : products) {
                    Log.d(TAG, "Product item " + productItem.getName());
                    Log.d(TAG, "Product item " + productItem.getDescription());
                }
            }
            return productsLength;
        }

        protected void onPostExecute(Integer result) {
            // specify an adapter (see also next example)
            if (result > 0) {
                tv_no_products.setVisibility(View.INVISIBLE);
                mAdapter = new ProductsListAdapter(activity, products);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                tv_no_products.setVisibility(View.VISIBLE);
                Log.d(TAG, "No items found");
            }
        }
    }



}
