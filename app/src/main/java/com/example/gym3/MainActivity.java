package com.example.gym3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.gym3.database.GymLogRepository;
import com.example.gym3.database.entities.GymLog;
import com.example.gym3.database.entities.User;
import com.example.gym3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.gym3.MAIN_ACTIVITY_USER_ID";

    static final String SHARED_PREFERENCE_USERID_KEY= "com.example.gym3.SHARED_PREFERENCE_USERID_KEY";
//    static final String SHARED_PREFERENCE_USERID_VALUE= "com.example.gym3.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;
    private static final String SAVED_INSTANCE_STATE_USERID_KEY ="com.example.gym3.SAVED_INSTANCE_STATE_USERID_KEY" ;

    private ActivityMainBinding binding;



private GymLogRepository repository;

String mExercise = "";

double numWeight = 0.0;

int numReps =0;
// add login info
int loggedInUserId =-1;

    private User user;

public static final String TAG = "DAC GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // gives access to database
        repository = GymLogRepository.getRepository(getApplication());

        loginUser(savedInstanceState);



// tells software to check menu is goood? user not logged in at this point

        if(loggedInUserId == -1)
        {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        // will write username ot share pref
        updateSharedPreference();



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
                updateDisplay();
            }
        });

    }

    private void loginUser(Bundle savedInstanceState) {

// check intent for logged in user
SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//    loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, Context.MODE_PRIVATE);



        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

    if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY))
    {
        loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY , LOGGED_OUT);
    }
    if(loggedInUserId == LOGGED_OUT)
    {
        return;;
    }


        LiveData<User> userObserver = repository.getUserById(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user =user;
            if (this.user != null) {
                invalidateOptionsMenu();
                }




        });


    }


    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        // from method u made earlier
    updateSharedPreference();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        if(user == null){
            return false;
        }

        // set user name
        item.setTitle(user.getUsername());

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                // something to make alert dialog i think to interact with

         showLogOutDialog();
                return false;
            }
        });
        return true;
    }
    private void showLogOutDialog()
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        // helps makes sure only one dialog is shown at a time i think
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }


    //the log out method operation?
    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();



        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,loggedInUserId);
        //finish method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference()
    {
       SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
                sharedPrefEditor.apply();
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
        ArrayList<GymLog> allLogs = repository.getAllLogsByUserId(loggedInUserId);
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