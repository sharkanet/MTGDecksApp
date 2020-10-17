package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chris.mtgdecksapp.databinding.ActivityCardAddBinding;
import com.chris.mtgdecksapp.databinding.ActivityCardDetailBinding;

public class CardAddActivity extends AppCompatActivity {
    private ActivityCardAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
