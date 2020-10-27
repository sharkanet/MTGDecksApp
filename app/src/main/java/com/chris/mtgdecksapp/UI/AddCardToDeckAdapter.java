package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.AddCardToDeckActivity;
import com.chris.mtgdecksapp.CardEditActivity;
import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.databinding.ActivityAddCardToDeckBinding;
import com.chris.mtgdecksapp.databinding.CardsListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AddCardToDeckAdapter extends RecyclerView.Adapter<AddCardToDeckAdapter.ViewHolder> implements Filterable {
    private OnCardClickListener listener;
    private final List<CardEntity> cards;
    private final Context context;
    private final List<CardEntity> cardsFull;

    public AddCardToDeckAdapter(List<CardEntity> cards, Context context){
        this.cards = cards;
        this.context = context;
        cardsFull = new ArrayList<>(cards);
    }
    public void updateCardsFull(){
        cardsFull.clear();
        cardsFull.addAll(cards);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_list_item, parent, false);
        return new AddCardToDeckAdapter.ViewHolder(view);
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

    @Override
    public Filter getFilter() {
        return cardFilter;
    }

    private Filter cardFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CardEntity> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(cardsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(CardEntity cardEntity: cardsFull){
                    if(cardEntity.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(cardEntity);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cards.clear();
            cards.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };




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
