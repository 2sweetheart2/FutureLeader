package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final List<Date> dates;
    private final Context context;
    private final onItemLongListener onItemLongListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, List<Date> dates, OnItemListener onItemListener, Context context, CalendarAdapter.onItemLongListener onItemLongListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.dates = dates;
        this.context = context;
        this.onItemLongListener = onItemLongListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener, onItemLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        for(Date date : dates){
            if(String.valueOf(date.day).equals(daysOfMonth.get(position))){
                holder.view.setBackground(context.getDrawable(R.drawable.secondary_gradient_with_corners));
            }
        }
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText, View view);
    }

    public interface onItemLongListener{
        void onItemLongClick(int position, String dayText, View view);
    }
}
