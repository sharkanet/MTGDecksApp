package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.databinding.DeckDetailListItemBinding;
import com.chris.mtgdecksapp.databinding.SimpleCardsListItemBinding;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.List;

public class PlayGameAdapter extends RecyclerView.Adapter<PlayGameAdapter.ViewHolder> {
    private OnCardClickListener listener;
    private final List<String> cards;
    private final Context context;

    public PlayGameAdapter (List<String> cards, Context context){
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_cards_list_item, parent, false);
        return new PlayGameAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final String card = cards.get(position);
        holder.cardName.setText(card);
    }

    @Override
    public int getItemCount(){
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        SimpleCardsListItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = SimpleCardsListItemBinding.bind(itemView);
            cardName = binding.cardNameText;
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onCardClick(cards.get(position));
                }
            });
        }
    }

    public interface OnCardClickListener{
        void onCardClick(String card);
    }
    public void setOnCardClickListener(OnCardClickListener listener){
        this.listener = listener;
    }
}
