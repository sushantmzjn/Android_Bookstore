package com.merobookstore.bookstore.bll;

import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.serverresponse.SignUpResponse;
import com.merobookstore.bookstore.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBll {
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> userCall = usersAPI.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = userCall.execute();
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
