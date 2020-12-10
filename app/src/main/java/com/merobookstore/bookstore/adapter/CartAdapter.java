package com.merobookstore.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.BookdetailsActivity;
import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.BookProduct;
import com.merobookstore.bookstore.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<BookProduct> bookProductList;

    public CartAdapter(Context context, List<BookProduct> bookProductList) {
        this.context = context;
        this.bookProductList = bookProductList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlist, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        final BookProduct bookProduct = bookProductList.get(position);

        holder.carttitle.setText(bookProduct.getTitle());
        holder.cartprice.setText(bookProduct.getPrice());
        holder.cartcategory.setText(bookProduct.getCategories());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String booktitle = holder.carttitle.getText().toString().trim();
                String bookprice = holder.cartprice.getText().toString().trim();
                String bookcategory = holder.cartcategory.getText().toString().trim();

                BookProduct bookProduct = new BookProduct(booktitle, bookprice, bookcategory);
                UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
                Call<List<BookProduct>> listCall = usersAPI.userCartDelete(Url.token, bookProduct.getTitle());
                
                listCall.enqueue(new Callback<List<BookProduct>>() {
                    @Override
                    public void onResponse(Call<List<BookProduct>> call, Response<List<BookProduct>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BookProduct>> call, Throwable t) {

                    }
                });




            }
        });

        holder.btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String booktitle = holder.carttitle.getText().toString().trim();
                String bookprice = holder.cartprice.getText().toString().trim();
                String bookcategory = holder.cartcategory.getText().toString().trim();

                BookProduct bookProduct = new BookProduct(booktitle, bookprice, bookcategory);
                UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
                Call<Void> voidCall = usersAPI.userOrder(Url.token, bookProduct);

                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookProductList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView carttitle, cartprice, cartcategory;
        private Button delete, btnorder;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            carttitle = itemView.findViewById(R.id.cartTitle);
            cartprice = itemView.findViewById(R.id.cartPrice);
            cartcategory = itemView.findViewById(R.id.cartCategory);
            delete = itemView.findViewById(R.id.btndelete);
            btnorder = itemView.findViewById(R.id.btnOrder);
        }
    }
}
