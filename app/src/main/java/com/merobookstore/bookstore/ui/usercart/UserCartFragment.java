package com.merobookstore.bookstore.ui.usercart;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.adapter.CartAdapter;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.BookProduct;
import com.merobookstore.bookstore.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCartFragment extends Fragment {


    public UserCartFragment() {
        // Required empty public constructor
    }



    private RecyclerView cartlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cart, container, false);

        cartlist = view.findViewById(R.id.cartList);





        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<List<BookProduct>> listCall = usersAPI.userCartList(Url.token);

        listCall.enqueue(new Callback<List<BookProduct>>() {
            @Override
            public void onResponse(Call<List<BookProduct>> call, Response<List<BookProduct>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                List<BookProduct> bookProducts = response.body();
                CartAdapter cartAdapter = new CartAdapter(getActivity(), bookProducts);
                cartlist.setAdapter(cartAdapter);
                cartlist.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<BookProduct>> call, Throwable t) {

            }
        });

        return view;
    }



}
