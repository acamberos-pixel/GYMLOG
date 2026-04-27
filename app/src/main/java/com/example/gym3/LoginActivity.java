package com.example.gym3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym3.database.GymLogRepository;
import com.example.gym3.database.entities.User;
import com.example.gym3.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;


    private GymLogRepository repository;


    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!verifyUser())
                {
                   toastMaker("invalid credentioals");
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });


    }

    private boolean verifyUser()
    {
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty())
        {
         toastMaker("username shouldnt be blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if (user != null)
        {
            String password = binding.passwordLoginEditText.getText().toString();
            if (password.equals(user.getPassword()))
            {
              return true;
            } else
            {
                toastMaker("invalid password");
                return false;
            }
        }
        toastMaker(String.format("no user %s found" ,username));
        return false;

    }

    private void toastMaker(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    static Intent loginIntentFactory(Context context)
    {
        return new Intent(context, LoginActivity.class);

    }
}