package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.databinding.DecksListItemBinding;
import com.chris.mtgdecksapp.databinding.GamesListItemBinding;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private final List<GameEntity> games;
    private final Context context;
    private OnItemClickListener listener;

    public GamesAdapter(List<GameEntity> games, Context context){
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.games_list_item, parent, false);
        return new GamesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final GameEntity gameEntity = games.get(position);
        holder.textOpponent.setText(gameEntity.getOpponent());
        holder.textOpponentDeck.setText(gameEntity.getOpponentDeckName());
        holder.textResult.setText(gameEntity.getResult());
    }

    @Override
    public int getItemCount(){
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textOpponent, textOpponentDeck, textResult;
        GamesListItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding = GamesListItemBinding.bind(itemView);
            textOpponent = binding.opponentName;
            textOpponentDeck = binding.opponentDeck;
            textResult = binding.result;
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!= null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(games.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(GameEntity deck);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
