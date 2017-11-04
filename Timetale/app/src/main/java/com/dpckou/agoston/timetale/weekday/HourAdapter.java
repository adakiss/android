package com.dpckou.agoston.timetale.weekday;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dpckou.agoston.timetale.R;

/**
 * Created by kissa on 2017. 11. 04..
 */

public class HourAdapter extends Adapter<HourAdapter.MyViewHolder> {

    private Hour[] hours;

    public HourAdapter(Hour[] hours) {
        this.hours = hours;

        //for(int i = 0; i < hours.length; i++) {
        //    hours[i] = new Hour(i++);
        //}
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_hour, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return hours.length;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Hour currentHour = hours[position];

        holder.hourTW.setText(currentHour.getHour().toString());
        if (currentHour.isEventPlanned()) {
            holder.eventTW.setText(currentHour.getEvent());
            holder.eventTW.setBackgroundColor(Color.GRAY);
        } else {
            holder.eventTW.setText("");
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hourTW;
        TextView eventTW;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.hourTW = itemView.findViewById(R.id.hour);
            this.eventTW = itemView.findViewById(R.id.event);
        }
    }
}
