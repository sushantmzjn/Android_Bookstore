package com.merobookstore.bookstore.ui.comic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.adapter.BooksAdapter;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicFragment extends Fragment {
private RecyclerView comic;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_comic, container, false);
        comic = root.findViewById(R.id.RVComic);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<List<Books>> listCall = usersAPI.getComicCategory(Url.token);

        listCall.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Books> booksList = response.body();
                BooksAdapter booksAdapter = new BooksAdapter(getActivity(), booksList);
                comic.setAdapter(booksAdapter);
                comic.setLayoutManager(new GridLayoutManager(getActivity(),3));
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}