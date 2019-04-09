package com.nishi.developer.retrofitvolleyexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.DatumUserList;
import com.nishi.developer.retrofitvolleyexample.Retrofit.POJO.UserList;
import com.nishi.developer.retrofitvolleyexample.Retrofit.Retrofit_APIClient;
import com.nishi.developer.retrofitvolleyexample.Retrofit.Retrofit_APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Retrofit_MainActivity extends AppCompatActivity {

    TextView responseText;
    Retrofit_APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = (TextView) findViewById(R.id.responseText);

        apiInterface = Retrofit_APIClient.getClient().create(Retrofit_APIInterface.class);


        /**
         GET List Resources
         **/


       /* Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG", response.code() + "");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<Datum> datumList = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
                }

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {

                call.cancel();

            }
        });*/

        /**
         Create new user
         **/
      /*  User user = new User("morpheus", "leader");

        Call<User> call1 = apiInterface.createUser(user);

        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user1 = response.body();

                responseText.setText(user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt);

                Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                call.cancel();

            }
        });*/

        /**
         GET List Users
         **/
  /*      Call<UserList> call2 = apiInterface.doGetUserList("2");

        call2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                String displayResponse = "";

                UserList userList = response.body();

                Integer text = userList.page;

                Integer total = userList.total;

                Integer totalPages = userList.totalPages;

                List<DatumUserList> datumList = userList.data;


                displayResponse += text + " page\n" + total + " total\n" + totalPages + " totalPages\n";


//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (DatumUserList datum : datumList) {

                    //  Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();

                    displayResponse += " id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar + "\n";


                }

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });*/


        /**
         POST name and job Url encoded.
         **/
        Call<UserList> call3 = apiInterface.doCreateUserWithField("morpheus", "leader");

        call3.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                String displayResponse = "";

                UserList userList = response.body();

                Integer text = userList.page;

                Integer total = userList.total;

                Integer totalPages = userList.totalPages;

                List<DatumUserList> datumList = userList.data;

                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                displayResponse += text + " page\n" + total + " total\n" + totalPages + " totalPages\n";


                for (DatumUserList datum : datumList) {

                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();

                    displayResponse += " id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar + "\n";


                }

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {

                call.cancel();

            }
        });

    }
}
