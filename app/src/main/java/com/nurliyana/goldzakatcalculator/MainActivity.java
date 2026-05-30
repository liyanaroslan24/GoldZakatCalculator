package com.nurliyana.goldzakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etValue;
    private RadioGroup radioGroupType;
    private RadioButton rbKeep, rbWear;
    private Button btnCalculate;

    private TextView txtTotalValue;
    private TextView txtPayable;
    private TextView txtZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar Customization
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            getSupportActionBar().setTitle("GZ Calculator");
        }

        // Initialize Views
        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);

        radioGroupType = findViewById(R.id.radioGroupType);

        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);

        btnCalculate = findViewById(R.id.btnCalculate);

        txtTotalValue = findViewById(R.id.txtTotalValue);
        txtPayable = findViewById(R.id.txtPayable);
        txtZakat = findViewById(R.id.txtZakat);

        btnCalculate.setOnClickListener(v -> calculateZakat());
    }

    private void calculateZakat() {

        String weightText = etWeight.getText().toString().trim();
        String valueText = etValue.getText().toString().trim();

        // Validation
        if (weightText.isEmpty()) {
            etWeight.setError("Please enter gold weight");
            return;
        }

        if (valueText.isEmpty()) {
            etValue.setError("Please enter gold value");
            return;
        }

        if (radioGroupType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(
                    this,
                    "Please select gold type",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double weight = Double.parseDouble(weightText);
        double value = Double.parseDouble(valueText);

        double nisab;

        if (rbKeep.isChecked()) {
            nisab = 85;
        } else {
            nisab = 200;
        }

        double totalGoldValue = weight * value;

        double payableWeight = weight - nisab;

        if (payableWeight < 0) {
            payableWeight = 0;
        }

        double zakatPayableValue = payableWeight * value;

        double totalZakat = zakatPayableValue * 0.025;

        txtTotalValue.setText(
                String.format(
                        "Total Gold Value: RM %.2f",
                        totalGoldValue
                )
        );

        txtPayable.setText(
                String.format(
                        "Zakat Payable Value: RM %.2f",
                        zakatPayableValue
                )
        );

        txtZakat.setText(
                String.format(
                        "Total Zakat: RM %.2f",
                        totalZakat
                )
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // About Page
        if (id == R.id.menuAbout) {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AboutActivity.class
                    );

            startActivity(intent);

            return true;
        }

        // Share App
        if (id == R.id.menuShare) {

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Gold Zakat Calculator\n\nGitHub Repository:\nhttps://github.com/liyanaroslan24/GoldZakatCalculator"
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share App"
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}