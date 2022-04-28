package me.solo_team.futureleader.ui.profile;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;

import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.R;

public class ProfileInfoGrid {

    private final TableLayout table;
    private final LayoutInflater inflater;
    private final ViewGroup container;
    public ProfileInfoGrid(TableLayout table, Context context, LayoutInflater inflater,ViewGroup container){
        this.table = table;
        this.inflater = inflater;
        this.container = container;
    }

    public void addElement(String name,String value){
        TableRow row = (TableRow)inflater.inflate(R.layout.row_in_profile_info, container,false);
        ((TextView)row.getChildAt(0)).setText(name);
        ((TextView)row.getChildAt(1)).setText(value);
        switch (MainActivity.wightwindowSizeClass){
            case COMPACT:
                ((TextView)row.getChildAt(1)).setTextSize(12f);
                ((TextView)row.getChildAt(0)).setTextSize(12f);
                break;
            case MEDIUM:
                ((TextView)row.getChildAt(1)).setTextSize(15f);
                ((TextView)row.getChildAt(0)).setTextSize(15f);
            case EXPANDED:
                ((TextView)row.getChildAt(1)).setTextSize(20);
                ((TextView)row.getChildAt(0)).setTextSize(20);
        }
        table.addView(row);
    }

    public void addRow(String name){
        View view = inflater.inflate(R.layout.single_gray_line_withtext_contact,container,false);
        ((TextView)view.findViewById(R.id.text_with_line)).setText(name);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 2;
        params.setMarginStart(50);
        params.setMarginEnd(50);
        table.addView(view,params);
    }

    public void addRow(){
        View view = inflater.inflate(R.layout.single_gray_line,container,false);
        table.addView(view);
    }
}
