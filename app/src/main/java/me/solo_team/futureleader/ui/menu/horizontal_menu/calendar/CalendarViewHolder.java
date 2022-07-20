package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.solo_team.futureleader.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

    public final TextView dayOfMonth;
    public final View view;
    private final CalendarAdapter.OnItemListener onItemListener;
    private final CalendarAdapter.onItemLongListener onItemLongListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, CalendarAdapter.onItemLongListener onItemLongListener)
    {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        view = itemView;
        this.onItemListener = onItemListener;
        this.onItemLongListener = onItemLongListener;
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        onItemLongListener.onItemLongClick(getAdapterPosition(),(String) dayOfMonth.getText(),view);
        return true;
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText(),view);
    }
}
