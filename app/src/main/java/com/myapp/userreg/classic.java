package com.myapp.userreg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class classic extends AppCompatActivity {

    Button button1;
    Button button2;
    Button btnAdd;
    Button btnRemove;
    Button btnAddToCart;
    Button viewCartButton; // Assuming you have a button for viewing the cart
    private int classicQuantity = 0;  // Variable to track quantity
    private static final double CLASSIC_PRICE = 100.00;  // Price for one classic cupcake
    ArrayList<CartItem> cartItems = CartItemsSingleton.getInstance().getCartItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_classic);

        button1 = findViewById(R.id.logout);
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
                Intent intent = new Intent(classic.this, ViewCartActivity.class);
                intent.putParcelableArrayListExtra("cartItems", cartItems);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Item added Successfully ", Toast.LENGTH_SHORT).show();
                // Get cupcake name (you might want to change this based on your actual implementation)
                String cupcakeName = "Classic Cupcake";

                // Create a CartItem and add it to the cartItems ArrayList
                CartItem cartItem = new CartItem(cupcakeName, classicQuantity, CLASSIC_PRICE);
                CartItemsSingleton.getInstance().getCartItems().add(cartItem);
                // Clear the quantity for the next item
                classicQuantity = 0;
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
        classicQuantity++;
        updateQuantityAndPrice();
    }

    // Method to handle the - button click
    public void onRemoveButtonClick(View view) {
        if (classicQuantity > 0) {
            classicQuantity--;
            updateQuantityAndPrice();
        }
    }

    // Method to update the quantity TextView and total price
    private void updateQuantityAndPrice() {
        TextView tvQuantityClassic = findViewById(R.id.tvQuantityClassic);
        tvQuantityClassic.setText("Qty: " + classicQuantity);

        TextView tvTotalPriceClassic = findViewById(R.id.tvTotalPriceClassic);
        double totalPrice = classicQuantity * CLASSIC_PRICE;
        tvTotalPriceClassic.setText("Total: Rs " + String.format("%.2f", totalPrice));
    }
}