package com.driftycode.productsassignment.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.driftycode.productsassignment.R;
import com.driftycode.productsassignment.ShowProductsActivity;
import com.driftycode.productsassignment.data.ProductTableModel;
import com.driftycode.productsassignment.viewmodels.DataViewModel;

import java.util.List;

/**
 * Created by nagendra on 12/11/17.
 */

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    private static final String TAG = "Recycleview";
    private static List<ProductTableModel> products;
    private static Context context;
    private DataViewModel viewModel;

    public ProductsListAdapter(Context context, List<ProductTableModel> products) {
        ProductsListAdapter.products = products;
        ProductsListAdapter.context = context;
        viewModel = ViewModelProviders.of((ShowProductsActivity) context).get(DataViewModel.class);

    }

    @Override
    public ProductsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextView.setText(products.get(position).getName());
        holder.description.setText(products.get(position).getDescription());
        holder.mTextActualPrice.setText("$ " + products.get(position).getRegular_price() + "");
        holder.mTextRegularPrice.setText("$ " + products.get(position).getSale_price() + "");

        // click listeners for edit and delete item
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, holder.getLayoutPosition() + "");

                Log.d(TAG, products.get(holder.getLayoutPosition()) + "");
                // viewmodel call to delete item from local database
                viewModel.deleteProducts(products.get(holder.getLayoutPosition()));
                products.remove(holder.getLayoutPosition());
                Log.d(TAG, "Deleted item successfully");
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted Item successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // showing image in enlarged view
        holder.coverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photoUrl = products.get(holder.getLayoutPosition()).getProduct_photo();
                ((ShowProductsActivity) context).loadPhoto(photoUrl);
            }
        });

        // loading image from url
        loadImageFromUrlToImageView(holder.coverImageView, products.get(position).getProduct_photo());

    }

    // Dynamically loading image from url to imageview
    private void loadImageFromUrlToImageView(final ImageView imageView, String imageUri) {

        if (imageUri != null) {
            Glide.with(context).load(imageUri)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {
            Log.d(TAG, "Not able to laod the image");
        }
    }

    // Return the size of your data-set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView, mTextActualPrice, mTextRegularPrice, description;
        ImageView deleteImageView, coverImageView;

        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv_product_name);
            description = v.findViewById(R.id.tv_product_description);
            mTextRegularPrice = v.findViewById(R.id.tv_product_regular_price);
            deleteImageView = v.findViewById(R.id.deleteImageView);
            coverImageView = v.findViewById(R.id.coverImageView);

            // To strike out the price
            mTextActualPrice = v.findViewById(R.id.tv_product_actual_price);
            mTextActualPrice.setPaintFlags(mTextActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }
    }
}



