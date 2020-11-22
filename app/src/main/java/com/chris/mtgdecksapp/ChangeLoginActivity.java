package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.UserEntity;
import com.chris.mtgdecksapp.databinding.ActivityChangeLoginBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;

public class ChangeLoginActivity extends AppCompatActivity {
    ActivityChangeLoginBinding binding;
    MTGAppRepository repository;
    Button button;
    EditText userField, passwordField, passwordField2;
    ToolbarBinding toolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = MTGAppRepository.getInstance(getApplicationContext());


        button = binding.button;
        userField = binding.editTextUser;
        passwordField = binding.editTextPW;
        passwordField2 = binding.editTextPWAgain;
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Change Login");

        button.setOnClickListener(v -> {
            if(userField.getText().toString().trim().equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeLoginActivity.this);
                builder.setMessage("Enter a user name.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            } else if(passwordField.getText().toString().equals(passwordField2.getText().toString())){
                repository.updateUserEntity(new UserEntity(1, userField.getText().toString().trim(), passwordField.getText().toString()));
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeLoginActivity.this);
                builder.setMessage("Passwords do not match.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.basic_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Intent intent= new Intent(ChangeLoginActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
