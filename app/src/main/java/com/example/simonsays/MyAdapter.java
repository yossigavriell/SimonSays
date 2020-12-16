package com.example.simonsays;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Map.Entry<String, Integer>> list;

    public MyAdapter(Context context, Map<String, Integer> map){
        this.context=context;
        list = new ArrayList<>(map.entrySet());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.recycler_view, null);
        TextView t1_rank = (TextView)convertView.findViewById(R.id.rank_lbl);
        TextView t2_score = (TextView)convertView.findViewById(R.id.score_lbl);
        TextView t3_name = (TextView)convertView.findViewById(R.id.name_lbl);

        Map.Entry<String, Integer> player = list.get(position);
        t1_rank.setText(String.valueOf(position+1));
        t2_score.setText(String.valueOf(player.getValue()));
        t3_name.setText(player.getKey());

        return convertView;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }
}