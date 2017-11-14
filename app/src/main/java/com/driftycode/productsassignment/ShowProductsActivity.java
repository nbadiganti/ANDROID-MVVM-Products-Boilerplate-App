package com.driftycode.productsassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.driftycode.productsassignment.adapters.ProductsListAdapter;
import com.driftycode.productsassignment.base.ProductsApplication;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;
import com.driftycode.productsassignment.views.CustomDialogFragment;

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
    public void loadPhoto(String imageUrl) {

        FragmentManager fm = getSupportFragmentManager();
        CustomDialogFragment dFragment = new CustomDialogFragment();
        // Show DialogFragment
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        dFragment.setArguments(bundle);
        dFragment.show(fm, "Dialog Fragment");
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
