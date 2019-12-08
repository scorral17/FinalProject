package com.example.finalproject;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Floor> floorsList = null;
    private ArrayList<Floor> arraylist;

    public ListViewAdapter(Context context, List<Floor> floorsList) {
        mContext = context;
        this.floorsList = floorsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Floor>();
        this.arraylist.addAll(floorsList);
    }

    public class ViewHolder {
        TextView floor;
        TextView capacity;
    }

    @Override
    public int getCount() {
        return floorsList.size();
    }

    @Override
    public Floor getItem(int position) {
        return floorsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.floor = (TextView) view.findViewById(R.id.floor);
            holder.capacity = (TextView) view.findViewById(R.id.capacity);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.capacity.setText(Integer.toString(floorsList.get(position).getCapacity()));
        holder.floor.setText(floorsList.get(position).getName());


        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        floorsList.clear();
        if (charText.length() == 0) {
            floorsList.addAll(arraylist);
        } else {
            for (Floor wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    floorsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
