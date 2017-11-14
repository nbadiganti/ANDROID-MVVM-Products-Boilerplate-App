package com.driftycode.productsassignment.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.driftycode.productsassignment.utils.DateConverter;

/**
 * Created by nagendra on 12/11/17.
 */
@Entity(tableName = "products")
@TypeConverters(DateConverter.class)
public class ProductTableModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "regular_price")
    private int regular_price;
    @ColumnInfo(name = "sale_price")
    private int sale_price;
    @ColumnInfo(name = "product_photo")
    private String product_photo;
    @ColumnInfo(name = "colors")
    private String colors;
    @ColumnInfo(name = "updated_date")
    private String updatedDate;


    public ProductTableModel(String name, String description, int regular_price, int sale_price, String product_photo, String colors, String updatedDate) {
        this.name = name;
        this.description = description;
        this.regular_price = regular_price;
        this.sale_price = sale_price;
        this.product_photo = product_photo;
        this.colors = colors;
        this.updatedDate = updatedDate;
    }

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

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
