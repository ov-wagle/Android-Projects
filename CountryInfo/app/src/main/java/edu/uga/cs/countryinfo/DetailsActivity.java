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
import java.util.StringTokenizer;

public class DetailsActivity extends AppCompatActivity {
    /**
     * Class is invoked when the intent in the mainActivity starts the details intent.
     * The choice of country selected on the adapterView is retrieved and based on the choice,
     * layout of the corresponding country is loaded and details containing conversion rate,
     * place to visit, hotels to stay, distance, mode of transport and cost of that respective country is
     * displayed on screen
     * @param  savedInstanceState Provides the saved state of the application to the onCreate() method
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int choice;
        TextView conversion_rate;
        TextView placeToVisit;
        TextView hotelsToStay;
        TextView distance;
        TextView mode;
        TextView cost;

        choice = intent.getIntExtra(MainActivity.COUNTRIES, MainActivity.choice);

        switch (choice) {
            case MainActivity.INDIA:
                setContentView(R.layout.india_details);
                conversion_rate = findViewById(R.id.conversion_rate_india);
                placeToVisit = findViewById(R.id.place_to_visit_india);
                hotelsToStay = findViewById(R.id.hotels_india);
                distance = findViewById(R.id.distance_india);
                mode = findViewById(R.id.mode_of_trans_india);
                cost = findViewById(R.id.cost_india);
                display_details_of_countries(conversion_rate, placeToVisit, hotelsToStay, distance, mode,
                        cost, R.raw.india_details);
                break;

            case MainActivity.AUSTRALIA:
                setContentView(R.layout.australia_details);
                conversion_rate = findViewById(R.id.conversion_rate_aus);
                placeToVisit = findViewById(R.id.place_to_visit_aus);
                hotelsToStay = findViewById(R.id.hotels_aus);
                distance = findViewById(R.id.distance_aus);
                mode = findViewById(R.id.mode_of_trans_aus);
                cost = findViewById(R.id.cost_aus);
                display_details_of_countries(conversion_rate, placeToVisit, hotelsToStay, distance, mode,
                        cost, R.raw.australia_details);
                break;

            case MainActivity.BRAZIL:
                setContentView(R.layout.brazil_details);
                conversion_rate = findViewById(R.id.conversion_rate_brz);
                placeToVisit = findViewById(R.id.place_to_visit_brz);
                hotelsToStay = findViewById(R.id.hotels_brz);
                distance = findViewById(R.id.distance_brz);
                mode = findViewById(R.id.mode_of_trans_brz);
                cost = findViewById(R.id.cost_brz);
                display_details_of_countries(conversion_rate, placeToVisit, hotelsToStay, distance, mode,
                        cost, R.raw.brazil_details);
                break;

            case MainActivity.EGYPT:
                setContentView(R.layout.egypt_details);
                conversion_rate = findViewById(R.id.conversion_rate_egy);
                placeToVisit = findViewById(R.id.place_to_visit_egy);
                hotelsToStay = findViewById(R.id.hotels_egy);
                distance = findViewById(R.id.distance_egy);
                mode = findViewById(R.id.mode_of_trans_egy);
                cost = findViewById(R.id.cost_egy);
                display_details_of_countries(conversion_rate, placeToVisit, hotelsToStay, distance, mode,
                        cost, R.raw.egypt_details);
                break;

            case MainActivity.FRANCE:
                setContentView(R.layout.france_details);
                conversion_rate = findViewById(R.id.conversion_rate_fra);
                placeToVisit = findViewById(R.id.place_to_visit_fra);
                hotelsToStay = findViewById(R.id.hotels_fra);
                distance = findViewById(R.id.distance_fra);
                mode = findViewById(R.id.mode_of_trans_fra);
                cost = findViewById(R.id.cost_fra);
                display_details_of_countries(conversion_rate, placeToVisit, hotelsToStay, distance, mode,
                        cost, R.raw.france_details);
        }
    }

    /**
     * Reads the contents from the file provided as the countryId in the parameter line by line separated
     * by the delimiter ';'. Each line contains details about conversion rate, place to visit,
     * hotels to stay, distance to reach the destination, best mode of transport to reach the destination,
     * cost to reach. The line is tokenize using StringTokenizer with ';' and each token is assigned to
     * its specific TextView
     * @param  conversion_rate  Displays conversion rate from USD to target country's currency
     * @param  placeToVisit     Displays famous place to visit
     * @param  hotelsToStay     Displays best hotels to stay
     * @param  distance         Displays distance from airport to the destination
     * @param  mode             Displays best mode of transport to reach the destination
     * @param  cost             Displays cost to reach the destination
     * @param  countryId        Country whose details shall be displayed
     **/
    private void display_details_of_countries(TextView conversion_rate, TextView placeToVisit,
                                              TextView hotelsToStay, TextView distance, TextView mode,
                                              TextView cost, int countryId) {
        String line;
        Resources res = getResources();

        try {
            InputStream file = res.openRawResource(countryId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer( line, ";");
            conversion_rate.setText(tokenizer.nextToken());
            placeToVisit.setText(tokenizer.nextToken());
            hotelsToStay.setText(tokenizer.nextToken());
            distance.setText(tokenizer.nextToken());
            mode.setText(tokenizer.nextToken());
            cost.setText(tokenizer.nextToken());
        } catch (Exception e) {
            Toast toast = Toast.makeText( getApplicationContext(),
                    "Can't read the states file",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
