package com.merobookstore.bookstore.model;

public class Books {
private String id;
private String title;
private String description;
private String price;
private String image;
private String categories;

    public Books(String title, String description, String price, String image, String categories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.categories = categories;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
