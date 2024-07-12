package com.myapp.userreg;
// CartItemsSingleton.java

import java.util.ArrayList;

public class CartItemsSingleton {
    private static CartItemsSingleton instance;
    private ArrayList<CartItem> cartItems;

    private CartItemsSingleton() {
        cartItems = new ArrayList<>();
    }

    public static CartItemsSingleton getInstance() {
        if (instance == null) {
            instance = new CartItemsSingleton();
        }
        return instance;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
}
