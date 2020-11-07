package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.chris.mtgdecksapp.ViewModel.DeckAddViewModel;
import com.chris.mtgdecksapp.databinding.ActivityDeckAddBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class DeckAddActivity extends AppCompatActivity {
    private ActivityDeckAddBinding binding;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private TextInputEditText nameField;
    private DeckAddViewModel viewModel;
    private CheckBox checkBox;

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

        checkBox = binding.checkBox;

        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(nameField.getText().toString().trim().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeckAddActivity.this);
                    builder.setMessage("Enter a Name.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                } else {
                    if(checkBox.isChecked()){
                        viewModel.save(nameField.getText().toString().trim(), true);
                        finish();

                    } else {
                        viewModel.save(nameField.getText().toString().trim(), false);
                        finish();

                    }
                }
            }
        });
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(DeckAddViewModel.class);
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
                Intent intent= new Intent(DeckAddActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
