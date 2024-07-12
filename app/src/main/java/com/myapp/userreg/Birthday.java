package com.myapp.userreg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Birthday extends AppCompatActivity {

    Button button1;
    Button button2;
    Button btnAdd;
    Button btnRemove;
    Button btnAddToCart;
    Button viewCartButton; // Assuming you have a button for viewing the cart
    private int birthdayQuantity = 0;  // Variable to track quantity
    private static final double BIRTHDAY_PRICE = 180.00;  // Price for one birthday cupcake
    ArrayList<CartItem> cartItems = CartItemsSingleton.getInstance().getCartItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_birthday);

        button1 = findViewById(R.id.logout); // Assuming you have the corresponding IDs in activity_birthday.xml
        button2 = findViewById(R.id.backmenu);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnAddToCart = findViewById(R.id.addToCartButton);
        viewCartButton = findViewById(R.id.viewCartButton);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent2);
                finish();
            }
        });

        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Birthday.this, ViewCartActivity.class);
                intent.putParcelableArrayListExtra("cartItems", cartItems);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get cupcake name (you might want to change this based on your actual implementation)
                String cupcakeName = "Birthday Cupcake";
                CartItem cartItem = new CartItem(cupcakeName, birthdayQuantity, BIRTHDAY_PRICE);
                CartItemsSingleton.getInstance().getCartItems().add(cartItem);

                // Clear the quantity for the next item
                birthdayQuantity = 0;
                updateQuantityAndPrice(); // Ensure the UI reflects the quantity reset
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick(v);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveButtonClick(v);
            }
        });
    }

    public void onAddButtonClick(View view) {
        birthdayQuantity++;
        updateQuantityAndPrice();
    }

    // Method to handle the - button click
    public void onRemoveButtonClick(View view) {
        if (birthdayQuantity > 0) {
            birthdayQuantity--;
            updateQuantityAndPrice();
        }
    }

    // Method to update the quantity TextView and total price
    private void updateQuantityAndPrice() {
        TextView tvQuantityBirthday = findViewById(R.id.tvQuantityBirthday);
        tvQuantityBirthday.setText("Qty: " + birthdayQuantity);

        TextView tvTotalPriceBirthday = findViewById(R.id.tvTotalPriceBirthday);
        double totalPrice = birthdayQuantity * BIRTHDAY_PRICE;
        tvTotalPriceBirthday.setText("Total: Rs " + String.format("%.2f", totalPrice));
    }
}