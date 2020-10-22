package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chris.mtgdecksapp.UI.DecksAdapter;
import com.chris.mtgdecksapp.UI.SupertypeAdapter;
import com.chris.mtgdecksapp.ViewModel.AddCardSupertypeViewModel;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.databinding.ActivityAddCardSupertypeBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

import static com.hootsuite.nachos.terminator.ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL;

public class AddCardSupertypeActivity extends AppCompatActivity {
    private ActivityAddCardSupertypeBinding binding;
    private NachoTextView nachoTextView;
    private ToolbarBinding toolbarBinding;
    private AddCardSupertypeViewModel viewModel;
    private SupertypeAdapter adapter;
    private List<SupertypeEntity> supertypeEntities = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardSupertypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //recyclerView = binding.recyclerView;
        listView = binding.listView;
        //init nachoView
        initNachoView();
        nachoTextView = binding.nachoTextView;

        initViewModel();

  //      initRecyclerView();
        initListView();

        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Add Supertypes");
    }




    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AddCardSupertypeViewModel.class);
        final Observer<List<SupertypeEntity>> supertypeObserver = newSupertype ->{
            supertypeEntities.clear();
            supertypeEntities.addAll(newSupertype);
            if(adapter ==null){
                adapter = new SupertypeAdapter(this, R.layout.supertype_list_item ,supertypeEntities);
            } else {
                adapter.notifyDataSetChanged();
            }
        };
        viewModel.getSupertypeEntities().observe(this, supertypeObserver);
    }

//    private void initRecyclerView() {
//        //setup recyclerview
//        recyclerView = binding.recyclerView;
//        adapter = new SupertypeAdapter(this, supertypeEntities);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//    }
    private void initListView() {
        listView = binding.listView;
        adapter = new SupertypeAdapter(this,R.layout.supertype_list_item ,supertypeEntities);
        listView.setAdapter(adapter);
    }
    private void initNachoView() {
        nachoTextView = binding.nachoTextView;
        adapter = new SupertypeAdapter(this,R.layout.supertype_list_item, supertypeEntities);
        nachoTextView.setAdapter(adapter);
        nachoTextView.addChipTerminator('\n',BEHAVIOR_CHIPIFY_ALL);
        nachoTextView.setText("first second thrid");
        nachoTextView.chipifyAllUnterminatedTokens();
    }
}
