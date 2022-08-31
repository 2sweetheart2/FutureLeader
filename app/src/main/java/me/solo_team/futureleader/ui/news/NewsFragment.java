package me.solo_team.futureleader.ui.news;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.News;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class NewsFragment extends Fragment {


    private LayoutInflater inflater;
    private ViewGroup container;
    private ListView nw;
    JSONArray news;
    SwipeRefreshLayout rt;
    MainActivity mainActivity;
    public NewsFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        nw = root.findViewById(R.id.news_list);
        nw.setOnScrollListener(scrollListener);
        nw.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        nw.setOverscrollHeader(requireContext().getDrawable(R.drawable.play));
        this.inflater = inflater;
        this.container = container;
        rt = (SwipeRefreshLayout) root;
        rt.setOnRefreshListener(() -> {
            System.out.println("REFRESH");
            rt.setRefreshing(true);
            recreate(true);
        });
        recreate(false);
        return root;
    }

    public boolean firstStart = true;


    CustomAdapter adapter;
    ArrayList<News> newsList = new ArrayList<>();

    public void createClick(boolean onlySet) throws JSONException {
        newsList.clear();
        try {

            for (int i = Constants.newsCache.news.length() - 1; i >= 0; i--) {
                JSONObject o = Constants.newsCache.news.getJSONObject(i);
                News news = new News(o);
                newsAddWitAnimation.add(news);
                newsList.add(news);
            }
            if (!onlySet) {
                adapter = new CustomAdapter(newsList, requireContext());
            }
            nw.setAdapter(adapter);

        }catch (Exception ignored){}

    }


    private boolean isEnd() {
        return preLast >= news.length();
    }
    View v;
    public View createHeader() {
        if(v==null)
            v = getLayoutInflater().inflate(R.layout.header, null);
        return v;
    }



    private int preLast;
    private boolean resume = false;

    boolean first_try = false;
    boolean fingerUp = false;

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState==0) {
//                if(nw.getFirstVisiblePosition()==0){
//                    if(!first_try && nw.getHeaderViewsCount()==0) {
//                        first_try = true;
//                        System.out.println("FIRST STEP");
//                        nw.addHeaderView(createHeader());
//                    }
//                    else if(nw.getHeaderViewsCount()==1){
//                        System.out.println("NEEEEEEEEEEEED UPDATE");
//                        nw.removeHeaderView(createHeader());
//                        v=null;
//                        first_try=false;
//                        recreate(true);
//                    }
//                    System.out.println("FINGER UP: "+fingerUp);
//                }
            }
            else fingerUp=true;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (view.getId() == R.id.news_list) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        preLast = lastItem;
                        if (isEnd())
                            Snackbar.make(nw, "Больше нет новостей", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        }
    };

    private void recreate(boolean force) {
        if(Constants.newsCache.news.length()==0 || force) {
            API.getNews(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) {
                    d.dismiss();
                    System.err.println(json.toString());
                }

                @Override
                public void inProcess() {
                    d = openWaiter(requireContext());
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    System.out.println(json.toString());
                    news = json.getJSONArray("news");
                    Constants.newsCache.news = news;
                    requireActivity().runOnUiThread(() -> {
                        try {
                            createClick(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(force)
                            rt.setRefreshing(false);
                    });
                    d.dismiss();

                }
            }, new CustomString("token", Constants.user.token));
        }
        resume = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (!firstStart)
                createClick(true);
            if(lastPosExit>=0)
                nw.setSelection(lastPosExit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            lastPosExit = adapter.lastPosition;
        }catch (Exception ignored){}
    }

    public int lastPosExit = -1;
    public List<News> newsAddWitAnimation = new ArrayList<>();
    public class CustomAdapter extends ArrayAdapter<News> {

        private ArrayList<News> dataSet;
        Context mContext;

        // View lookup cache
        private class ViewHolder {
            TextView txtName;
            TextView txtType;
            TextView txtVersion;
            ImageView info;
        }

        public CustomAdapter(ArrayList<News> data, Context context) {
            super(context, R.layout.news_news, data);
            this.dataSet = data;
            this.mContext = context;

        }


        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            News dataModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;
            ConstraintLayout cn;


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_news, parent, false);

            result = convertView;
            cn = result.findViewById(R.id.news_obj);
            Constants.cache.addPhoto(dataModel.photoUrl, (ImageView) cn.getChildAt(1), NewsFragment.this);
            convertView.setTag(viewHolder);


            if (newsAddWitAnimation.contains(dataModel)) {
                System.out.println("RESUME: " + resume);
                System.out.println("FIRST START: " + firstStart);

                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.left_ro_right);
                result.startAnimation(animation);
                newsAddWitAnimation.remove(dataModel);

            }

            lastPosition = position;


            ((TextView) cn.getChildAt(2)).setText(dataModel.title);
            switch (MainActivity.wightwindowSizeClass) {
                case COMPACT:
                    ((TextView) cn.getChildAt(2)).setTextSize(12f);
                    break;
                case MEDIUM:
                    ((TextView) cn.getChildAt(2)).setTextSize(15f);
                    break;
                case EXPANDED:
                    ((TextView) cn.getChildAt(2)).setTextSize(20);
                    break;
            }
            ((TextView) result.findViewById(R.id.news_view_countt)).setText(String.valueOf(dataModel.viewCount));
            ((TextView) result.findViewById(R.id.news_view_likess)).setText(String.valueOf(dataModel.likes));

            result.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
                intent.putExtra("tag", dataModel.title);
                intent.putExtra("id", dataModel.id);
                startActivity(intent);
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }


}