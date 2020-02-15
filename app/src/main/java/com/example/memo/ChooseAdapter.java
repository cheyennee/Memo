package com.example.memo;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChooseAdapter extends ArrayAdapter<ChooseItem> {
    private int resourceId;
    public ChooseAdapter(@NonNull Context context, int resource, @NonNull List<ChooseItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChooseItem item = getItem(position);
        View view;
        ViewHolder holder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            holder = new ViewHolder();
            holder.content = view.findViewById(R.id.content);
            holder.date = view.findViewById(R.id.date);
            holder.state = view.findViewById(R.id.checkbox);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.content.setText(item.getContent());
        holder.date.setText(item.getDate());
        holder.state.setChecked((item.getState() == 0) ? false : true);
        /*holder.state.setChecked(checkStates.valueAt(position));
        holder.state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStates.setValueAt(position,isChecked);
            }
        });*/
        return view;
    }
    class ViewHolder{
        TextView content,date;
        CheckBox state;
    }
}
