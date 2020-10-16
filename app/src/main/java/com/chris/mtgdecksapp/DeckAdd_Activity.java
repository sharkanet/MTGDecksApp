package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chris.mtgdecksapp.ViewModel.DeckAddViewModel;
import com.chris.mtgdecksapp.databinding.ActivityDeckAddBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class DeckAdd_Activity extends AppCompatActivity {
    private ActivityDeckAddBinding binding;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private TextInputEditText nameField;
    private DeckAddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeckAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nameField = binding.editTextName;
        initViewModel();

        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Add Deck");

        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO
                viewModel.save(nameField.getText().toString().trim());
                finish();
            }
        });
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(DeckAddViewModel.class);
    }
}
