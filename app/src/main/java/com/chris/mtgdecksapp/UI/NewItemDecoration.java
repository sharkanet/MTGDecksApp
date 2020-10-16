package com.chris.mtgdecksapp.UI;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontal, vertical;
    public NewItemDecoration(int horizontal, int vertical){
        this.horizontal= horizontal;
        this.vertical= vertical;
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = horizontal;
        outRect.left = horizontal;
        if(parent.getChildLayoutPosition(view) == 0){
            outRect.top = vertical;
        }
        outRect.bottom = vertical;
    }
}
