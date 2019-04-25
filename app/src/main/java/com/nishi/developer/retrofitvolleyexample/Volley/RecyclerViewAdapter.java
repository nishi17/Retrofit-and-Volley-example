package com.nishi.developer.retrofitvolleyexample.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nishi.developer.retrofitvolleyexample.R;
import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.UserList;
import com.nishi.developer.retrofitvolleyexample.Volley.POJO.Volley_UserList;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomRecyclerView> {

    private List<Volley_UserList.UserDataList> itemList;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

  /*  We’ve initialized an ImageLoader that’ll display the Image from the URL in the NetworkImageView for each row.
    The LRU cache is used for caching the image by implementing an ImageCache.
    The argument inside the LruCache constructor is the number of cache entries limit.*/


    public RecyclerViewAdapter(Context context, List<Volley_UserList.UserDataList> itemList) {
        this.itemList = itemList;
        mRequestQueue = SingletonRequestQueue.getInstance(context).getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    @Override
    public CustomRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, null);
        CustomRecyclerView rcv = new CustomRecyclerView(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerView holder, int position) {

        Volley_UserList.UserDataList myData = itemList.get(position);
        holder.txtLabel.setText(myData.first_name + " " + myData.last_name);
        holder.avatar.setImageUrl(myData.avatar, mImageLoader);


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class CustomRecyclerView extends RecyclerView.ViewHolder {
        TextView txtLabel;
        NetworkImageView avatar;

        CustomRecyclerView(View itemView) {
            super(itemView);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            avatar = itemView.findViewById(R.id.imgNetwork);
        }
    }
}
