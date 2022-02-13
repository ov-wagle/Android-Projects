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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int choice;
        TextView description;

        choice = intent.getIntExtra(MainActivity.OVERVIEWTYPE, MainActivity.choice);

        if (choice == MainActivity.INDIA) {
            setContentView(R.layout.india_overview);
            description = findViewById(R.id.textView5);
            display_overview_of_country(description, R.raw.india_overview);
        } else if (choice == MainActivity.AUSTRALIA) {
            setContentView(R.layout.australia_overview);
            description = findViewById(R.id.textView3);
            display_overview_of_country(description, R.raw.australia_overview);
        }
    }

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
