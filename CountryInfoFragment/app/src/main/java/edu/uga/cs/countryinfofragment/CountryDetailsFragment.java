package edu.uga.cs.countryinfofragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CountryDetailsFragment extends Fragment {
    private final static int AUSTRALIA = 0;
    private final static int BRAZIL = 1;
    private final static int EGYPT = 2;
    private final static int FRANCE = 3;
    private final static int INDIA = 4;

    /***
     * Initiates a new fragment object and stores the option selected on the list of countries
     * @param countryChoice Choice of country selected
     * @return  new object to CountryDetailsFragment
     */
    public static CountryDetailsFragment newInstance(int countryChoice) {
        CountryDetailsFragment fragment = new CountryDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("CountryChoice", countryChoice);
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * Retrieve the index from the argument set while initiating a new instance of CountryDetailsFragment
     * @return index of the country selected
     */
    public int getIndex() {
        return getArguments().getInt("CountryChoice", 0);
    }

    // https://developer.android.com/reference/android/app/Fragment#onCreateView(android.view.LayoutInflater,%20android.view.ViewGroup,%20android.os.Bundle)
    /***
     * onCreateView() is used to create initialize all the views required to display the details
     * of the country and based on the choice made, it will display the details of the
     * corresponding country
     * @param inflater              The LayoutInflater object that can be used to inflate any views
     *                              in the fragment
     * @param container             If non-null, this is the parent view that the fragment's UI
     *                              should be attached to. The fragment should not add the view itself,
     *                              but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState    Stores the previous saved state
     * @return                      Returns the view for the fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scroll = new ScrollView(getActivity());

        // Adding Vertical linear layout to the scrollView
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linear.setLayoutParams(params);
        scroll.addView(linear);

        switch (getIndex()) {
            case AUSTRALIA:
                set_image_on_screen(linear, R.drawable.australia);
                display_country_overview(linear, R.raw.australia_overview);
                set_image_on_screen(linear, R.drawable.opera);
                display_country_details(linear, R.raw.australia_details);
                break;

            case BRAZIL:
                set_image_on_screen(linear, R.drawable.brazil);
                display_country_overview(linear, R.raw.brazil_overview);
                set_image_on_screen(linear, R.drawable.jesus_statue);
                display_country_details(linear, R.raw.brazil_details);
                break;

            case EGYPT:
                set_image_on_screen(linear, R.drawable.egypt);
                display_country_overview(linear, R.raw.egypt_overview);
                set_image_on_screen(linear, R.drawable.pyramids);
                display_country_details(linear, R.raw.egypt_details);
                break;

            case FRANCE:
                set_image_on_screen(linear, R.drawable.france);
                display_country_overview(linear, R.raw.france_overview);
                set_image_on_screen(linear, R.drawable.eiffel);
                display_country_details(linear, R.raw.france_details);
                break;

            case INDIA:
                set_image_on_screen(linear, R.drawable.india);
                display_country_overview(linear, R.raw.india_overview);
                set_image_on_screen(linear, R.drawable.taj);
                display_country_details(linear, R.raw.india_details);
                break;
        }

        return scroll;
    }

    /***
     * Used to initialize an image view in the linear vertical layout, setting the parameters of the
     * image view and loading the image based on the imageId passed.
     * @param linear    Linear layout instance
     * @param imageId   image to display
     */
    private void set_image_on_screen(LinearLayout linear, int imageId) {
        // Adding imageView to the LinearLayout
        ImageView image = new ImageView(getActivity());
        image.setImageResource(imageId);
        linear.addView(image);

        // Setting the configurations of the ImageView in the LinearLayout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 414;
        params.setMargins(0,10,0,10);
        image.setLayoutParams(params);
    }

    /**
     * Used to load the file mentioned in the overviewId, initialize a new TextView instance,
     * setting the content of the overview file into the text view instance and adding the text view
     * as a part of the linear layout.
     * @param linear        Linear layout instance
     * @param overviewId    File to load
     */
    private void display_country_overview(LinearLayout linear, int overviewId) {
        TextView overview = new TextView(getActivity());
        String content = "";
        String line;
        Resources res = getResources();

        try {
            InputStream file = res.openRawResource(overviewId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            while ((line = reader.readLine()) != null) {
                content = content.concat(line);
            }
            overview.setText(content);

        } catch (Exception e) {
            assert getParentFragment() != null;
            Toast toast = Toast.makeText(getActivity(),
                    "Can't read the description file",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        linear.addView(overview);
    }

    /**
     * Used to load the file mentioned in the countryId, initialize various textView to display details
     * like comversion_rate, placeToVisit, hotelsToStay, website, email, contact, distance, mode of
     * transport and cost, setting the content of the details file into all the text views instance
     * based on the tokens and adding the text views as a part of the linear layout.
     * @param linear    Linear layout instance
     * @param countryId File to load
     */
    private void display_country_details(LinearLayout linear, int countryId) {
        TextView conversion_rate = new TextView(getActivity());
        TextView placeToVisit = new TextView(getActivity());
        TextView hotelsToStay = new TextView(getActivity());
        TextView website = new TextView(getActivity());
        TextView email = new TextView(getActivity());
        TextView contact = new TextView(getActivity());
        TextView distance = new TextView(getActivity());
        TextView mode = new TextView(getActivity());
        TextView cost = new TextView(getActivity());
        String line;
        Resources res = getResources();

        try {
            InputStream file = res.openRawResource(countryId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer( line, ";");
            set_text(linear, conversion_rate, tokenizer.nextToken());
            set_text(linear, placeToVisit, tokenizer.nextToken());
            set_text(linear, hotelsToStay, tokenizer.nextToken());
            set_text(linear, website, tokenizer.nextToken(), Linkify.WEB_URLS);
            set_text(linear, email, tokenizer.nextToken(), Linkify.EMAIL_ADDRESSES);
            set_text(linear, contact, tokenizer.nextToken(), Linkify.PHONE_NUMBERS);
            set_text(linear, distance, tokenizer.nextToken());
            set_text(linear, mode, tokenizer.nextToken());
            set_text(linear, cost, tokenizer.nextToken());
        } catch (Exception e) {
            Toast toast = Toast.makeText( getActivity(),
                    "Can't read the details file",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /***
     * Set the text into the textView, set the parameters for the textView and add the textView
     * to the linear layout
     * @param linear    Linear Layout instance
     * @param text      TextView
     * @param content   text to set in the textView
     */
    private void set_text(LinearLayout linear, TextView text, String content) {
        text.setText(content);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,10);
        text.setLayoutParams(params);
        linear.addView(text);
    }

    /***
     * Set the text into the textView, set the parameters for the textView, add link to the
     * textView and add the textView to the linear layout
     * @param linear    Linear Layout instance
     * @param text      TextView
     * @param content   text to set in the textView
     * @param mask      Set the link to display on the textView
     */
    private void set_text(LinearLayout linear, TextView text, String content, int mask) {
        text.setText(content);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,10);
        text.setLayoutParams(params);
        Linkify.addLinks(text, mask);
        linear.addView(text);
    }
}