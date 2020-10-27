package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.AddCardSupertypeActivity;
import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.SupertypeEntity;

import java.util.ArrayList;
import java.util.List;

public class AddCardSupertypeViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<SupertypeEntity>> supertypeEntities;


    public AddCardSupertypeViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        supertypeEntities = repository.getAllSuperTypeEntity();
    }

    public LiveData<List<SupertypeEntity>> getSupertypeEntities() {
        return supertypeEntities;
    }
}
