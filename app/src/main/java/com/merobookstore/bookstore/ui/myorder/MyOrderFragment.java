package com.merobookstore.bookstore.ui.myorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.adapter.BooksAdapter;
import com.merobookstore.bookstore.adapter.OrdreAdapter;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.BookProduct;
import com.merobookstore.bookstore.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderFragment extends Fragment {

    private RecyclerView orderlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_myorder, container, false);
        orderlist = root.findViewById(R.id.orderList);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<List<BookProduct>> listCall = usersAPI.userOrderList(Url.token);

        listCall.enqueue(new Callback<List<BookProduct>>() {
            @Override
            public void onResponse(Call<List<BookProduct>> call, Response<List<BookProduct>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<BookProduct>  bookProducts = response.body();
                OrdreAdapter ordreAdapter = new OrdreAdapter(getActivity(), bookProducts);
                orderlist.setAdapter(ordreAdapter);
                orderlist.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<BookProduct>> call, Throwable t) {

            }
        });

        return root;
    }
}