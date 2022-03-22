package edu.uga.cs.countryinfofragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityToFragment extends AppCompatActivity {
    /**
     * onCreate() method is called when the button is pressed on the splash screen. This Activity makes
     * sure that we load the fragment country_info with its corresponding .xml file and also lets us
     * create an option to move from the fragment to the previous main activity.
     * @param savedInstanceState    Provides the saved state of the application to the onCreate() method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar action = getSupportActionBar();

        if (action != null) {
            action.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.country_info);
    }

    /**
     * Provide an option to return to the ActivityToFragment activity when up button is pressed
     * from the fragment containing list of countries in portrait mode or activity hosting both
     * the fragments in landscape mode.
     */
    public void back_button() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
