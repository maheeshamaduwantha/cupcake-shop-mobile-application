package com.myapp.userreg;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bill);

        // Receive the cart items from the intent
        ArrayList<CartItem> cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Initialize UI components
        ListView billListView = findViewById(R.id.billListView);
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        TextView thankYouTextView = findViewById(R.id.thankYouTextView);

        // Get the current user's email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = currentUser != null ? currentUser.getEmail() : "Unknown";

        // Display a thank you message with the user's email
        thankYouTextView.setText("Thank you for shopping with us, " + userEmail + "!");

        // Set up the adapter for the ListView
        BillItemAdapter billItemAdapter = new BillItemAdapter(this, cartItems);
        billListView.setAdapter(billItemAdapter);

        // Calculate and display the total price
        double totalPrice = calculateTotalPrice(cartItems);
        totalPriceTextView.setText("Total: Rs " + String.format("%.2f", totalPrice));
    }

    private double calculateTotalPrice(ArrayList<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
