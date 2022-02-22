package edu.uga.cs.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static final String COUNTRIES = "edu.uga.cs.countryinfo.COUNTRIES";
    public static final int AUSTRALIA = 1;
    public static final int INDIA = 2;
    public static final int BRAZIL = 3;
    public static final int EGYPT = 4;
    public static final int FRANCE = 5;

    public static int choice = 0;

    /**
     * This method is an overridden method over the android's onCreate
     * It calls the android's onCreate method and then loads the design stored in activity_main.xml
     * MainActivity employs a spinner containing a list of 5 countries to select from and two buttons:
     * overview and details. Based on the button clicked, MainActivity will send an intent to the desired
     * class with the choice of country selected on the spinner
     * @param  savedInstanceState Provides the saved state of the application to the onCreate() method
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner listOfCountries;
        Button overview;
        Button details;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfCountries = findViewById(R.id.spinner);
        overview = findViewById(R.id.button);
        details = findViewById(R.id.button2);

        listOfCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // https://developer.android.com/reference/android/widget/AdapterView.OnItemSelectedListener
            /**
             * Based on the item selected on the spinner, this method is used to match the item
             * selected on the screen and choose the value of choice accordingly.
             * @param adapterView   Contains the adapter view where the selection of countries happens
             * @param view          View within the adapterView that was clicked.
             * @param pos           Position of the country within the adapter
             * @param l             The row id of the item that is selected
             **/
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (((String) adapterView.getItemAtPosition(pos)).equals("India")) {
                    choice = INDIA;
                } else if (((String) adapterView.getItemAtPosition(pos)).equals("Australia")) {
                    choice = AUSTRALIA;
                } else if (((String) adapterView.getItemAtPosition(pos)).equals("Brazil")) {
                    choice = BRAZIL;
                } else if (((String) adapterView.getItemAtPosition(pos)).equals("Egypt")) {
                    choice = EGYPT;
                } else if (((String) adapterView.getItemAtPosition(pos)).equals("France")) {
                    choice = FRANCE;
                }
            }

            // https://developer.android.com/reference/android/widget/AdapterView.OnItemSelectedListener
            /**
             * Callback is invoked when the selection disappears from the view.
             * @param adapterView   Contains the adapter view that now has no countries listed
             **/
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        overview.setOnClickListener(new View.OnClickListener() {
            /**
             * Callback is invoked when the overview button is clicked
             * @param v   Contains the view present while selecting the overview button
             **/
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OverviewActivity.class);
                if (choice != 0) {
                    intent.putExtra(COUNTRIES, choice);
                    v.getContext().startActivity(intent);
                    choice = 0;
                }
            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            /**
             * Callback is invoked when the details button is clicked
             * @param v   Contains the view present while selecting the details button
             **/
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                if (choice != 0) {
                    intent.putExtra(COUNTRIES, choice);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }
}