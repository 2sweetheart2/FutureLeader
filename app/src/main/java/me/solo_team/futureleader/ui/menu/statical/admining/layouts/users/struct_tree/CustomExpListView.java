package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.struct_tree;

import android.content.Context;
import android.widget.ExpandableListView;

public class CustomExpListView extends ExpandableListView
{
    public CustomExpListView(Context context)
    {
        super(context);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}