package com.myapp.userreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_cart);

        // Receive the cart items from the intent
        ArrayList<CartItem> cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Initialize UI components
        ListView cartListView = findViewById(R.id.cartListView);
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        Button addMoreButton = findViewById(R.id.addMoreButton);
        Button checkoutButton = findViewById(R.id.checkoutButton);

        // Set up the adapter for the ListView
        CartItemAdapter cartItemAdapter = new CartItemAdapter(this, cartItems);
        cartListView.setAdapter(cartItemAdapter);

        // Calculate and display the total price
        double totalPrice = calculateTotalPrice(cartItems);
        totalPriceTextView.setText("Total: Rs " + String.format("%.2f", totalPrice));

        // Set click listener for "Add More" button
        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the main activity
                Intent intent = new Intent(ViewCartActivity.this, MainActivity2.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });

        // Set click listener for "Checkout" button
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the bill in a new activity
                displayBill(cartItems, totalPrice);
            }
        });
    }

    private double calculateTotalPrice(ArrayList<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
    }

    private void displayBill(ArrayList<CartItem> cartItems, double totalPrice) {
        // Create an intent to start the BillActivity
        Intent intent = new Intent(ViewCartActivity.this, BillActivity.class);
        intent.putParcelableArrayListExtra("cartItems", cartItems);
        intent.putExtra("totalPrice", totalPrice);
        startActivity(intent);
    }
}
