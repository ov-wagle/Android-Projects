package edu.uga.cs.countryinfofragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    /**
     * onCreate() method of MainActivity is called when the application is loaded and the splash
     * screen is to be displayed as the entry point to the app. The entry to the list of fragments
     * takes place once the button is pressed. onCreate() loads the activity_main.xml file.
     * @param savedInstanceState    Provides the saved state of the application to the onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView appDescription;
        Button entryButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDescription = findViewById(R.id.appDesc);
        entryButton = findViewById(R.id.button);

        // Read the content from the file app_description.txt and display the content
        // on the textView
        read_file_content(appDescription, R.raw.app_description);

        entryButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick() is an overriden method and is invoked when the button associated to the
             * entryButton is clicked. This button will initiate an intent to a new activity where
             * the onCreate() method declared in that activity will load the country_info.xml which is
             * associated to the countryInfo fragment.
             * @param view  Contains the view present while selecting the "View Countries" button
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ActivityToFragment.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This method is written in order to write the content of the file mentioned in the
     * descriptionId into the TextView
     * @param text          TextView displaying the data
     * @param descriptionId File containing the data
     */
    public void read_file_content(TextView text, int descriptionId) {
        String content = "";
        String line;
        Resources res = getResources();

        try {
            InputStream file = res.openRawResource(descriptionId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            while ((line = reader.readLine()) != null) {
                content = content.concat(line);
            }
            text.setText(content);

        } catch (Exception e) {
            Toast toast = Toast.makeText( getApplicationContext(),
                    "Can't read the description file",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}