package edu.uga.cs.frugalshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            String [] nameOfProducts = new String[] {null, null, null};
            double[] weights = new double[] {0.0, 0.0, 0.0};
            double[] amounts = new double[] {0.0, 0.0, 0.0};

            // Local variables to help calculate and display correct result
            String weight, amount;
            double calculation = Double.MAX_VALUE;
            String [] productTag = {"A", "B", "C"};
            String productToShow = "";

            // Parsing inputs for text field A
            nameOfProducts[0] = productNameA.getText().toString();
            weight = weightOfA.getText().toString();
            amount = amountOfA.getText().toString();

            if (nameOfProducts[0].isEmpty() && (!weight.isEmpty() || !amount.isEmpty())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter complete details for product A", Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return;
            }

            if (!nameOfProducts[0].isEmpty()) {
                try {
                    weights[0] = Double.parseDouble(weight);
                    amounts[0] = Double.parseDouble(amount);
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product A", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            if (!nameOfProducts[0].isEmpty()) {
                if (weights[0] <= 0 || amounts[0] <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Value cannot be less than equal to 0", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            nameOfProducts[1] = productNameB.getText().toString();
            weight = weightOfB.getText().toString();
            amount = amountOfB.getText().toString();

            if (nameOfProducts[1].isEmpty() && (!weight.isEmpty() || !amount.isEmpty())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter complete details for product B", Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return;
            }

            if (!nameOfProducts[1].isEmpty()) {
                try {
                    weights[1] = Double.parseDouble(weight);
                    amounts[1] = Double.parseDouble(amount);
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product B", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            if (!nameOfProducts[1].isEmpty()) {
                if (weights[1] <= 0 || amounts[1] <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Value cannot be less than equal to 0", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            nameOfProducts[2] = productNameC.getText().toString();
            weight = weightOfC.getText().toString();
            amount = amountOfC.getText().toString();

            if (nameOfProducts[2].isEmpty() && (!weight.isEmpty() || !amount.isEmpty())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter complete details for product C", Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return;
            }

            if (!nameOfProducts[2].isEmpty()) {
                try {
                    weights[2] = Double.parseDouble(weight);
                    amounts[2] = Double.parseDouble(amount);
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product C", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            if (!nameOfProducts[2].isEmpty()) {
                if (weights[2] <= 0 || amounts[2] <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Value cannot be less than equal to 0", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            for (int i = 0; i < 3; i++) {
                if (amounts[i] == 0.0 || weights[i] == 0.0) {
                    continue;
                }

                double cost = (double)convert_amount_to_cents(amounts[i]) / convert_weight_to_ounce(weights[i]);
                if (cost < calculation) {
                    calculation = cost;
                    productToShow = productTag[i];
                }
            }

            result.setText(bestBuy.concat(productToShow));
            return;
        }
    }
}