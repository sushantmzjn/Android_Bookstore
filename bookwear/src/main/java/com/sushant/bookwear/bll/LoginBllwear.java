package com.sushant.bookwear.bll;

import com.sushant.bookwear.SignUpResponsewear;
import com.sushant.bookwear.Url;
import com.sushant.bookwear.UsersAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBllwear {
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponsewear> userCall = usersAPI.checkUserwear(username, password);

        try {
            Response<SignUpResponsewear> loginResponse = userCall.execute();
            if (loginResponse.isSuccessful() && loginResponse.body().getStatus().equals("Login success!")) {
                Url.token += loginResponse.body().getToken();
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
