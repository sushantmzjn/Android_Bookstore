package com.merobookstore.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.merobookstore.bookstore.api.UsersAPI;
import com.merobookstore.bookstore.model.BookProduct;
import com.merobookstore.bookstore.model.Books;
import com.merobookstore.bookstore.strictmode.StrictModeClass;
import com.merobookstore.bookstore.url.Url;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookdetailsActivity extends AppCompatActivity {

    private ImageView bookimg;
    private TextView title, price, category, description;
    private Button btnOrder, btnCart;
    private String oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);
        bookimg = findViewById(R.id.imgBook);
        title = findViewById(R.id.Title);
        price = findViewById(R.id.price);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        btnOrder = findViewById(R.id.Order);
        btnCart = findViewById(R.id.cart);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String img = bundle.getString("image");
            Books books = new Books( "", "", "", img, "");

            String imgPath = Url.imagePath + books.getImage();
            StrictModeClass.StrictMode();
            try {
                URL url = new URL(imgPath);
                bookimg.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            title.setText(bundle.getString("title"));
            price.setText(bundle.getString("price"));
            category.setText(bundle.getString("cate"));
            description.setText(bundle.getString("des"));

            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookOrder();
                }
            });
            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart();
                }
            });
        }
    }

    private void bookOrder() {
        String booktitle = title.getText().toString().trim();
        String bookprice = price.getText().toString().trim();
        String bookcategory = category.getText().toString().trim();

        BookProduct bookProduct = new BookProduct(booktitle, bookprice, bookcategory);
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<Void> voidCall = usersAPI.userOrder(Url.token, bookProduct);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(BookdetailsActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(BookdetailsActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void Cart() {

        String orderTitle = title.getText().toString().trim();
        String orderPrice = price.getText().toString().trim();
        String orderCategory = category.getText().toString().trim();

        BookProduct bookProduct = new BookProduct(orderTitle, orderPrice, orderCategory);
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<Void> voidCall = usersAPI.userCart(Url.token, bookProduct);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(BookdetailsActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(BookdetailsActivity.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
