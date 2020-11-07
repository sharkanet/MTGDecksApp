package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.DecksActivity;
import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.databinding.DecksListItemBinding;
import com.chris.mtgdecksapp.model.Deck;

import java.util.List;

public class  DecksAdapter extends RecyclerView.Adapter<DecksAdapter.ViewHolder> {
    private OnDeckClickListener listener;
    private final List<DeckEntity> decks;
    private final Context context;

    public DecksAdapter(List<DeckEntity> decks, Context context){
        this.decks = decks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.decks_list_item, parent, false);
        return new DecksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final DeckEntity deck = decks.get(position);
        holder.textDeckName.setText(deck.getName());
        if(deck.isCommanderDeck()){
            holder.cmdText.setVisibility(View.VISIBLE);
        } else
            holder.cmdText.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount(){
        return decks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textDeckName, cmdText;
        DecksListItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = DecksListItemBinding.bind(itemView);
            textDeckName = binding.deckNameText;
            cmdText = binding.cmdText;
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onDeckClick(decks.get(position));
                }
            });
        }
    }

    public interface OnDeckClickListener{
        void onDeckClick(DeckEntity deck);
    }
    public void setOnDeckClickListener(OnDeckClickListener listener){
        this.listener = listener;
    }
}
