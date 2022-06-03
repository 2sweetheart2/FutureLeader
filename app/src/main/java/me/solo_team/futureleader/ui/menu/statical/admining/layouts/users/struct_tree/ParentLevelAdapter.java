package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.struct_tree;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.Objects.Structur;
import me.solo_team.futureleader.R;

public class ParentLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final List<Structur> mListDataHeader;
    private final Map<String, List<Structur>> mListData_SecondLevel_Map;
    private final Map<String, List<Structur>> mListData_ThirdLevel_Map;

    public ParentLevelAdapter(Context mContext, List<Structur> mListDataHeader) {
        this.mContext = mContext;
        this.mListDataHeader = new ArrayList<>();
        this.mListDataHeader.addAll(mListDataHeader);
        // Init second level data
        mListData_SecondLevel_Map = new HashMap<>();
        int parentCount = mListDataHeader.size();
        for (int i = 0; i < parentCount; i++) {
            mListData_SecondLevel_Map.put(mListDataHeader.get(i).name, mListDataHeader.get(i).chields);
        }
        // THIRD LEVEL
        List<Structur> mItemChildOfChild;
        List<String> listChild;
        mListData_ThirdLevel_Map = new HashMap<>();
        for (Map.Entry<String, List<Structur>> o : mListData_SecondLevel_Map.entrySet()) {
            List<Structur> object = o.getValue();
            if (object != null) {
                List<String> stringList = new ArrayList<>();
                for(Structur s :object){
                    stringList.add(s.name);
                }
                for (int i = 0; i < stringList.size(); i++) {
                    mItemChildOfChild = object.get(i).chields;
                    mListData_ThirdLevel_Map.put(stringList.get(i), mItemChildOfChild);
                }
            }
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
        String parentNode = getGroup(groupPosition).name;
        secondLevelExpListView.setAdapter(new SecondLevelAdapter(this.mContext, mListData_SecondLevel_Map.get(parentNode), mListData_ThirdLevel_Map));
        secondLevelExpListView.setGroupIndicator(null);
        return secondLevelExpListView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Structur getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).name;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_view, parent, false);
        }
        TextView lblListHeader = convertView
                .findViewById(R.id.textGroup);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextColor(Color.CYAN);
        lblListHeader.setText(headerTitle);
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
