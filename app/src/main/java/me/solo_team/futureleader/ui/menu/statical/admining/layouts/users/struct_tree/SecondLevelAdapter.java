package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.struct_tree;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.solo_team.futureleader.Objects.Structur;
import me.solo_team.futureleader.R;

public class SecondLevelAdapter extends BaseExpandableListAdapter
{
    private final Context mContext;
    private final List<Structur> mListDataHeader;
    private final Map<String, List<Structur>> mListDataChild;
    public SecondLevelAdapter(Context mContext, List<Structur> mListDataHeader, Map<String, List<Structur>> mListDataChild) {
        this.mContext = mContext;
        this.mListDataHeader = mListDataHeader;
        this.mListDataChild = mListDataChild;
    }
    @Override
    public Structur getChild(int groupPosition, int childPosition)
    {
        return Objects.requireNonNull(this.mListDataChild.get(mListDataHeader.get(groupPosition).name))
                .get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        final Structur child =  getChild(groupPosition, childPosition);
        final String childText = child.name;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.chield_layout, parent, false);
        }
        TextView txtListChild = convertView
                .findViewById(R.id.textChild);
        txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        txtListChild.setText(childText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition)
    {
        try {
            return Objects.requireNonNull(this.mListDataChild.get(this.mListDataHeader.get(groupPosition).name)).size();
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public Structur getGroup(int groupPosition)
    {
        return this.mListDataHeader.get(groupPosition);
    }
    @Override
    public int getGroupCount()
    {
        return this.mListDataHeader.size();
    }
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent)
    {
        String headerTitle = getGroup(groupPosition).name;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_view, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textGroup);
        lblListHeader.setText(headerTitle);
        lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        lblListHeader.setTextColor(Color.YELLOW);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}