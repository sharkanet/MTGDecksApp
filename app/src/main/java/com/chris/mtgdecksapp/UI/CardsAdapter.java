package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.databinding.CardsListItemBinding;

import java.util.List;

public class CardsAdapter extends  RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private OnCardClickListener listener;
    private final List<CardEntity> cards;
    private final Context context;

    public CardsAdapter(List<CardEntity> cards, Context context){
        this.cards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_list_item, parent, false);
        return new CardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final CardEntity card = cards.get(position);
        holder.textCardName.setText(card.getName());
        holder.textCardMana.setText(card.getManaCost());
        holder.textCardText.setText(card.getText());
    }

    @Override
    public int getItemCount(){
        return cards.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textCardName, textCardMana, textCardText;
        CardsListItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = CardsListItemBinding.bind(itemView);
            textCardName = binding.cardNameText;
            textCardMana = binding.cardManaCost;
            textCardText = binding.cardText;
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onCardClick(cards.get(position));
                }
            });
        }
    }
    public interface OnCardClickListener{
            void onCardClick(CardEntity card);
    }

    public void setOnCardClickListener(OnCardClickListener listener){
            this.listener = listener;
    }

}
