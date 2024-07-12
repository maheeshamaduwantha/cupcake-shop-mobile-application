package com.myapp.userreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textview;
    FirebaseUser user;
    Button viewCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_2);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textview = findViewById(R.id.user_details);
        user = auth.getCurrentUser();


        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textview.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You're Logged Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Themed Cupcakes
        ImageView themedCupcakes = findViewById(R.id.ThemedCupcakes);
        themedCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Themed Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, Themed.class));
            }
        });

        // Birthday Cupcakes
        ImageView birthdayCupcakes = findViewById(R.id.birthdayCupcakes);
        birthdayCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Birthday Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, Birthday.class));
            }
        });

        // New Baby Cupcakes
        ImageView newBabyCupcakes = findViewById(R.id.newBabyCupcakes);
        newBabyCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Baby Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, Baby.class));
            }
        });

        // Anniversary Cupcakes
        ImageView anniversaryCupcakes = findViewById(R.id.anniversaryCupcakes);
        anniversaryCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Anniversary Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, Anniversary.class));
            }
        });

        // Classic Cupcakes
        ImageView classicCupcakes = findViewById(R.id.classicCupcakes);
        classicCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Classic Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, classic.class));
            }
        });

        // Valentine Cupcakes
        ImageView valentineCupcakes = findViewById(R.id.valentineCupcakes);
        valentineCupcakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Valentine Cupcakes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity2.this, Valentine.class));
            }
        });
    }
}