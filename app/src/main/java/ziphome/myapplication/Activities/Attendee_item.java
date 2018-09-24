package ziphome.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import ziphome.myapplication.R;

public class Attendee_item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_item);

        DisplayMetrics dm = new DisplayMetrics();

        Button save_btn;

        String clid = getIntent().getStringExtra("clid");
        String clname = getIntent().getStringExtra("clName");
        String clType = getIntent().getStringExtra("clType");
        String clEmail= getIntent().getStringExtra("clEmail");


        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width  = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.8),(int) (height*0.4));

        TextView tv_clid = (TextView) findViewById(R.id.tv_clid);
        TextView tv_clname = (TextView) findViewById(R.id.tv_clname);
        TextView tv_clType = (TextView) findViewById(R.id.tv_cltype);
        TextView tv_clEmail = (TextView) findViewById(R.id.tv_clEmail) ;
        tv_clid.setText(clid);
        tv_clname.setText(clname);
        tv_clType.setText(clType);
        tv_clEmail.setText(clEmail);
    }
}
