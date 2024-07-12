package com.myapp.userreg;
import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String cupcakeName;
    private int quantity;
    private double price;

    public CartItem(String cupcakeName, int quantity, double price) {
        this.cupcakeName = cupcakeName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters for each attribute

    public String getCupcakeName() {
        return cupcakeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Calculate total price for the item
    public double getTotalPrice() {
        return quantity * price;
    }

    // Parcelable implementation

    protected CartItem(Parcel in) {
        cupcakeName = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cupcakeName);
        dest.writeInt(quantity);
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
