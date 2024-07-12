package com.myapp.userreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    public CartItemAdapter(Context context, ArrayList<CartItem> cartItems) {
        super(context, 0, cartItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CartItem cartItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cart_item_adapter, parent, false);
        }

        // Lookup view for data population
        TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
        TextView quantityTextView = convertView.findViewById(R.id.quantityTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        TextView totalPriceTextView = convertView.findViewById(R.id.totalPriceTextView);

        // Populate the data into the template view using the data object
        itemNameTextView.setText(cartItem.getCupcakeName());
        quantityTextView.setText("Qty: " + cartItem.getQuantity());
        priceTextView.setText("Price: Rs " + String.format("%.2f", cartItem.getPrice()));
        totalPriceTextView.setText("Total: Rs " + String.format("%.2f", cartItem.getTotalPrice()));

        // Return the completed view to render on screen
        return convertView;
    }
}
