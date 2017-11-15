package com.driftycode.productsassignment.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nagendra on 12/11/17.
 */
public class ProductRepository {


    private final ProductDao productDao;

    @Inject
    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }


    public LiveData<List<ProductTableModel>> getListOfData() {
        return productDao.getProducts();
    }


    public void createProduct(ProductTableModel productItem) {
        productDao.insertProduct(productItem);
    }

    public void deleteProduct(ProductTableModel productItem) {
        productDao.deleteProduct(productItem);
    }
}
