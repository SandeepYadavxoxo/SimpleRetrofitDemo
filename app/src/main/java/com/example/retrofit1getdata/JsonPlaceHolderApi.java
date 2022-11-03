package com.example.retrofit1getdata;

import com.example.retrofit1getdata.Model.Comment;
import com.example.retrofit1getdata.Model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts(@Header("Dynamic-Header") String postHeader, @QueryMap Map<String,String> map);

    @GET("posts/{id}")
    Call<Post> getPostUserId(@Path("id") Integer userId);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getPostIdComment(@Path("id") Integer id);

    @GET("/comments")
    Call<List<Comment>> getCommentQuery(@Query("postId") Integer postId);


    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
