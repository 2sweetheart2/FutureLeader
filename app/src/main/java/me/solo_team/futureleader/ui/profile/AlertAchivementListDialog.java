package me.solo_team.futureleader.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Achievement;
import me.solo_team.futureleader.R;

public class AlertAchivementListDialog extends AppCompatDialogFragment {


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_alert_dialog_list, null);
        builder.setView(view);
        RecyclerView recyclerView = view.findViewById(R.id.achivement_list);
        List<Achievement> achs = new ArrayList<>();
        try {
            JSONArray ach = Constants.user.achievements;
            for (int i = 0; i < ach.length(); i++) {
                JSONObject a = ach.getJSONObject(i);
                Achievement achievement = new Achievement(a,false);
                achs.add(achievement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(requireContext(), achs, requireParentFragment());
        adapter.setClickListener((view1, position) -> {
            AlertAchivementDialog alertAchivementDialog = new AlertAchivementDialog(achs.get(position));
            alertAchivementDialog.show(getParentFragmentManager(), null);
        });
        recyclerView.setAdapter(adapter);


        return builder.create();
    }

    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<Achievement> achievements;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;
        private Fragment fragmentActivity;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<Achievement> data, Fragment fragmentActivity) {
            this.mInflater = LayoutInflater.from(context);
            this.achievements = data;
            this.fragmentActivity = fragmentActivity;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.recycle_item, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String image = achievements.get(position).image_url;
            String name = achievements.get(position).name;
            Constants.cache.addPhoto(image, holder.image, fragmentActivity);
            holder.name.setText(name);
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return achievements.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView name;
            ImageView image;

            ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.achivement_list_image);
                name = itemView.findViewById(R.id.achivement_list_name);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        Achievement getItem(int id) {
            return achievements.get(id);
        }

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }
}
