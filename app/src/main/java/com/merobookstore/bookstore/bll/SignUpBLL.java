package com.merobookstore.bookstore.bll;

import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.User;
import com.merobookstore.bookstore.serverresponse.SignUpResponse;
import com.merobookstore.bookstore.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpBLL {
    private boolean isSuccess = false;

    public boolean signupUser(String fullName, String address, String username, String gender, String password, String image){
        User user = new User(fullName,address,username,gender,password,image);
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);

        Call<SignUpResponse> signUpResponseCall = usersAPI.registerUser(user);

        try {
            Response<SignUpResponse> signUpResponseResponse = signUpResponseCall.execute();
            if (signUpResponseResponse.isSuccessful() && signUpResponseResponse.body().getStatus().equals("Signup success!")){
                isSuccess = true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return isSuccess;
    }
}
