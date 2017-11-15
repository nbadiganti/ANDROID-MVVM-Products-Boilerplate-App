package com.driftycode.productsassignment.models;

import java.util.List;

/**
 * Created by nagendra on 11/15/2017.
 */

public class Product {
    public int id;
    private String name;
    private String description;
    private int regular_price;
    private int sale_price;
    private String product_photo;
    private List<String> colors;

    private Stores[] stores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(int regular_price) {
        this.regular_price = regular_price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public Stores[] getStores() {
        return stores;
    }

    public void setStores(Stores[] stores) {
        this.stores = stores;
    }

    private class Stores {
        private String stores;
        private String adress1;

        public String getStores() {
            return stores;
        }

        public void setStores(String stores) {
            this.stores = stores;
        }

        public String getAdress1() {
            return adress1;
        }

        public void setAdress1(String adress1) {
            this.adress1 = adress1;
        }
    }
}
