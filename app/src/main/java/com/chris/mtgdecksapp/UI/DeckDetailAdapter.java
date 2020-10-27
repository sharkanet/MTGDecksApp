package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardInDeckDao;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.databinding.DeckDetailListItemBinding;
import com.chris.mtgdecksapp.databinding.DecksListItemBinding;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.ArrayList;
import java.util.List;

public class DeckDetailAdapter extends RecyclerView.Adapter<DeckDetailAdapter.ViewHolder> {
    private OnCardClickListener listener;
    private final List<CardInDeck> cards;
    private final Context context;

    public DeckDetailAdapter (List<CardInDeck> cards, Context context){
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.deck_detail_list_item, parent, false);
        return new DeckDetailAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final CardInDeck cardEntity = cards.get(position);
        holder.cardName.setText(cardEntity.getName());
        holder.cardMana.setText(cardEntity.getManaCost());
        holder.cardQuantity.setText("x"+cardEntity.getQuantity());
        if(cardEntity.isCurrentlyInDeck()){
            holder.button.setText("In");
            holder.button.setBackgroundColor(Color.GREEN);
        } else {
            holder.button.setText("Out");
            holder.button.setBackgroundColor(Color.RED);
        }


    }

    @Override
    public int getItemCount(){
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        TextView cardMana;
        TextView cardQuantity;
        Button button;
        DeckDetailListItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = DeckDetailListItemBinding.bind(itemView);
            cardName = binding.cardNameText;
            cardMana = binding.cardManaCost;
            cardQuantity = binding.cardQuantity;
            button = binding.inOut;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onCardClick(cards.get(position));
                }
            });
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onLongClick(cards.get(position));
                }
                return true;
            });
        }
    }

    public interface OnCardClickListener{
        void onCardClick(CardInDeck card);
        void onLongClick(CardInDeck card);
    }
    public void setOnCardClickListener(OnCardClickListener listener){
        this.listener = listener;
    }
}


