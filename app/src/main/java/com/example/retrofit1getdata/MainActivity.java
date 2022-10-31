package com.example.retrofit1getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Synchronous";
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.text_view_result);
        //create Retrofit Instance
        Retrofit retrofit = new Retrofit.Builder()  //builds a new Retrofit
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

         getPosts();
        //createPost();

    }
    //--------execute() runs the request on the current thread.
    //--------call. enqueue(callback) runs the request on a background thread, and runs the callback on the current thread

    //execute network request
        private void getPosts() {
            Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
            //Asynchronous Execution
            call.enqueue(new Callback<List<Post>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                    if (!response.isSuccessful()) {
                        textViewResult.setText("Code: " + response.code());
                    }

                    List<Post> posts = response.body();

                    for (Post post : posts) {
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "User ID: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";
                        textViewResult.append(content);
                    }

                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
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
        Post post = new Post(23, "Sandeep", "demo text");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);

                //Asynchronous Execution
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

}