package com.example.synerzip.quiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Snehal Tembare on 22/7/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class CustomAdapter extends ArrayAdapter{
    private Context mContext;
    private List<String> subjectNames;


    public CustomAdapter(Context context, int list_item, List<String> mSubjects) {
        super(context,list_item);
        mContext=context;
        subjectNames=mSubjects;
    }
    @Override
    public int getCount() {
        return subjectNames.size();
    }
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        RelativeLayout layout=null;
        ViewHolder holder=new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_item,parent,false);
            convertView.setTag(holder);
        }else {
            layout=(RelativeLayout) convertView;
        }

        String name=subjectNames.get(position);

        holder.TITLE=(TextView)convertView.findViewById(R.id.idItemName);
        holder.TICK=(ImageView) convertView.findViewById(R.id.image_tick);


        holder.TITLE.setText(name);

        return convertView;
    }

    //View Holder for listView's row layout
    public static class ViewHolder{
        public TextView TITLE;
        public ImageView TICK;

    }
}
