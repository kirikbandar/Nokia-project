package com.example.nokiafinal1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NokiaList extends ArrayAdapter<Nokia> {
    private Activity context;
    private List<Nokia> nokiaList;

    public NokiaList(Activity context, List<Nokia> nokiaList){
        super(context, R.layout.list_layout,nokiaList);
        this.context = context;
        this.nokiaList = nokiaList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewLat = (TextView) listViewItem.findViewById(R.id.textViewLat);
        TextView textViewLong = (TextView) listViewItem.findViewById(R.id.textViewLong);
        TextView textViewTag = (TextView) listViewItem.findViewById(R.id.textViewTag);

        Nokia nokia = nokiaList.get(position);
        textViewLat.setText(nokia.getNokiaLat());
        textViewLong.setText(nokia.getNokiaLong());
        textViewTag.setText(nokia.getNokiaTag());

        return listViewItem;
    }
}
