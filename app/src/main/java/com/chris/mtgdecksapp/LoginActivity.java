package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.UserEntity;
import com.chris.mtgdecksapp.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.PASSWORD;
import static com.chris.mtgdecksapp.utility.Constants.USER;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private MTGAppRepository repository;
    private EditText userField, pwField;
    private Button button, buttonReset;
    private LiveData<List<UserEntity>> userEntities;
    private List<UserEntity> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MTGAppRepository.getInstance(getApplicationContext());
        repository.insertUserEntity(new UserEntity(1,USER,PASSWORD));

        button = binding.button;
        buttonReset = binding.button2;
        userField = binding.editTextUser;
        pwField = binding.editTextPW;

        userEntities = repository.getAllUsers();

        final Observer<List<UserEntity>> userObserver = newUsers ->{
            users.clear();
            users.addAll(newUsers);
        };
        userEntities.observe(this, userObserver);

        button.setOnClickListener(v -> {
            boolean userFound = false;
            UserEntity loginUser = new UserEntity(userField.getText().toString().trim(), pwField.getText().toString());
            for(UserEntity userEntity: users){
                if(loginUser.equals(userEntity)){
                    userFound = true;
                }
            }
            if(userFound){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Incorrect user name or password.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }

        });
        buttonReset.setOnClickListener( v -> {
            repository.clearDB();
            repository.clearUserEntity();
        });

    }
}
