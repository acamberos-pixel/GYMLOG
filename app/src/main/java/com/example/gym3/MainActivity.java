package com.example.gym3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym3.database.GymLogRepository;
import com.example.gym3.database.entities.GymLog;
import com.example.gym3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.gym3.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;

private GymLogRepository repository;

String mExercise = "";

double numWeight = 0.0;

int numReps =0;
// add login info
int loggedInUserId =-1;

public static final String TAG = "DAC GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        loginUser();
        if(loggedInUserId == -1)
        {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // gives access to database
        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();


        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // makes some alert i think
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();

            }
        });
        binding.exerciseInputEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

    }

    private void loginUser() {

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,-1);
        // to do create login method
    }

static Intent mainActivityIntentFactory(Context context, int userId)
{
    Intent intent = new Intent(context, MainActivity.class);
     intent.putExtra(MAIN_ACTIVITY_USER_ID,userId);
     return intent;
}
    private void insertGymLogRecord()
    {
        if(mExercise.isEmpty())
        {
            return;
        }

        GymLog log = new GymLog(mExercise,numWeight, numReps, loggedInUserId);
        repository.insertGymLog(log);
    }
// pull value from screen input idk where yet
    private void updateDisplay()
    {
        ArrayList<GymLog> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty())
        {
            binding.logDisplayTextView.setText(R.string.nothing_to_show_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for(GymLog log : allLogs)
        {
            sb.append(log);

        }


        binding.logDisplayTextView.setText(sb.toString());


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