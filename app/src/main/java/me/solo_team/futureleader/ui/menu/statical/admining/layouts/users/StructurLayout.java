package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Structur;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.struct_tree.CustomExpListView;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.users.struct_tree.ParentLevelAdapter;

public class StructurLayout extends Her {

    ExpandableListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structure_layout);

        listView = findViewById(R.id.expListView);

        //Создаем набор данных для адаптера
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        ArrayList<String> children1 = new ArrayList<>();
        ArrayList<String> children2 = new ArrayList<>();
        children1.add("Child_1");
        children1.add("Child_2");
        groups.add(children1);
        children2.add("Child_1");
        children2.add("Child_2");
        children2.add("Child_3");
        groups.add(children2);
        API.getStructure(new ApiListener() {
            @Override
            public void onError(JSONObject json) {

            }

            @Override
            public void inProcess() {

            }

            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray structure = json.getJSONArray("structure");
                    List<Structur> structurList = new ArrayList<>();
                    for (int i = 0; i < structure.length(); i++) {
                        JSONObject o = structure.getJSONObject(i);
                        int user_id;
                        if (o.isNull("user_id")) user_id=-1;
                        else user_id=o.getInt("user_id");
                        Structur structur = new Structur(o.getString("name"), o.getInt("id"),user_id);
                        if (o.getJSONArray("chields").length() != 0) {
                            for(int j=0;j<o.getJSONArray("chields").length();j++){
                                JSONObject o1 = o.getJSONArray("chields").getJSONObject(j);
                                int user_id1;
                                if (o1.isNull("user_id")) user_id1=-1;
                                else user_id1=o1.getInt("user_id");
                                Structur structur1 = new Structur(o1.getString("name"),o1.getInt("id"),user_id1);
                                if(o1.getJSONArray("chields").length()!=0) {
                                    for (int k = 0; k < o1.getJSONArray("chields").length(); k++) {
                                        JSONObject o2 = o1.getJSONArray("chields").getJSONObject(k);
                                        int user_id2;
                                        if (o1.isNull("user_id")) user_id2 = -1;
                                        else user_id2 = o2.getInt("user_id");
                                        Structur structur2 = new Structur(o2.getString("name"), o2.getInt("id"), user_id2);
                                        structur1.addChiled(structur2);
                                    }
                                }
                                structur.addChiled(structur1);
                            }
                        }
                        structurList.add(structur);
                    }
                    runOnUiThread(()->{
                        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), structurList,StructurLayout.this);
                        //ParentLevelAdapter adapter = new ParentLevelAdapter(StructurLayout.this, structurList);
                        listView.setAdapter(adapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new CustomString("token", Constants.user.token));
        //Создаем адаптер и передаем context и список с данными

    }


    public class ExpListAdapter extends BaseExpandableListAdapter {

        private List<Structur> mGroups;
        private Context mContext;
        private AppCompatActivity activity;

        public ExpListAdapter(Context context, List<Structur> groups, AppCompatActivity appCompatActivity) {
            mContext = context;
            mGroups = groups;
            activity = appCompatActivity;
        }

        @Override
        public int getGroupCount() {
            return mGroups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mGroups.get(groupPosition).chields.size();
        }

        @Override
        public Structur getGroup(int groupPosition) {
            return mGroups.get(groupPosition);
        }

        @Override
        public Structur getChild(int groupPosition, int childPosition) {
            return mGroups.get(groupPosition).chields.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.group_view, null);
            }

            if (isExpanded) {
                //Изменяем что-нибудь, если текущая Group раскрыта
            } else {
                //Изменяем что-нибудь, если текущая Group скрыта
            }

            TextView textGroup = convertView.findViewById(R.id.textGroup);
            textGroup.setText(getGroup(groupPosition).name);

            ImageView plus_btn = convertView.findViewById(R.id.group_view_plus_btn);

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.chield_layout, null);
            }

            if(mGroups.get(groupPosition).hasChield()) {
                CustomExpListView secondLevelExpListView = new CustomExpListView(mContext);
                secondLevelExpListView.setLayoutParams(listView.getLayoutParams());
                secondLevelExpListView.setDivider(listView.getDivider());
                expListSecondLevelv expListSecondLevel = new expListSecondLevelv(mContext,mGroups.get(groupPosition).chields);
                secondLevelExpListView.setAdapter(expListSecondLevel);
                return secondLevelExpListView;
            }

            TextView textChild = convertView.findViewById(R.id.textChild);
            textChild.setText(mGroups.get(groupPosition).chields.get(childPosition).name);

            ImageView button = convertView.findViewById(R.id.chield_opt_btn);
            button.setOnClickListener(view -> Toast.makeText(mContext, "button is pressed", 5000).show());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class expListSecondLevelv extends BaseExpandableListAdapter{

        Context context;
        List<Structur> structurs;

        public expListSecondLevelv(Context mContext, List<Structur> structurs) {
            this.context = mContext;
            this.structurs = structurs;
            System.out.println("CHIELD HAS: "+structurs.size());

        }

        @Override
        public int getGroupCount() {
            return structurs.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return structurs.get(groupPosition).chields.size();
        }

        @Override
        public Structur getGroup(int groupPosition) {
            return structurs.get(groupPosition);
        }

        @Override
        public Structur getChild(int groupPosition, int childPosition) {
            return structurs.get(groupPosition).chields.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.group_view, parent, false);
            }
            System.out.println("SIZE: "+getGroupCount());
            Structur o = getGroup(groupPosition);
            TextView textView = convertView.findViewById(R.id.textGroup);

            textView.setText(getGroup(groupPosition).name);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.chield_layout, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.textChild);

            textView.setText(getChild(groupPosition,childPosition).name);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
}
