package com.merobookstore.bookstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.BookdetailsActivity;
import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    Context context;
    List<Books> booksList;

    public BooksAdapter(Context context, List<Books> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booksview, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        final Books books = booksList.get(position);

        String imgPath = Url.imagePath + books.getImage();
        StrictModeClass.StrictMode();
        try {
            URL url = new URL(imgPath);
            holder.photo.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        holder.price.setText(books.getPrice());
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookdetailsActivity.class);
                intent.putExtra("image", books.getImage());
                intent.putExtra("title", books.getTitle());
                intent.putExtra("price", books.getPrice());
                intent.putExtra("des", books.getDescription());
                intent.putExtra("cate", books.getCategories());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView price;
//private String id;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo1);
            price = itemView.findViewById(R.id.price1);

        }
    }
}
