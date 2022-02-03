package edu.uga.cs.frugalshopper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_LOG = "Frugal Shopper";
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

    private long convert_amount_to_cents(double amount) {
        return Math.round(amount * 100);
    }

    private int convert_weight_to_ounce(int weight) {
        return weight * 16;
    }
    
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
                if (amounts[i] == 0 || weights[i] == 0) {
                    continue;
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