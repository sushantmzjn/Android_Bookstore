package com.sushant.bookwear;



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

public interface UsersAPI {
//    /**
//     * user registration
//     */
//    @POST("users/signup")
//    Call<SignUpResponse> registerUser(@Body User users);
//
//    /**
//     * user cart
//     */
//    @POST("cart")
//    Call<Void> userCart(@Header("Authorization") String token, @Body BookProduct bookProduct);
//
//    @GET("cart")
//    Call<List<BookProduct>> userCartList(@Header("Authorization") String token);
//
//    @DELETE("cart")
//    Call<Void> userCartDelete(@Header("Authorization") String token);
//
//    /**
//     * user book
//     */
//    @POST("bookorder")
//    Call<Void> userOrder(@Header("Authorization") String token, @Body BookProduct bookProduct);
//
//    @GET("bookorder")
//    Call<List<BookProduct>> userOrderList(@Header("Authorization") String token);
//
//    /**
//     * image upload
//     */
//    @Multipart
//    @POST("upload")
//    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    /**
     * user login
     */
    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponsewear> checkUserwear(@Field("username") String username, @Field("password") String password);

//    /**
//     * user detail retrieve
//     */
//    @GET("users/me")
//    Call<User> getUserDetails(@Header("Authorization") String token);
//
//    /**
//     * update user
//     */
//    @PUT("users/updateuser")
//    Call<String> userUpdate(@Header("Authorization") String token, @Body UserUpdate userUpdate);
//
//    /**
//     * categories
//     */
//    @GET("book/comic")
//    Call<List<Books>> getComicCategory(@Header("Authorization") String token);
//
//    @GET("book/romantic")
//    Call<List<Books>> getRomanticCategory(@Header("Authorization") String token);
//
//    @GET("book/novel")
//    Call<List<Books>> getNovelCategory(@Header("Authorization") String token);
//
//    @GET("book/thriller")
//    Call<List<Books>> getThrillerCategory(@Header("Authorization") String token);


}
