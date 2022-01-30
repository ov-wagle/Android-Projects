package edu.uga.cs.frugalshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_LOG = "Frugal Shopper";
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
    
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String nameOfProductA;
            double weightA = 0.0;
            double amountA = 0.0;

            String nameOfProductB;
            double weightB;
            double amountB;

            String nameOfProductC;
            double weightC;
            double amountC;

            String weight, amount;

            // Parsing inputs for text field A
            nameOfProductA = productNameA.getText().toString();
            weight = weightOfA.getText().toString();
            amount = amountOfA.getText().toString();

            if (nameOfProductA.isEmpty() && (!weight.isEmpty() || !amount.isEmpty())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Enter complete details for product A", Toast.LENGTH_SHORT);
                toast.show();
                result.setText("");
                return;
            }

            if (!nameOfProductA.isEmpty()) {
                try {
                    weightA = Double.parseDouble(weight);
                    amountA = Double.parseDouble(amount);
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product A", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            nameOfProductB = productNameB.getText().toString();
            if (!nameOfProductB.isEmpty()) {
                try {
                    weightB = Double.parseDouble(weightOfB.getText().toString());
                    amountB = Double.parseDouble(amountOfB.getText().toString());
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product B", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }

            nameOfProductC = productNameC.getText().toString();
            if (!nameOfProductC.isEmpty()) {
                try {
                    weightC = Double.parseDouble(weightOfC.getText().toString());
                    amountC = Double.parseDouble(amountOfC.getText().toString());
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Enter complete details for product C", Toast.LENGTH_SHORT);
                    toast.show();
                    result.setText("");
                    return;
                }
            }
        }
    }
}