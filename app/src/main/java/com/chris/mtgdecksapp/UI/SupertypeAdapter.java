package com.chris.mtgdecksapp.UI;

import android.app.Activity;
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
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.databinding.SupertypeListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SupertypeAdapter extends ArrayAdapter<SupertypeEntity> {
    private List<SupertypeEntity> supertypeEntities;
    private Context context;
    private int itemLayout;
    private ListFilter listFilter = new ListFilter();
    private List<SupertypeEntity> dataListAllItems;


    public SupertypeAdapter(@NonNull Context context, int itemLayout, List<SupertypeEntity> list){
        super(context, itemLayout, list);
        this.supertypeEntities = list;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = null;
//        ViewHolder viewHolder;
        SupertypeEntity currentSupertype = supertypeEntities.get(position);
        if(convertView == null){
//            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            view = inflater.inflate(R.layout.supertype_list_item, parent, false);
            view = LayoutInflater.from(context).inflate(itemLayout, parent,false);
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.supertype_list_item, parent, false);
//            viewHolder.supertypeName = (TextView) convertView.findViewById(R.id.supertypeListItemText);
        } else  {
            view = convertView;
          //  viewHolder = (ViewHolder) convertView.getTag();
        }

        TextView supertypeName = (TextView) view.findViewById(R.id.supertypeListItemText);
        supertypeName.setText(currentSupertype.getSupertype());
     //   viewHolder.supertypeName.setText(currentSupertype.getSupertype());
      //  return convertView;
        return view;
    }

    private static class ViewHolder{
        TextView supertypeName;
    }

    @Override
    public int getCount() {
        return supertypeEntities.size();
    }

    @Nullable
    @Override
    public SupertypeEntity getItem(int position) {
        return supertypeEntities.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter{
        private Object lock = new Object();
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(dataListAllItems == null){
                synchronized (lock){
                    dataListAllItems = new ArrayList<>(supertypeEntities);
                }
            }
            if(constraint == null|| constraint.length()==0){
                synchronized (lock){
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = constraint.toString().toLowerCase();
                ArrayList<SupertypeEntity> matches = new ArrayList<>();
                for(SupertypeEntity supertypeEntity: dataListAllItems){
                    if(supertypeEntity.getSupertype().toLowerCase().contains(searchStrLowerCase)){
                        matches.add(supertypeEntity);
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
                supertypeEntities = (ArrayList<SupertypeEntity>) results.values;
            } else {
                supertypeEntities = null;
            }
            if(results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
