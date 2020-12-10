package com.merobookstore.bookstore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.adapter.BooksAdapter;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.url.Url;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView1, recyclerView2, recyclerView3,recyclerView4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView1 = root.findViewById(R.id.recyclerView1);
        recyclerView2 = root.findViewById(R.id.recyclerView2);
        recyclerView3 = root.findViewById(R.id.recyclerView3);
        recyclerView4 = root.findViewById(R.id.recyclerView4);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        /**for romantic category*/
        Call<List<Books>> listCall = usersAPI.getRomanticCategory(Url.token);

        listCall.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Books> booksList = response.body();
                BooksAdapter booksAdapter = new BooksAdapter(getActivity(), booksList);
                recyclerView1.setAdapter(booksAdapter);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {

            }
        });

        /**comic category*/
        Call<List<Books>> listCall1 = usersAPI.getComicCategory(Url.token);

        listCall1.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Books> booksList1 = response.body();
                BooksAdapter booksAdapter = new BooksAdapter(getActivity(), booksList1);
                recyclerView2.setAdapter(booksAdapter);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {

            }
        });

        /**for novel category*/

        Call<List<Books>> listCall3 = usersAPI.getNovelCategory(Url.token);

        listCall3.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Books> booksList2 = response.body();
                BooksAdapter booksAdapter = new BooksAdapter(getActivity(), booksList2);
                recyclerView3.setAdapter(booksAdapter);
                recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {

            }
        });

        /**for thriller*/
        Call<List<Books>> listCall4 = usersAPI.getThrillerCategory(Url.token);

        listCall4.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Books> booksList3 = response.body();
                BooksAdapter booksAdapter = new BooksAdapter(getActivity(), booksList3);
                recyclerView4.setAdapter(booksAdapter);
                recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {

            }
        });

        return root;
    }

}