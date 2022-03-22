package edu.uga.cs.countryinfofragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class CountryInfo extends ListFragment {
    private static final String TAG = "CountryInfo";
    static int countryChoice = 0;
    static boolean isLandscape = false;

    public CountryInfo() {
        // Required empty public constructor
    }
    
    public static String[] listOfCountries = {
            "Australia",
            "Brazil",
            "Egypt",
            "France",
            "India",
    };

    /**
     * onCreate() method is invoked when ActivityToFragment activity loads country_info.xml
     * This method when called checks for the orientation of the device and sets the isLandscape
     * variable to either true if in LANDSCAPE or false if in PORTRAIT. It also introduces an up
     * button which is directed to the ActivityToFragment activity.
     * @param savedInstanceState    Provides the saved state of the application to the onCreate() method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;

        isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE;

        ((ActivityToFragment)getActivity()).back_button();
    }

    /**
     * onViewCreated() method is used to set the list of countries to choose from.
     * If the fragment is loaded for the first time, the country on index 0 will be highlighted and
     * if the orientation is Landscape, it will also display the details of country at index 0.
     * However, if the orientation is changed from Portrait to landscape, the fragment is built again.
     * It will reinitialise the countryChoice variable with the option chosen before switching the
     * orientation and even load the details for the option selected.
     * @param view                  Contains the view present while selecting the options.
     * @param savedInstanceState    Provides the saved state of the application to the onViewCreated()
     *                              method
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the choice of countries to select from
        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1,
                listOfCountries ) );

        // Check if any savedInstance is available in case the app started in portrait and later
        // changed to landscape. If yes, assign the saved value
        if (savedInstanceState != null) {
            countryChoice = savedInstanceState.getInt(TAG, 0);
        }

        // Highlight the selected option
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // Select the option
        getListView().setItemChecked(countryChoice, true);

        // If landscape is true, load the details of the country selected
        if (isLandscape) {
            display_country_details(countryChoice);
        }
    }

    /***
     * onOptionsItemSelected is used to navigate from current fragment back to mainActivity
     * when up button is pressed.
     * @param item  Displays the items in the menu
     * @return  true if handle the item successfully
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // android.R.id.home is the built-in designation of the back button widget.
        if (id == android.R.id.home) {
            ((MainActivity)getActivity()).onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    // https://developer.android.com/reference/android/app/ListActivity#onListItemClick(android.widget.ListView,%20android.view.View,%20int,%20long)
    /***
     * onListItemClick() method is invoked when the user selects any option from the list of countries.
     * @param lv        The listView where the click happened
     * @param v         The View that was clicked
     * @param position  The position of the View in the list
     * @param id        The row id of the item that was clicked
     */
    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        display_country_details(position);
    }

    /***
     * onSaveInstanceState() method is called to save the choice made by the user before changing
     * the orientation of the phone
     * @param outState  Stores the state of the data
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG, countryChoice);
    }

    /***
     * Used to either invoke a new intent if the orientation of the phone is Portrait mode or
     * or create a new fragment object and replace the fragment object with details object.
     * @param position
     */
    void display_country_details(int position) {
        countryChoice = position;

        if (isLandscape) {
            getListView().setItemChecked(countryChoice, true);
            Log.d(TAG, "Detected Landscape mode");

            CountryDetailsFragment details = (CountryDetailsFragment)
                    getParentFragmentManager().findFragmentById(R.id.info);

            // Replace the right fragment with the details fragment element when the
            // phone is in Landscape mode
            if (details == null || details.getIndex() != countryChoice) {
                details = CountryDetailsFragment.newInstance(countryChoice);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.info, details);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        } else {
            // If the phone is in the Portrait mode, invoke a new intent and display the details
            // of the country on a screen hosting the details fragment
            Intent intent = new Intent();
            intent.setClass(getContext(), CountryDetails.class);
            intent.putExtra("CountryChoice", countryChoice);
            startActivity(intent);
        }
    }
}