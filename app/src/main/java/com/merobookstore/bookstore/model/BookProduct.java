package com.merobookstore.bookstore.model;

public class BookProduct {

    String title;
    String price;
    String categories;

    public BookProduct(String title, String price, String categories) {

        this.title = title;
        this.price = price;
        this.categories = categories;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
