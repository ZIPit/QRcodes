package ziphome.myapplication.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import ziphome.myapplication.DBHelper;
import ziphome.myapplication.R;

public class ToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        final Button btn = findViewById(R.id.btn_remove);
        final DBHelper dbHelper;
        dbHelper = new DBHelper(this);

        final Button btn2 = findViewById(R.id.btn_remevid);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.clear_eventid();
                btn2.setEnabled(false);
            }
        });


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbHelper.clear_clientsData();
               btn.setEnabled(false);
            }


        };
        btn.setOnClickListener(clickListener);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
