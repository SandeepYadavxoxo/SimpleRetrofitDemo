package com.example.retrofit1getdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.retrofit1getdata.Model.Comment;
import com.example.retrofit1getdata.Model.Post;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Synchronous";
    private TextView textViewResult;
    private ProgressBar progressBar;

    private Button getBtn1, getBtn2, getBtn3, getBtn4, getBtn5, nextActivityBtn;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBtn1 = findViewById(R.id.getPost);
        getBtn2 = findViewById(R.id.getBtnpost1);
        getBtn3 = findViewById(R.id.getBtnpost1Comment);
        getBtn4 = findViewById(R.id.getComment);
        getBtn5 = findViewById(R.id.POST);
        nextActivityBtn = findViewById(R.id.nextActivity);
        progressBar = findViewById(R.id.progressBar);
        textViewResult = findViewById(R.id.text_view_result);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();


                        Request newRequest = originalRequest.newBuilder()
                                .header("Header-For-All-Request", "Value")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        //create Retrofit Instance
        Retrofit retrofit = new Retrofit.Builder()  //builds a new Retrofit
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setText("");
                getPosts();

            }
        });
        getBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setText("");
                getPostUserId();
            }
        });

        getBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setText("");
                getPostIdComment();
            }
        });

        getBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setText("");
                getCommentQuery();
            }
        });

        getBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setText("");
                createPost();
            }
        });

        nextActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });



    }
    //--------execute() runs the request on the current thread.
    //--------call. enqueue(callback) runs the request on a background thread, and runs the callback on the current thread

    //execute network request


    private void getPosts() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

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
                    List<Post> posts = response.body();
                    for (Post post : posts) {
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "User ID: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getBody() + "\n\n";
                        textViewResult.append(content);
                    }
                }
                else  {
                    textViewResult.setText(response.code());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    textViewResult.setText(t.getMessage());
                }
                else if (t instanceof IOException)
                {
                    textViewResult.setText(t.getMessage());
                }
                else {

                    textViewResult.setText(t.getMessage());
                }
                progressDialog.dismiss();
            }
        });
    }

    private void getPostUserId() {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Call<Post> call = jsonPlaceHolderApi.getPostUserId(1);
        //Asynchronous Execution
        call.enqueue(new Callback<Post>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {

                if (response.isSuccessful()) {
                    Post posts = response.body();
                    String content = "";
                    content += "ID: " + posts.getId() + "\n";
                    content += "User ID: " + posts.getUserId() + "\n";
                    content += "Title: " + posts.getTitle() + "\n";
                    content += "Text: " + posts.getBody() + "\n\n";
                    textViewResult.append(content);
                }
                else {

                    textViewResult.setText(response.code());

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    private void getPostIdComment() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        Call<List<Comment>> call = jsonPlaceHolderApi.getPostIdComment(1);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (response.isSuccessful()){
                List<Comment> comment = response.body();
                for (Comment comments : comment) {
                    String content = "";
                    content += "PostID: " + comments.getPostId() + "\n";
                    content += "ID: " + comments.getId() + "\n";
                    content += "name: " + comments.getName() + "\n";
                    content += "email: " + comments.getEmail() + "\n";
                    content += "body:" + comments.getBody() + "\n\n";
                    textViewResult.append(content);
                }
                }
                else {

                    textViewResult.setText(response.code());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void getCommentQuery() {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog


        Call<List<Comment>> call = jsonPlaceHolderApi.getCommentQuery(1);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (response.isSuccessful()) {

                    List<Comment> comment = response.body();
                    for (Comment comments : comment) {
                        String content = "";
                        content += "PostID: " + comments.getPostId() + "\n";
                        content += "ID: " + comments.getId() + "\n";
                        content += "name: " + comments.getName() + "\n";
                        content += "email: " + comments.getEmail() + "\n";
                        content += "body:" + comments.getBody() + "\n\n";
                        textViewResult.append(content);
                    }
                }
                else
                {
                    textViewResult.setText(response.code());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                progressDialog.dismiss();
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
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog


        Post post = new Post(100, 23, "Sandeep", "demo text");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        //Asynchronous Execution
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.isSuccessful()) {
                    Post postResponse = response.body();
                    String content = "";

                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getBody() + "\n\n";

                    textViewResult.setText(content);
                }
                else {
                    textViewResult.setText(response.code());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

}