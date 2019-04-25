package com.nishi.developer.retrofitvolleyexample.Retrofit.POJO;

import com.google.gson.annotations.SerializedName;
import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.DatumUserList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable {


    @SerializedName("page")
    public Integer page;

    @SerializedName("per_page")
    public Integer perPage;

    @SerializedName("total")
    public Integer total;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("data")
    public List<DatumUserList> data = new ArrayList();


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<DatumUserList> getData() {
        return data;
    }

    public void setData(List<DatumUserList> data) {
        this.data = data;
    }
}
