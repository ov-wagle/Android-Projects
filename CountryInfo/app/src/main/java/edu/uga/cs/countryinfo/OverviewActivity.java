package edu.uga.cs.countryinfo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OverviewActivity extends AppCompatActivity {
    /**
     * Class is invoked when the intent in the mainActivity starts the overview intent.
     * The choice of country selected on the adapterView is retrieved and based on the choice,
     * layout of the corresponding country is loaded and overview of that respective country is
     * displayed on screen
     * @param  savedInstanceState Provides the saved state of the application to the onCreate() method
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int choice;
        TextView description;

        choice = intent.getIntExtra(MainActivity.COUNTRIES, MainActivity.choice);

        switch (choice) {
            case MainActivity.INDIA:
                setContentView(R.layout.india_overview);
                description = findViewById(R.id.textView5);
                display_overview_of_country(description, R.raw.india_overview);
                break;

            case MainActivity.AUSTRALIA:
                setContentView(R.layout.australia_overview);
                description = findViewById(R.id.textView3);
                display_overview_of_country(description, R.raw.australia_overview);
                break;

            case MainActivity.BRAZIL:
                setContentView(R.layout.brazil_overview);
                description = findViewById(R.id.textView7);
                display_overview_of_country(description, R.raw.brazil_overview);
                break;

            case MainActivity.EGYPT:
                setContentView(R.layout.egypt_overview);
                description = findViewById(R.id.textView9);
                display_overview_of_country(description, R.raw.egypt_overview);
                break;

            case MainActivity.FRANCE:
                setContentView(R.layout.france_overview);
                description = findViewById(R.id.textView11);
                display_overview_of_country(description, R.raw.france_overview);
        }
    }

    /**
     * Reads the contents from the file provided as the countryId in the parameter line by line and
     * displays the complete content as an end result using a TextView field
     * @param  view         TextView displaying the contents of the file
     * @param  countryId    Country whose overview shall be displayed
     **/
    private void display_overview_of_country(TextView view, int countryId) {
        String content = "";
        String line;
        Resources res = getResources();

        try {
            InputStream file = res.openRawResource(countryId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            while ((line = reader.readLine()) != null) {
                content = content.concat(line);
            }
            view.setText(content);

        } catch (Exception e) {
            Toast toast = Toast.makeText( getApplicationContext(),
                    "Can't read the states file",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
