package com.nishi.developer.retrofitvolleyexample.Volley;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nishi.developer.retrofitvolleyexample.R;
import com.nishi.developer.retrofitvolleyexample.Volley.POJO.Volley_UserList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Volley_MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGET, btnPOST, btnImageLoader, btnCustomRequest, btnImageRequest;
    private NetworkImageView networkImageView;
    private ImageView imageView;
    private ArrayList<Volley_UserList.UserDataList> mUserDataList = new ArrayList<>();
    private String BASE_URL = "https://reqres.in";
    private String IMAGE_URL = "https://www.android.com/static/2016/img/share/oreo-lg.jpg";
    private int numberOfRequestsCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley__main);

        btnGET = findViewById(R.id.btnGET);
        btnPOST = findViewById(R.id.btnPOST);
        btnImageLoader = findViewById(R.id.btnImageLoader);
        btnImageRequest = findViewById(R.id.btnImageRequest);
        btnCustomRequest = findViewById(R.id.btnCustomRequest);
        networkImageView = findViewById(R.id.networkImageView);
        imageView = findViewById(R.id.imageView);
        btnGET.setOnClickListener(this);
        btnPOST.setOnClickListener(this);
        btnImageLoader.setOnClickListener(this);
        btnCustomRequest.setOnClickListener(this);
        btnImageRequest.setOnClickListener(this);

        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        //networkImageView.setErrorImageResId(R.drawable.ic_error);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGET:
                GETStringAndJSONRequest("2", "4");
                break;
            case R.id.btnPOST:
                POSTStringAndJSONRequest();
                break;
            case R.id.btnImageLoader:
                imageLoader();
                break;
            case R.id.btnImageRequest:
                imageRequest();
                break;
            case R.id.btnCustomRequest:
                customRequest();
                break;
        }
    }


    /*We’ve created an instance of ErrorListener that’ll be used throughout the Activity.*/
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };


    /* We’ve chained two requests in the RequestQueue.*/
    private void GETStringAndJSONRequest(String page_1, String page_2) {

        mUserDataList.clear();
        numberOfRequestsCompleted = 0;
        VolleyLog.DEBUG = true;

        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

        String uri_page_one = String.format(BASE_URL + "/api/users?page=%1$s", page_1);

        final String uri_page_two = String.format(BASE_URL + "/api/users?page=%1$s", page_2);


       /* The first Request is a StringRequest.
        page acts as a URL encoded parameter in the API /api/users?.
        The response is a JSONObject that’s serialized with Gson. We’ve set the priority of the StringRequest to low.
        Thus, this request should finish at last(in cases when the response from the server is quick, the priority won’t work).*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri_page_one, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                Volley_UserList userList = mGson.fromJson(response, Volley_UserList.class);
                mUserDataList.addAll(userList.userDataList);
                ++numberOfRequestsCompleted;

            }
        }, errorListener) {

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

        };

        queue.add(stringRequest);

   /*    The second Request is a JsonObjectRequest.
        Since it is a GET request, we’ve set the request body as null(check the second parameter).
        Priority is set to Immediate. Thus the JsonObjectRequest should complete first.*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri_page_two, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.wtf(response.toString(), "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                Volley_UserList userList = mGson.fromJson(response.toString(), Volley_UserList.class);
                mUserDataList.addAll(userList.userDataList);
                ++numberOfRequestsCompleted;

            }
        }, errorListener) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        queue.add(jsonObjectRequest);

        /*  We join the Lists returned from both the requests in mUserDataList ArrayList.*/

        /*Inside the addRequestFinishedListener callback listener,
                we check if both the requests are over(by checking the numberOfRequestsCompleted counter.*/

        /* Besides, inside addRequestFinishedListener callback, we can retrieve the response from the Cache.Entry*/

        /*Try setting setShouldCache() to false on the Requests and you find the response in the cache.*/

        /*Finally the user data list retrieved from both the requests is passed to the RecyclerViewActivity.java.
                This is where we’ll populate the ArrayList in a RecyclerView*/

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                try {
                    if (request.getCacheEntry() != null) {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        VolleyLog.d(request.getCacheKey() + " " + cacheValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (numberOfRequestsCompleted == 2) {
                    numberOfRequestsCompleted = 0;
                    startActivity(new Intent(Volley_MainActivity.this, Volley_RecyclerViewActivity.class).putExtra("users", mUserDataList));
                }
            }
        });

    }

    /*    This method would chain multiple POST requests in a RequestQueue.*/
    private void POSTStringAndJSONRequest() {

        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

        VolleyLog.DEBUG = true;
        String uri = BASE_URL + "/api/users";


        /*stringRequest – To POST parameters in a StringRequest,
        we need to override getParams() and pass the parameters as a key value pair.*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                VolleyLog.wtf(response.toString(), "utf-8");
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }


        }, errorListener) {
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

            @Override
            public Map getParams() {
                Map params = new HashMap();

                params.put("name", "Anupam");
                params.put("job", "Android Developer");


                return params;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                return headers;
            }

        };



      /*  jsonObjectRequest – To POST parameters in a JsonObjectRequest
        we pass the parameters inside a JSONObject and set them in the second parameter of the constructor.*/

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "JournalDev.com");
            jsonObject.put("job", "To teach you the best");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri, jsonObject, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                VolleyLog.wtf(response.toString(), "utf-8");
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

            }


        }, errorListener) {

            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Priority getPriority() {
                return Priority.NORMAL;
            }
        };


        /* stringRequestPOSTJSON – To POST a JSON request body in a StringRequest, we override the method getBody().*/

        StringRequest stringRequestPOSTJSON = new StringRequest(Request.Method.POST, uri, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                VolleyLog.wtf(response.toString());
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

            }


        }, errorListener) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", "Android Tutorials");
                    jsonObject.put("job", "To implement Volley in an Android Application.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String requestBody = jsonObject.toString();


                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };

        queue.add(stringRequest);
        queue.add(jsonObjectRequest);
        queue.add(stringRequestPOSTJSON);


    }


    private void imageLoader() {
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

        ImageLoader imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache mCache = new LruCache(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return (Bitmap) mCache.get(url);
            }
        });

        networkImageView.setImageUrl(IMAGE_URL, imageLoader);
        imageLoader.get(IMAGE_URL, ImageLoader.getImageListener(
                networkImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }
 /*getImageListener() handles the functionality of showing a default image until the network response is received,
    at which point it will switch to either the actual image or the error image.
    The arguments passed inside the listener are the view’s instance, default image drawable, error image drawable.*/

    private void imageRequest() {
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(IMAGE_URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                if (response != null) {
                    imageView.setImageBitmap(response);
                }

            }
        }, 200, 200, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, errorListener);

        mRequestQueue.add(imageRequest);
    }


   /* We’ve created a CustomRequest named GsonRequest that serializes and converts the response into the POJO class internally.
    Also, the CustomRequest takes the headers as a constructor argument too.*/
    private void customRequest() {
        mUserDataList.clear();
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(BASE_URL + "/api/users?page=%1$s", "2");

        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        GsonRequest gsonRequest = new GsonRequest(url, Volley_UserList.class, headers, new Response.Listener<Volley_UserList>() {
            @Override
            public void onResponse(Volley_UserList response) {
                mUserDataList.addAll(response.userDataList);
                startActivity(new Intent(Volley_MainActivity.this, Volley_RecyclerViewActivity.class).putExtra("users", mUserDataList));

            }
        }, errorListener);

        mRequestQueue.add(gsonRequest);

    }

}
