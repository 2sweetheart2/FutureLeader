package me.solo_team.futureleader.ui.menu.horizontal_menu.calendar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Calendar extends Her implements CalendarAdapter.OnItemListener, CalendarAdapter.onItemLongListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private List<Date> dates = new ArrayList<>();
    private LinearLayout list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Календарь");
        setContentView(R.layout.calendar_menu);
        initWidgets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
        }

        setMonthView();
        API.getEventsDate(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                System.out.println(json);
            }

            @Override
            public void inProcess() {
                d = openWaiter(Calendar.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                d.dismiss();
                JSONArray eventsDate = json.getJSONArray("dates");
                dates = new ArrayList<>();
                for (int i = 0; i < eventsDate.length(); i++) {
                    String[] date = eventsDate.getString(i).split("/");
                    int d1 = Integer.parseInt(date[0]);
                    int d2 = Integer.parseInt(date[1]);
                    int d3 = Integer.parseInt(date[2]);
                    dates.add(new Date(d1, d2, d3));
                }
                runOnUiThread(() -> setMonthView());

            }
        }, new CustomString("token", Constants.user.token));

    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        list = findViewById(R.id.event_list);
    }

    private List<Date> getEventsByDate() {
        List<Date> dates1 = new ArrayList<>();
        for (Date date : dates) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (selectedDate.getYear() == date.year && selectedDate.getMonthValue() == date.month)
                    dates1.add(date);
            }
        }
        return dates1;
    }


    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, getEventsByDate(), this, this, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        calendarAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = 0;

            dayOfWeek = firstOfMonth.getDayOfWeek().getValue();


        for (int i = 2; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }      }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        return "";
    }

    public void previousMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.minusMonths(1);
        }
        setMonthView();
    }

    public void nextMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.plusMonths(1);
        }
        setMonthView();
    }

    View oldDateView = null;
    boolean needReturn = true;
    Date currentDate = null;

    @Override
    public void onItemClick(int position, String dayText, View view) {
        if (!dayText.equals("")) {
            if (needReturn && oldDateView != null)
                oldDateView.setBackground(null);
            else if (!needReturn)
                oldDateView.setBackground(getDrawable(R.drawable.secondary_gradient_with_corners));
            oldDateView = view;
            needReturn = view.getBackground() == null;
            String message = "Выбрана дата " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            oldDateView.setBackground(getDrawable(R.drawable.gray_gradient_with_corners));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentDate = new Date(Integer.parseInt(dayText), selectedDate.getMonthValue(), selectedDate.getYear());
            }
        }
    }


    @Override
    public void onItemLongClick(int position, String dayText, View view) {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "")
                .setIcon(R.drawable.menu_white)
                .setOnMenuItemClickListener(item -> {
                    if (currentDate == null) return true;
                    if(needReturn) {
                        String message = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            message = "Событий " + selectedDate.getDayOfMonth() + ' ' + monthYearFromDate(selectedDate) + " нету";
                        }
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        return true;
                    }
                    Intent intent = new Intent(Calendar.this, EventCheck.class);
                    intent.putExtra("date", currentDate.toStr());
                    startActivity(intent);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        if (Constants.user.adminStatus != 0) {
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.plus)
                    .setTitle("хуй")
                    .setOnMenuItemClickListener(item -> {
                        if (currentDate == null) return true;
                        Intent intent = new Intent(this, AddEvent.class);
                        intent.putExtra("date", currentDate.toStr());
                        startActivityIfNeeded(intent,100);
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else menu.removeItem(1);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==-500)
            Utils.ShowSnackBar.show(Calendar.this,"отказано в доступе!",list);
    }
}
