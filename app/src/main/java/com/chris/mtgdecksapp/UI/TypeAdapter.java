package com.chris.mtgdecksapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chris.mtgdecksapp.R;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends ArrayAdapter<TypeEntity> {
    private List<TypeEntity> typeEntities;
    private Context context;
    private int itemLayout;
    private ListFilter listFilter = new ListFilter();
    private List<TypeEntity> dataListAllItems;

    private static class ViewHolder{
        TextView type;
    }

    public TypeAdapter(Context context, int itemLayout, List<TypeEntity> list){
        super(context, itemLayout, list);
        this.context = context;
        this.typeEntities = list;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        TypeEntity currentTypeEntity = typeEntities.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(itemLayout, parent, false);
            viewHolder.type = (TextView) convertView.findViewById(R.id.typeListItemText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.type.setText(currentTypeEntity.getType());
        return convertView;
    }

    @Override
    public int getCount() {
        return typeEntities.size();
    }

    @Nullable
    @Override
    public TypeEntity getItem(int position) {
        return typeEntities.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    // the filter
    public class ListFilter extends Filter {
        private Object lock = new Object();
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(dataListAllItems == null){
                synchronized (lock){
                    dataListAllItems = new ArrayList<>(typeEntities);
                }
            }
            if(constraint == null|| constraint.length()==0){
                synchronized (lock){
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = constraint.toString().toLowerCase();
                ArrayList<TypeEntity> matches = new ArrayList<>();
                for(TypeEntity typeEntity: dataListAllItems){
                    if(typeEntity.getType().toLowerCase().contains(searchStrLowerCase)){
                        matches.add(typeEntity);
                    }
                }
                results.values= matches;
                results.count = matches.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values!= null){
                typeEntities = (ArrayList<TypeEntity>) results.values;
            } else {
                typeEntities = null;
            }
            if(results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
