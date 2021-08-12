package com.btcsc.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class SpinnerAdapter extends ArrayAdapter<ListPlanning> {
    public SpinnerAdapter(@NonNull Context context, @NonNull List<ListPlanning> objects) {
        super(context, 0, objects);
    }



    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.layout_list,parent,false);

        ListPlanning data = getItem(position);

        TextView textView = convertView.findViewById(R.id.txtListAdapter);

        textView.setText(data.name);



        return convertView;
    }

}
