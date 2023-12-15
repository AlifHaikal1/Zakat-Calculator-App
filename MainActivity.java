package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber, editTextNumber2;
    Button typeButton;
    Button calculateButton;
    PopupMenu popupMenu;
    TextView aboutMeText, totalValueTextView, zakatPayableValueTextView, totalZakatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        typeButton = findViewById(R.id.typeButton);
        calculateButton = findViewById(R.id.calculateButton);
        aboutMeText = findViewById(R.id.aboutMeText);
        totalValueTextView = findViewById(R.id.totalValueTextView);
        zakatPayableValueTextView = findViewById(R.id.zakatPayableValueTextView);
        totalZakatTextView = findViewById(R.id.totalZakatTextView);

        // Set click listener for the TextView
        aboutMeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AboutMeActivity when the text is clicked
                Intent intent = new Intent(MainActivity.this, Aboutme.class);
                startActivity(intent);
            }
        });

        popupMenu = new PopupMenu(this, typeButton);

        // Add options to the menu
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, 0, Menu.NONE, "Keep");
        menu.add(Menu.NONE, 1, Menu.NONE, "Wear");

        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the selected option here
                typeButton.setText(item.getTitle());
                return true;
            }
        });

        // Set a click listener for the Button to show the PopupMenu
        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });
    }

    private void calculateZakat() {
        try {
            double weight = Double.parseDouble(editTextNumber.getText().toString());
            double value = Double.parseDouble(editTextNumber2.getText().toString());
            String goldType = typeButton.getText().toString(); // Use the Button text instead of Spinner

            double totalValue = weight * value;
            double zakatPayableValue;

            if (goldType.equals("Keep")) {
                zakatPayableValue = (weight - 85) * value;
            } else {  // Assume the type is "Wear"
                zakatPayableValue = (weight - 200) * value;
            }

            double zakat = 0.025 * zakatPayableValue;


            // Display the results in the TextViews
            totalValueTextView.setText("Total Value of Gold: " + totalValue);
            zakatPayableValueTextView.setText("Total Gold Value that is Zakat Payable: " + zakatPayableValue);
            totalZakatTextView.setText("Total Zakat: " + zakat);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
