package ziphome.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                    String[] mas = new String[]{
                            clientid,
                            "Client " + clientid,
                            "EMail is unknown" ,
                            dbHelper.getEvent()

                    };
                    Log.d("checkman", "1: ");
                    dbHelper.addData(mas,"1");
                    //dbHelper.manualInput(dbHelper.getEvent(),clientidET.getText().toString());

                    finish();
                }
            }
        };
        submitBtn.setOnClickListener(submitBTN);


    }
}
