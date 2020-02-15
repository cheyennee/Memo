package com.example.memo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Item> {
    private int resourceId;
    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = (Item)getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.date = view.findViewById(R.id.date);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.content.setText(item.getContent());
        viewHolder.date.setText(item.getDate());
        return view;
    }
    class ViewHolder{
        TextView content,date;
    }
}
