package com.myapp.userreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText flavor, price, location, imgurl; // Updated variable names
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add);

        flavor = findViewById(R.id.txtFlavor);
        price = findViewById(R.id.txtPrice);
        location = findViewById(R.id.txtLocation);
        imgurl = findViewById(R.id.txtImgurl);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertData() {
        String inputFlavor = flavor.getText().toString().trim();       // Updated variable names
        String inputPrice = price.getText().toString().trim();         // Updated variable names
        String inputLocation = location.getText().toString().trim();   // Updated variable names
        String inputImgurl = imgurl.getText().toString().trim();       // Updated variable names

        if (inputFlavor.isEmpty() || inputPrice.isEmpty() || inputLocation.isEmpty() || inputImgurl.isEmpty()) {
            // Display a toast or some feedback indicating that fields cannot be empty
            Toast.makeText(AddActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return; // Stop execution if any field is empty
        }

        Map<String, Object> map = new HashMap<>();
        map.put("flavor", inputFlavor);          // Updated variable names
        map.put("price", inputPrice);            // Updated variable names
        map.put("location", inputLocation);      // Updated variable names
        map.put("imgurl", inputImgurl);          // Updated variable names

        FirebaseDatabase.getInstance().getReference().child("cupcakes").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        clearData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Data not Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearData() {
        flavor.setText("");       // Updated variable names
        price.setText("");        // Updated variable names
        location.setText("");     // Updated variable names
        imgurl.setText("");       // Updated variable names
    }
}
