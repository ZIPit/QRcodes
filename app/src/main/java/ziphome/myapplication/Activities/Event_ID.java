package ziphome.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import ziphome.myapplication.DBHelper;
import ziphome.myapplication.R;

public class Event_ID extends AppCompatActivity {
    EditText seminarID;
    String seminarValue;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("event_track","pre");
        setContentView(R.layout.activity_event__id);
        Log.d("event_track","pre2");
        //getSupportActionBar().hide();
        //getSupportActionBar().setTitle(R.string.title_event_id);
        Log.d("event_track","pre3");
        DisplayMetrics dm = new DisplayMetrics();

        Button save_btn;




        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width  = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.8),(int) (height*0.4));

        save_btn=(Button) findViewById(R.id.popupSave_btn);
        seminarID= (EditText) findViewById(R.id.event_edit);
        dbHelper = new DBHelper(this);

        seminarID.setText(dbHelper.getEvent());

        View.OnClickListener saveListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seminarValue = seminarID.getText().toString();
                Log.d("EventIDtrack", "Event ID data " + seminarID.getText().length());
                if (seminarID.getText().length()>0){

                    if (dbHelper.setEvent(seminarValue))
                    {
                        Log.d("EventIDtrack","Updated Successfully");
                        finish();
                    }
                    else {
                        Log.d("EventIDtrack","Updated Failed");
                    }
                };

            }
        };
        save_btn.setOnClickListener(saveListener);

    }


}
