package com.driftycode.productsassignment.utils;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

/**
 * Created by nagendra on 12/11/17.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
