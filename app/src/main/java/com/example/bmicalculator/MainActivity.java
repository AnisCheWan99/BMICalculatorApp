package com.example.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCalculate, btnReset;
    EditText etWeight, etHeight;
    TextView tvBmi, tvCategory, tvRisk;
    float weight, height, bmi;

    SharedPreferences sharedPref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bmi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "About Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AboutPage.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "BMI Calculator Page", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("BMI CALCULATOR");

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnReset = (Button) findViewById(R.id.btnReset);

        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);
        tvBmi = (TextView) findViewById(R.id.tvBmi);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvRisk = (TextView) findViewById(R.id.tvRisk);

        btnCalculate.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        weight = 0;
        height = 0;
        bmi = 0;

        sharedPref = this.getSharedPreferences( "wt", Context.MODE_PRIVATE);
        weight = sharedPref.getInt("wt", 0);
        sharedPref = this.getSharedPreferences( "wt", Context.MODE_PRIVATE);
        height = sharedPref.getInt("ht", 0);

        etHeight.setText(""+ height);
        etWeight.setText(""+weight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnCalculate:
                calculateBMI();
                break;

            case R.id.btnReset:
                resetBMI();
                break;
        }
    }

    public void calculateBMI()
    {
        try {
            weight = Float.parseFloat(etWeight.getText().toString());
            height = Float.parseFloat(etHeight.getText().toString());
            float newHeight = height/100;
            bmi = weight/(newHeight*newHeight);
            etHeight.setText(""+ height);
            etWeight.setText(""+weight);
            if (bmi <= 18.4)
            {
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Underweight");
                tvRisk.setText("Risk : Malnutrition risk");
            }
            else if (bmi >= 18.5 && bmi <= 24.9 )
            {
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Normal weight");
                tvRisk.setText("Risk : Low risk");
            }
            else if (bmi >= 25 && bmi <= 29.9 )
            {
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Overweight");
                tvRisk.setText("Risk : Enhanced risk");
            }
            else if (bmi >= 30 && bmi <= 34.9 )
            {
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Moderately obese");
                tvRisk.setText("Risk : Medium risk");
            }
            else if (bmi >= 35 && bmi <= 39.9 )
            {
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Severely obese");
                tvRisk.setText("Risk : High risk");
            }
            else if (bmi >= 40){
                tvBmi.setText(String.format("Your BMI is: %.2f", bmi));
                tvCategory.setText("Category : Very severely obese");
                tvRisk.setText("Risk : Very high risk");
            }
            else {
                tvBmi.setText("");
                tvCategory.setText("");
                tvRisk.setText("");
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("ht", (int) height);
            editor.putInt("wt", (int) weight);
            editor.apply();

        } catch (NumberFormatException nfe) {

            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {

            Toast.makeText(this, "Unknown Exception" + exp.getMessage(), Toast.LENGTH_SHORT).show();

            Log.d("Exception", exp.getMessage());

        }

    }

    public void resetBMI()
    {
        weight = 0;
        height = 0;
        etWeight.setText("");
        etHeight.setText("");
        tvBmi.setText("");
        tvCategory.setText("");
        tvRisk.setText("");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ht", (int) height);
        editor.putInt("wt", (int) weight);
        editor.apply();
    }
}