package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.databinding.ActivityMainBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ToolbarBinding toolbarBinding;
    private MTGAppRepository repository;
    private Button btnToDecks, btnToCards, btnToPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = MTGAppRepository.getInstance(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);

        //bind and setup buttons
        btnToDecks = binding.button1;
        btnToDecks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DecksActivity.class);
            startActivity(intent);
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.main_menu);
        return true;
    }


}
