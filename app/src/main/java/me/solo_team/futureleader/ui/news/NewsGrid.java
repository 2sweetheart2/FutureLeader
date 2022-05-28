package me.solo_team.futureleader.ui.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.R;

public class NewsGrid {

    private final TableLayout table;
    private final LayoutInflater inflater;
    private final ViewGroup container;
    public NewsGrid(TableLayout table, LayoutInflater inflater, ViewGroup container){
        this.table = table;
        this.inflater = inflater;
        this.container = container;
    }

    public void addElement(String uri,String name){
        TableRow row = (TableRow)inflater.inflate(R.layout.news_news, container,false);
        ConstraintLayout cn = (ConstraintLayout)row.getChildAt(0);
        Picasso.get().load(uri).into((ImageView)cn.getChildAt(1));
        ((TextView)cn.getChildAt(2)).setText(name);
        switch (MainActivity.wightwindowSizeClass){
            case COMPACT:
                ((TextView)cn.getChildAt(2)).setTextSize(12f);
                break;
            case MEDIUM:
                ((TextView)cn.getChildAt(2)).setTextSize(15f);
                break;
            case EXPANDED:
                ((TextView)cn.getChildAt(2)).setTextSize(20);
                break;
        }
        table.addView(row);
    }
}
