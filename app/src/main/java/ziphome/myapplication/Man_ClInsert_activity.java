package ziphome.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import ziphome.myapplication.Activities.MainActivity;
import ziphome.myapplication.Fragments.Clientslist_fragment;
import ziphome.myapplication.other.AlertCust;

public class Man_ClInsert_activity extends AppCompatActivity {
    EditText clientidET ;
    Button submitBtn;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__cl_insert);
        clientidET = (EditText) findViewById(R.id.clientID_ET);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        dbHelper = new DBHelper(this);
        View.OnClickListener submitBTN = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("checkman","0: "+clientidET.getText().length());
                if (clientidET.getText().length()>0){
                    String clientid= clientidET.getText().toString();
                    String date;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                    date = format.format(new Date());
                    CheckBox checkBox = findViewById(R.id.checkBox);
                    String checkbox_val = "client";
                    if (checkBox.isChecked()) {
                        checkbox_val = "partner";
                    }
                    String str = dbHelper.getEvent();
                   if (str==null || str.equals(""))
                    {

                        AlertCust alertCust = new AlertCust();
                        Log.d("manemptydata","manemptydata");
                        alertCust.Show("event_empty", Man_ClInsert_activity.this);
                    }
                    else {
                       String[] mas = new String[]{
                               clientid,
                               "Client " + clientid,
                               "EMail is unknown",
                               dbHelper.getEvent(),
                               checkbox_val,
                               date

                       };
                       Log.d("checkman", "1: ");
                       dbHelper.addData(mas, "true");

                       //dbHelper.manualInput(dbHelper.getEvent(),clientidET.getText().toString());
                       //Toast.makeText(Man_ClInsert_activity.this,date, Toast.LENGTH_LONG).show();

                       finish();
                   }
                }
            }
        };
        submitBtn.setOnClickListener(submitBTN);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
