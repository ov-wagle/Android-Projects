package edu.uga.cs.frugalshopper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The MainActivity is new activity created extending the AppCompatActivity in Android
 * This class retrieves all the details for product A, B and C, verifies if all the details are
 * provided correctly and accordingly either display a toast message if it finds any errors in the
 * input data or calculate the price to weight ratio and determine the best buy based on the lowest
 * value of price to weight ratio.
 **/
public class MainActivity extends AppCompatActivity {
    private static final String bestBuy = "Best Buy : ";

    // Input Details of A
    private EditText weightInLbsA;
    private EditText weightInOzA;
    private EditText amountOfA;

    // Input Details of B
    private EditText weightInLbsB;
    private EditText weightInOzB;
    private EditText amountOfB;

    // Input Details of C
    private EditText weightInLbsC;
    private EditText weightInOzC;
    private EditText amountOfC;

    // Compare Button and display result
    private Button compare;
    private EditText result;

    /**
    * This method is an overridden method over the android's onCreate
    * It calls the android's onCreate method and then loads the design stored in activity_main.xml
    * The method is use to retrieve the values entered in the nine blocks in total on the screen.
    * Once all the values are assigned to their respective instances, it sets the callback to be invoked
    * when the button press would happen, using new ButtonClickListener().
    * @param  savedInstanceState Provides the saved state of the application to the onCreate() method
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Input of A
        amountOfA = findViewById(R.id.editTextNumberDecimal);
        weightInLbsA = findViewById(R.id.editTextNumberDecimal18);
        weightInOzA = findViewById(R.id.editTextNumberDecimal21);

        // Input of B
        amountOfB = findViewById(R.id.editTextNumberDecimal2);
        weightInLbsB = findViewById(R.id.editTextNumberDecimal19);
        weightInOzB = findViewById(R.id.editTextNumberDecimal22);

        // Input of C
        amountOfC = findViewById(R.id.editTextNumberDecimal3);
        weightInLbsC = findViewById(R.id.editTextNumberDecimal20);
        weightInOzC = findViewById(R.id.editTextNumberDecimal23);

        // Comparing the inputs
        compare = findViewById(R.id.button);
        result = findViewById(R.id.editTextTextPersonName8);

        // set the task after clicking the compare button
        compare.setOnClickListener(new ButtonClickListener());
    }

    /**
     * This method is used to verify details entered for each product A, B and C.
     * The method verifies the condition wherein the price is entered and no weight details are entered,
     * a proper error message is displayed as toast or if only weights are entered and no price is
     * mentioned, it errors out. It also handles the condition where price is entered and either of
     * the weights are entered, in such case, the other weight category is initialised to 0.
     * If both the weights or price is entered as 0, a proper toast message is displayed.
     * @param  weightInLbs  Details carrying weight in lbs for a product
     * @param  weightInOz   Details carrying weight in oz for a product
     * @param  amountOfProd Details carrying price of the product
     * @param  productTag   String carrying what product's details are passed in other arguments.
     * @return              Returns the array containing the amount of the product in index 0 and
     *                      total weight of the product in ounces in index 1.
     **/
    private double[] verify_product_details(EditText weightInLbs, EditText weightInOz,
                                            EditText amountOfProd,
                                            String productTag) {
        double []productDetails = new double[]{0.0, 0.0};
        String pounds, ounces, amount;
        int poundsInNum = 0, ounceInNum = 0;
        final String incompleteData = "Enter either pounds or ounces for product ";
        final String noPriceEntered = "Price must be entered for product ";

        pounds = weightInLbs.getText().toString();
        ounces = weightInOz.getText().toString();
        amount = amountOfProd.getText().toString();

        // If price is provided for a product and both the weight fields are empty, display a
        // toast message
        if (!amount.isEmpty() && pounds.isEmpty() && ounces.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), incompleteData.concat(productTag),
                    Toast.LENGTH_SHORT);
            toast.show();
            result.setText("");
            return null;
        }

        // Assign the price field to the index 0. If either pound or ounce is empty, assign 0 to it
        if (!amount.isEmpty()) {
            productDetails[0] = Double.parseDouble(amount);

            try {
                poundsInNum = Integer.parseInt(pounds);
            } catch (NumberFormatException e) {
                poundsInNum = 0;
            }

            try {
                ounceInNum = Integer.parseInt(ounces);
            } catch (NumberFormatException e) {
                ounceInNum = 0;
            }
        }

        // If amount is empty and either of the weight fields is filled, reject this input as
        // price is a mandatory field and display a toast message indicating the same
        if (amount.isEmpty() && (!pounds.isEmpty() || !ounces.isEmpty())) {
            Toast toast = Toast.makeText(getApplicationContext(), noPriceEntered.concat(productTag),
                    Toast.LENGTH_SHORT);
            toast.show();
            result.setText("");
            return null;
        }

        // convert weight in pounds to ounces and add with the weight in ounce
        productDetails[1] = convert_weight_to_ounce(poundsInNum) + ounceInNum;

        // If price is non zero and final weight is 0, this indicates that both the entries in
        // pounds and ounce were zero which is invalid. This is indicated using a toast message
        if (productDetails[0] != 0.0 && productDetails[1] == 0.0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Either pounds or ounces must be non zero", Toast.LENGTH_SHORT);
            toast.show();
            result.setText("");
            return null;
        }

        // The final array contains the amount of the product and the total weight of product in Oz.
        return productDetails;
    }

    /**
     * This method converts the amount to its cents equivalent rounding to the nearest value.
     * @param  amount  Amount in dollars and cents
     * @return         Returns the equivalent value of the amount entered to its nearest cents.
     **/
    private long convert_amount_to_cents(double amount) {
        return Math.round(amount * 100);
    }

    /**
     * This method converts pounds to ounces.
     * @param  weight  weight in lbs
     * @return         Returns the ounces
     **/
    private int convert_weight_to_ounce(int weight) {
        return weight * 16;
    }

    /**
     * This class is provided as a callback class which is invoked every time the compare button is
     * pressed.
     * The class implements a method onClick() which is overridden
     * The class calls verify_product_details() method in order to verify the details of the product
     * entered by the user.
     * If verify_product_details() returns null, we return else we calculate the amount to weight
     * ratio. Based on which product has the minimum ratio, we displayed that product as best buy.
     **/
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Weight and amounts array is initialized to a size of 3 with all zeros.
            // Each index in these 2 arrays represent total weight and amount of product A,
            // B and C respectively
            double[] weights = new double[] {0.0, 0.0, 0.0};
            double[] amounts = new double[] {0.0, 0.0, 0.0};
            double[] priceAndWt;

            // Local variables to help calculate and display correct result
            double minCost = Double.MAX_VALUE;
            String[] productTag = {"A", "B", "C"};
            String productToShow = "";

            // Extract data for product A
            priceAndWt = verify_product_details(weightInLbsA, weightInOzA, amountOfA,
                                                  productTag[0]);

            if (priceAndWt == null) {
                return;
            }
            amounts[0] = priceAndWt[0];
            weights[0] = priceAndWt[1];

            // Extract data for product B
            priceAndWt = verify_product_details(weightInLbsB, weightInOzB, amountOfB,
                    productTag[1]);

            if (priceAndWt == null) {
                return;
            }
            amounts[1] = priceAndWt[0];
            weights[1] = priceAndWt[1];

            // Extract data for product C
            priceAndWt = verify_product_details(weightInLbsC, weightInOzC, amountOfC,
                    productTag[2]);

            if (priceAndWt == null) {
                return;
            }
            amounts[2] = priceAndWt[0];
            weights[2] = priceAndWt[1];

            for (int i = 0; i < 3; i++) {
                if (weights[i] == 0) {
                    continue;
                }

                if (amounts[i] == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Price cannot be zero", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }

                double cost = (double)convert_amount_to_cents(amounts[i]) / weights[i];
                if (cost < minCost) {
                    minCost = cost;
                    productToShow = productTag[i];
                }
            }

            // If productToShow is non empty, this indicates that at least one field was entered.
            // Else compare button was pressed when all the fields were empty
            if (!productToShow.isEmpty()) {
                result.setText(bestBuy.concat(productToShow));
            } else {
                result.setText(productToShow);
            }
        }
    }
}