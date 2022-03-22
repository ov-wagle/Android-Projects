package edu.uga.cs.countryinfofragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CountryDetails extends AppCompatActivity {
    private static final String TAG = "Country";

    /**
     * onCreate() method is called when the user select an option in the list of countries
     * and a new fragment is loaded to display the details of the country chosen. In case of
     * Landscape mode, it finishes the current fragment.
     * @param savedStateInstance    Provides the saved state of the application to the onCreate() method
     */
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "CountryDetails.onCreate()");
            finish();
            return;
        }

        CountryDetailsFragment details = new CountryDetailsFragment();
        details.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, details).commit();
    }

    /***
     * onOptionsItemSelected is used to navigate from current fragment back to previous fragment
     * hosting list of countries when up button is pressed.
     * @param item  Displays the items in the menu
     * @return  true if handle the item successfully
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // android.R.id.home is the built-in designation of the back button widget.
        if( id == android.R.id.home ) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
