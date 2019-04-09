package com.nishi.developer.retrofitvolleyexample.Retrofit;

import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.MultipleResource;
import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.User;
import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.UserList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Retrofit_APIInterface {


    /*   THIS IS OLD WORKING CODE TILL NOW IN EVERY PROJECT */
/*    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @GET(Retrofit_apiLink.UNKNOWN)
    Call<IdResponse> callFlyer(@Header("authorization") String auth);

    @Headers({
            "Accept:application/json",
            "Content-Type:application/json"
    })
    @POST(Retrofit_apiLink.USERS)
    Call<SignUpResponse> verifyCall(@Body VerifyPhone verifyPhone);*/


    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
 /* we’ve defined some methods that perform HTTP requests with annotation.
    We’ve used a few test APIs from here
    @GET("/api/unknown") calls doGetListResources();.
    doGetListResources() is the method name.
    MultipleResource.java is a Model POJO class for our response object that’s used to map the response parameters to their respective variables.
    These POJO class act as the method return type.

*/

/*
    Method Parameters : There are a wide variety of possible options of parameters to pass inside a method:

@Body – Sends Java objects as request body.
@Url – use dynamic URLs.
@Query – We can simply add a method parameter with @Query() and a query parameter name, describing the type. To URL encode a query use the form:
@Query(value = "auth_token",encoded = true) String auth_token
@Field – send data as form-urlencoded. This requires a @FormUrlEncoded annotation attached with the method.
        The @Field parameter works only with a POST

        Note: @Field requires a mandatory parameter. In cases when @Field is optional, we can use @Query instead and pass a null value
*/
