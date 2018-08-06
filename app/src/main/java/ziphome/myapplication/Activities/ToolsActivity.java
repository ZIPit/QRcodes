package ziphome.myapplication.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ziphome.myapplication.DBHelper;
import ziphome.myapplication.R;

public class ToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        Button btn = findViewById(R.id.btn_remove);
        final DBHelper dbHelper;
        dbHelper = new DBHelper(this);



        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbHelper.clear_clientsData();
            }


        };
        btn.setOnClickListener(clickListener);
    }
}
