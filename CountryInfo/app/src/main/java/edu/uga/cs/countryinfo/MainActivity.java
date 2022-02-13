package edu.uga.cs.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static final String OVERVIEWTYPE = "edu.uga.cs.countryinfo.OVERVIEWTYPE";
    public static final int AUSTRALIA = 1;
    public static final int INDIA = 2;

    public static int choice = 0;

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
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (((String) adapterView.getItemAtPosition(pos)).equals("India")) {
                    choice = INDIA;
                } else if (((String) adapterView.getItemAtPosition(pos)).equals("Australia")) {
                    choice = AUSTRALIA;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OverviewActivity.class);
                intent.putExtra(OVERVIEWTYPE, choice);
                v.getContext().startActivity(intent);
                choice = 0;
            }
        });
    }
}