package com.merobookstore.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.merobookstore.bookstore.R;
import com.merobookstore.bookstore.model.BookProduct;

import java.util.List;

public class OrdreAdapter extends RecyclerView.Adapter<OrdreAdapter.OrderViewHolder> {

    Context context;
    List<BookProduct> bookProducts;

    public OrdreAdapter(Context context, List<BookProduct> bookProducts) {
        this.context = context;
        this.bookProducts = bookProducts;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orerlist,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        final BookProduct bookProduct = bookProducts.get(position);

        holder.title.setText(bookProduct.getTitle());
        holder.price.setText(bookProduct.getPrice());
        holder.category.setText(bookProduct.getCategories());


    }

    @Override
    public int getItemCount() {
        return bookProducts.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        private TextView title, price, category;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.orderTitle);
            price = itemView.findViewById(R.id.orderPrice);
            category = itemView.findViewById(R.id.orderCate);
        }
    }
}
