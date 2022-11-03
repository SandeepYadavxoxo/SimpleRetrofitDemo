package com.example.retrofit1getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.retrofit1getdata.Model.Comment;
import com.example.retrofit1getdata.Model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String TAG = "resultAPI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //create Retrofit Instance
        Retrofit retrofit = new Retrofit.Builder()  //builds a new Retrofit
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
    }

    private void getPosts() {

        Map<String,String> map = new HashMap<>();
        map.put("userId","6");
        map.put("id","51");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts("This is a Dynamic Header",map);

        //Asynchronous Execution
        call.enqueue(new Callback<List<Post>>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "getPost: " + response.body());
                } else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
                getCommentQuery();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void getPostUserId() {
        Call<Post> call = jsonPlaceHolderApi.getPostUserId(1);
        //Asynchronous Execution
        call.enqueue(new Callback<Post>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "getPostUserId: " + response.body());
                } else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
                getPostIdComment();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }


    private void getPostIdComment() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getPostIdComment(1);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "getPostIdComment: " + response.body());
                } else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
                getPostUserId();

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void getCommentQuery() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getCommentQuery(1);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "getCommentQuery: " + response.body());
                } else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
                createPost();

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }


//            try
//            {
//                Response<List<Post>> response = call.execute(); //Synchronously send the request and return its response.
//                List<Post> posts = response.body();
//
//                //API response
//                for (Post post : posts) {
//                    String content = "";
//                        content += "ID: " + post.getId() + "\n";
//                        content += "User ID: " + post.getUserId() + "\n";
//                        content += "Title: " + post.getTitle() + "\n";
//                        content += "Text: " + post.getText() + "\n\n";
//                        textViewResult.append(content);
//                    }
//            }
//            catch (Exception ex)
//            {
//                Log.d(TAG, "getPosts: ");
//                ex.printStackTrace();
//            }
//        }

    //execute network request
    private void createPost() {
        Post post = new Post(100, 23, "Sandeep", "demo text");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        //Asynchronous Execution
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "createPost: " + response.body());
                }
                else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

}