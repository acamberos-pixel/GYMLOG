package com.example.gym3;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym3.databinding.ActivityMainBinding;

import java.util.Locale;



public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;



String mExercise = "";

double numWeight = 0.0;

int numReps =0;

public static final String TAG = "DAC GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());




        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // makes some alert i think
                getInformationFromDisplay();
                updateDisplay();

            }
        });

    }

// pull value from screen input idk where yet
    private void updateDisplay()
    {
        String currentInfo = binding.logDisplayTextView.getText().toString();
        Log.d(TAG,"current info" +currentInfo);
        String newDisplay = String.format(Locale.US,"Exercise: %s%nWeight:%.2f%nReps:%d%n:=-=-=-%n%s:",mExercise,numWeight,numReps, currentInfo);

        binding.logDisplayTextView.setText(newDisplay);
    }
    private void getInformationFromDisplay()
    {
        mExercise = binding.exerciseInputEditText.getText().toString();
try {
    numWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
} catch (NumberFormatException e)

{
    Log.d(TAG , "error reading value from weight edit test") ;
}

try
{
    numReps = Integer.parseInt(binding.repInputEditText.getText().toString());
}catch (NumberFormatException e)
    {
    Log.d(TAG , "error reading value from rep value edit test");
    }
    }
}