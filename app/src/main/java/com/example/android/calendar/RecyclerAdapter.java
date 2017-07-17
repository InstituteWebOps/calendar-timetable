package com.example.android.calendar;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by harshitha on 8/6/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
   List<String> day_dataset ,date_dataset ,desc_dataset;
    Context context;

    public RecyclerAdapter(List<String> list1, List<String> list2, List<String> list3,Context context){
        day_dataset=list1;
        date_dataset=list2;
        desc_dataset=list3;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_card, parent, false);

       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_day.setText((String)day_dataset.get(position));
        holder.tv_date.setText((String)date_dataset.get(position));
        holder.tv_desc.setText((String)desc_dataset.get(position));

        if(day_dataset.get(position).equals("Sun")  ||  day_dataset.get(position).equals("Sat"))
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ba68c8"));//violet color
        else
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(MainActivity.yearForRecyclerView,MainActivity.monthForRecyclerView,position+1);
                // A date-time specified in milliseconds since the epoch.
                long hr = beginTime.getTimeInMillis();

                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, hr);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date_dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_day, tv_date, tv_desc;
        CardView cardView;


        ViewHolder(View itemView) {
            super(itemView);
            tv_day=(TextView)itemView.findViewById(R.id.tv_day);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            tv_desc=(TextView)itemView.findViewById(R.id.tv_description);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
