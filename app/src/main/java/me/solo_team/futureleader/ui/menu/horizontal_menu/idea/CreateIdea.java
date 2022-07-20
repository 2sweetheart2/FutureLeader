package me.solo_team.futureleader.ui.menu.horizontal_menu.idea;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateIdea extends Her implements View.OnClickListener{

    EditText label;
    EditText text;
    View rootView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_idea_layout);
        setTitle("Предложить идею");

        label = findViewById(R.id.create_idea_label);
        text = findViewById(R.id.create_idea_text);
        rootView = findViewById(R.id.create_idea_layout);
        FloatingActionButton button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(label.getText().length()==0){
            Utils.ShowSnackBar.show(getApplicationContext(),"Заголовок не может быть пустым!",rootView);
            return;
        }
        else if(label.getText().length()>45){
            Utils.ShowSnackBar.show(getApplicationContext(),"заголовок не может превышать 45 сим.!",rootView);
            return;
        }
        if(text.getText().length()==0){
            Utils.ShowSnackBar.show(getApplicationContext(),"Текст не может быть пустым!",rootView);
            return;
        }

        //TODO: Отправить запрос
    }
}
