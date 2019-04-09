package com.nishi.developer.retrofitvolleyexample.Retrofit.POJO;

import com.google.gson.annotations.SerializedName;

public class Datum {

/*    The POJO classes are wrapped into a typed Retrofit Call class.
    Note: A JSONArray is serialised a List of Objects in the POJO classes*/

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("year")
    public Integer year;
    @SerializedName("pantone_value")
    public String pantoneValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPantoneValue() {
        return pantoneValue;
    }

    public void setPantoneValue(String pantoneValue) {
        this.pantoneValue = pantoneValue;
    }
}
