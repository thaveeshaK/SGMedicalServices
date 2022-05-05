package me.sanesh.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class BMIActivity extends AppCompatActivity {

    EditText weight, height;
    TextView value;
    LinearLayout resultLayout;
    MaterialButton calculate;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        weight = findViewById(R.id.txtWeight);
        height = findViewById(R.id.txtHeight);
        value = findViewById(R.id.txtBMIValue);
        resultLayout = findViewById(R.id.resLayout);
        calculate = findViewById(R.id.btnCalculateBMI);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if ((weight.getText() != null) && (height.getText() != null)) {
                        double w = Double.parseDouble(weight.getText().toString());
                        double h = Double.parseDouble(height.getText().toString());
                        double bmi = w / (h * h);;
                        bmi = Math.round(bmi * 100.0) / 100.0;

                        resultLayout.setVisibility(View.VISIBLE);
                        value.setText(bmi+"");


                        if (bmi < 18.5) {
                            result = ("You are underweight");
                        } else if (bmi < 25) {
                            result = ("You are normal");
                        } else if (bmi < 30) {
                            result = ("You are overweight");
                        } else {
                            result = ("You are obese");
                        }

                        Toast.makeText(BMIActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BMIActivity.this, "Please enter valid values", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BMIActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}