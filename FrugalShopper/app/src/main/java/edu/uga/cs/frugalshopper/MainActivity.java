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
    private EditText productNameA;
    private EditText weightOfA;
    private EditText amountOfA;

    // Input Details of B
    private EditText productNameB;
    private EditText weightOfB;
    private EditText amountOfB;

    // Input Details of C
    private EditText productNameC;
    private EditText weightOfC;
    private EditText amountOfC;

    // Compare Button
    private Button compare;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Input of A
        productNameA = findViewById(R.id.editTextTextPersonName3);
        weightOfA = findViewById(R.id.editTextNumberDecimal18);
        amountOfA = findViewById(R.id.editTextNumberDecimal21);

        // Input of B
        productNameB = findViewById(R.id.editTextTextPersonName5);
        weightOfB = findViewById(R.id.editTextNumberDecimal19);
        amountOfB = findViewById(R.id.editTextNumberDecimal22);

        // Input of C
        productNameC = findViewById(R.id.editTextTextPersonName7);
        weightOfC = findViewById(R.id.editTextNumberDecimal20);
        amountOfC = findViewById(R.id.editTextNumberDecimal23);

        // Comparing the inputs
        compare = findViewById(R.id.button);
        result = findViewById(R.id.editTextTextPersonName8);

        // set the task after clicking the compare button
        compare.setOnClickListener(new ButtonClickListener());
    }

    private double[] verify_product_details(EditText productName, EditText weightOfProd,
                                            EditText amountOfProd,
                                            String productTag) {
        double []productDetails = new double[2];
        String weight, amount;
        final String incompleteData = "Enter complete details for product ";
        String product;

        product = productName.getText().toString();
        weight = weightOfProd.getText().toString();
        amount = amountOfProd.getText().toString();

        if (product.isEmpty() && (!weight.isEmpty() || !amount.isEmpty())) {
            Toast toast = Toast.makeText(getApplicationContext(), incompleteData.concat(productTag),
                    Toast.LENGTH_SHORT);
            toast.show();
            result.setText("");
            return null;
        }

        if (!product.isEmpty()) {
            try {
                productDetails[0] = Double.parseDouble(weight);
                productDetails[1] = Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(getApplicationContext(), incompleteData.concat(productTag),
                        Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return null;
            }
        }

        if (!product.isEmpty()) {
            if (productDetails[0] <= 0 || productDetails[1] <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Value cannot be less than equal to 0", Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return null;
            }
        }

        return productDetails;
    }

    private long convert_amount_to_cents(double amount) {
        return Math.round(amount * 100);
    }

    private long convert_weight_to_ounce(double weight) {
        int pounds = (int)weight;
        double ounce = weight - pounds;

        return (pounds * 16) + Math.round(16 * ounce);
    }
    
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            double[] weights = new double[] {0.0, 0.0, 0.0};
            double[] amounts = new double[] {0.0, 0.0, 0.0};
            double[] weightAndAmt;

            // Local variables to help calculate and display correct result
            //String weight, amount;
            double minCost = Double.MAX_VALUE;
            String [] productTag = {"A", "B", "C"};
            String productToShow = "";

            // Extract data for product A
            weightAndAmt = verify_product_details(productNameA, weightOfA, amountOfA,
                                                  productTag[0]);

            if (weightAndAmt == null) {
                return;
            }

            weights[0] = weightAndAmt[0];
            amounts[0] = weightAndAmt[1];

            // Extract data for product B
            weightAndAmt = verify_product_details(productNameB, weightOfB, amountOfB,
                    productTag[1]);

            if (weightAndAmt == null) {
                return;
            }

            weights[1] = weightAndAmt[0];
            amounts[1] = weightAndAmt[1];

            // Extract data for product C
            weightAndAmt = verify_product_details(productNameC, weightOfC, amountOfC,
                    productTag[2]);

            if (weightAndAmt == null) {
                return;
            }

            weights[2] = weightAndAmt[0];
            amounts[2] = weightAndAmt[1];

            for (int i = 0; i < 3; i++) {
                if (amounts[i] == 0.0 || weights[i] == 0.0) {
                    continue;
                }

                double cost = (double)convert_amount_to_cents(amounts[i]) / convert_weight_to_ounce(weights[i]);
                if (cost < minCost) {
                    minCost = cost;
                    productToShow = productTag[i];
                }
            }

            if (!productToShow.isEmpty()) {
                result.setText(bestBuy.concat(productToShow));
            } else {
                result.setText(productToShow);
            }

            return;
        }
    }
}