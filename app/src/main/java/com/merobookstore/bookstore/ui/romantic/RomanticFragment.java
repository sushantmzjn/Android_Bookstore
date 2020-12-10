package com.merobookstore.bookstore.ui.romantic;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.adapter.BooksAdapter;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RomanticFragment extends Fragment {
private RecyclerView rom;

    public RomanticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_romantic, container, false);

        rom = root.findViewById(R.id.RVRomantic);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
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
                rom.setAdapter(booksAdapter);
                rom.setLayoutManager(new GridLayoutManager(getActivity(),3));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return root;
    }

}
