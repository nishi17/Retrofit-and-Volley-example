package com.nishi.developer.retrofitvolleyexample.Retrofit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_APIClient {

    public static Retrofit retrofit;


    /*   THIS IS OLD WORKING CODE TILL NOW IN EVERY PROJECT */
   /* public static Retrofit_APIInterface retrofitServices;

    public static final Retrofit_APIInterface retrofitServices() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        if (Retrofit_APIClient.retrofitServices == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://reqres.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            Retrofit_APIClient.retrofitServices = retrofit.create(Retrofit_APIInterface.class);
            return Retrofit_APIClient.retrofitServices;

        }

        return Retrofit_APIClient.retrofitServices;
    }*/


    /* The getClient() method in the below code will be called every time while setting up a Retrofit interface.
     Retrofit provides with a list of annotations for each of the HTTP methods: @GET, @POST, @PUT, @DELETE, @PATCH or @HEAD.*/
    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }

   /*
   OkHttp Interceptors
    Interceptors are a powerful mechanism present in OkHttp that can monitor, rewrite, and retry calls.
    Interceptors can be majorly divided into two categories:
    Application Interceptors : To register an application interceptor, we need to call addInterceptor() on OkHttpClient.Builder
    Network Interceptors : To register a Network Interceptor, invoke addNetworkInterceptor() instead of addInterceptor()*/


}
