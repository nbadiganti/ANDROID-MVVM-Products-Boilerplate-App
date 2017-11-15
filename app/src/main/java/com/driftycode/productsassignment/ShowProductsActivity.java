package com.driftycode.productsassignment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.driftycode.productsassignment.adapters.ProductsListAdapter;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.data.ProductsDatabase;
import com.driftycode.productsassignment.viewmodels.DataViewModel;
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
    private DataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        tv_no_products = findViewById(R.id.tv_no_products_available);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        activity = this;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        viewModel.getProducts().observe(this, new Observer<List<ProductTableModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductTableModel> products) {
                if (products != null) {
                    int productsLength = products.size();
                    if (productsLength > 0) {
                        tv_no_products.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter = new ProductsListAdapter(activity, products);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        tv_no_products.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "No items found");
                    }
                    Log.d(TAG, "Product Length " + products.size());
                } else {
                    tv_no_products.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /*
     * Method: To load photo into the imageview dynamically and show in the alert dialog
     */
    public void loadPhoto(String imageUrl) {
        FragmentManager fm = getSupportFragmentManager();
        CustomDialogFragment dFragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        dFragment.setArguments(bundle);
        dFragment.show(fm, "Dialog Fragment");
    }
}
