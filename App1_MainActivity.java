package edu.uic.cs478.s2026.project3app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // These action strings must match A2's intent-filter actions exactly
    public static final String ACTION_SHOW_ATTRACTIONS =
            "edu.uic.cs478.s2026.project3app1.action.SHOW_ATTRACTIONS";

    public static final String ACTION_SHOW_RESTAURANTS =
            "edu.uic.cs478.s2026.project3app1.action.SHOW_RESTAURANTS";

    private Button btnAttractions;
    private Button btnRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnAttractions = findViewById(R.id.btnAttractions);
        btnRestaurants = findViewById(R.id.btnRestaurants);

        btnAttractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttractionsButtonClicked();
            }
        });

        btnRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestaurantsButtonClicked();
            }
        });
    }

    private void onAttractionsButtonClicked() {
        Toast.makeText(
                this,
                getString(R.string.toast_attractions),
                Toast.LENGTH_SHORT
        ).show();

        try {
            Intent intent = new Intent(ACTION_SHOW_ATTRACTIONS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    "Please install the Chicago Guide app first.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void onRestaurantsButtonClicked() {
        Toast.makeText(
                this,
                getString(R.string.toast_restaurants),
                Toast.LENGTH_SHORT
        ).show();

        try {
            Intent intent = new Intent(ACTION_SHOW_RESTAURANTS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    "Please install the Chicago Guide app first.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
