package com.merobookstore.bookstore.api;

import com.merobookstore.bookstore.model.BookProduct;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.model.UserUpdate;
import com.merobookstore.bookstore.serverresponse.ImageResponse;
import com.merobookstore.bookstore.serverresponse.SignUpResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsersAPI {
    /**
     * user registration
     */
    @POST("users/signup")
    Call<SignUpResponse> registerUser(@Body User users);

    /**
     * user cart
     */
    @POST("cart")
    Call<Void> userCart(@Header("Authorization") String token, @Body BookProduct bookProduct);

    @GET("cart")
    Call<List<BookProduct>> userCartList(@Header("Authorization") String token);

    @DELETE("cart/{title}")
    Call<List<BookProduct>> userCartDelete(@Header("Authorization") String token, @Path("title") String title);

    /**
     * user book
     */
    @POST("bookorder")
    Call<Void> userOrder(@Header("Authorization") String token, @Body BookProduct bookProduct);

    @GET("bookorder")
    Call<List<BookProduct>> userOrderList(@Header("Authorization") String token);

    /**
     * image upload
     */
    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    /**
     * user login
     */
    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("username") String username, @Field("password") String password);

    /**
     * user detail retrieve
     */
    @GET("users/me")
    Call<User> getUserDetails(@Header("Authorization") String token);

    /**
     * update user
     */
    @PUT("users/updateuser")
    Call<String> userUpdate(@Header("Authorization") String token, @Body UserUpdate userUpdate);

    /**
     * categories
     */
    @GET("book/comic")
    Call<List<Books>> getComicCategory(@Header("Authorization") String token);

    @GET("book/romantic")
    Call<List<Books>> getRomanticCategory(@Header("Authorization") String token);

    @GET("book/novel")
    Call<List<Books>> getNovelCategory(@Header("Authorization") String token);

    @GET("book/thriller")
    Call<List<Books>> getThrillerCategory(@Header("Authorization") String token);


}
