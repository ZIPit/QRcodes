package ziphome.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ziphome.myapplication.Fragments.Clientslist_fragment;
import ziphome.myapplication.DBHelper;
import ziphome.myapplication.Fragments.Home_fragment;
import ziphome.myapplication.Man_ClInsert_activity;
import ziphome.myapplication.R;
import ziphome.myapplication.api.Api;
import ziphome.myapplication.api.RetrofitClient;
import ziphome.myapplication.api.UnsafeOkHttpClient;
import ziphome.myapplication.models.Attendee;
import ziphome.myapplication.models.AttendeeResp;
import ziphome.myapplication.models.AuthResponse;
import ziphome.myapplication.other.AlertCust;
import ziphome.myapplication.other.FragmentIntentIntegrator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_step1 = (TextView) findViewById(R.id.tv_step1);
        tv_step1.setLinksClickable(true);
        tv_step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Event_ID.class);
                startActivity(intent);
            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



       final DBHelper dbHelper;
        dbHelper = new DBHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = dbHelper.getEvent();
                if (str==null || str.equals("")) {

                    AlertCust alertCust = new AlertCust();
                    Log.d("manemptydata","manemptydata");
                    alertCust.Show("event_empty", MainActivity.this);
                }
                else {

                    IntentIntegrator qrScan = new IntentIntegrator(MainActivity.this);

                    qrScan.setBeepEnabled(false);
                    qrScan.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    qrScan.setPrompt("Let's Go!");
                    qrScan.initiateScan();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headview = navigationView.getHeaderView(0);
        ImageView imageProfile = headview.findViewById(R.id.profileImg);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_fragment home_fragment = new Home_fragment();
                FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                        home_fragment).commit();
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();



            }
        });


    }



    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
     //   IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        IntentResult result = FragmentIntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            //if qrcode has nothing in it
            //if qr contains data
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else try {
                String date;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                date = format.format(new Date());
                //converting the data to json
                JSONObject obj = new JSONObject(result.getContents());
                //setting values to textviews


                String[] mas = new String[]{
                        obj.getString("external_id"),
                        obj.getString("name"),
                        obj.getString("email"),
                        obj.getString("seminar_id"),
                        "client",
                        date

                };

                Log.d("AddMas", mas[0] + " " + mas[1] + " " + mas[2]+ " " + mas[3]+ " " + mas[4]+ " " + mas[5]);
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                DBHelper dbHelper;
                dbHelper = new DBHelper(this);
                String clName;
                final char dm = (char) 34;
                int res;
                res = 0;
                Log.d("AddMas", "starting3..");
                res = dbHelper.valiadate(mas);
                if (res == 0) {
                    clName = dbHelper.addData(mas, "0");
                    Log.d("Insert", "222 " + clName + "");
                    if (!clName.equals("")) {
                        //   Log.d("AddMas","222" );
                        Log.d("Insert", dm + clName + dm + " has been added Successfully");




                        LayoutInflater inflater = getLayoutInflater();

                        View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.showCustom));
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        TextView text = toastLayout.findViewById(R.id.tv_cust);
                        text.setText(clName + " has been joined to Seminar ");
                        toast.setView(toastLayout);

                        toast.show();

                    } else {
                        Log.d("Insert", "Insert Failed");
                        Toasty.error(this, "Insert Failed", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.d("validate", "is " + res);
                    switch (res) {
                        case 1:
                            Toasty.error(this, "Failed. Client ID number is invalid", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toasty.error(this, "Failed. Client ID number is not a number", Toast.LENGTH_LONG).show();
                        case 3:
                            Toasty.error(this, "Failed. Seminar ID from QR code is not Matched with Current Seminar ID", Toast.LENGTH_LONG).show();
                            break;
                        case 4:
                            Toasty.error(this, "Failed. Client with MyFXTM ID " + mas[0] + " is already joined to Seminar", Toast.LENGTH_LONG).show();
                            break;
                        case 5:
                            Toasty.error(this, "Failed. Seminar ID was not chosen in Mobile App. Please set it up", Toast.LENGTH_LONG).show();
                            break;

                    }
                }


            } catch (JSONException e) {
                Log.d("Insert", "333");
                Toasty.error(this, "Insert Failed. QR code has Invalid structure", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        } else {
            Log.d("Insert","444");
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Toast.makeText(this,"onBackPressed",Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START);


        } else {
            //Toast.makeText(this,"onBackPressed2",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Exit");
            alert.setMessage("Are you sure you want to quit?");

            alert.setPositiveButton("Exit", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          //  super.onBackPressed();
                            finish();

                        }
                    }

            );
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();;
                }
            });
            AlertDialog al = alert.create();
            al.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
         Toolbar toolbar;
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.event_id) {


            Intent intent = new Intent(MainActivity.this, Event_ID.class);
            startActivity(intent);
//            Toast.makeText(this,"event_id",Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.attenders) {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.title_clients_list);

            Clientslist_fragment clientsListFragment = new Clientslist_fragment();
            FragmentManager manager = MainActivity.this.getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                    clientsListFragment,"clfrag").commit();
            Log.d("jalo","onNavigationItemSelected ");

          //  Toast.makeText(this,"Attenders",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.insert_client) {
            Intent intent = new Intent(MainActivity.this, Man_ClInsert_activity.class);
            startActivity(intent);

  //          Toast.makeText(this,"Insert Client",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MainActivity.this, ToolsActivity.class);
            startActivity(intent);


          //  Toast.makeText(this,"nav_manage",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.login_layout,null);
            final EditText mlogin = (EditText) mView.findViewById(R.id.et_login);
            final EditText mpassword = (EditText) mView.findViewById(R.id.et_password);

            final DBHelper dbHelper = new DBHelper(this);

            final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);


            Button blogin = (Button) mView.findViewById(R.id.btn_login);
            mlogin.setText("manager");
            mpassword.setText("lf9FzJOL");
          //  mpassword.setText("Pass1234");

            builder.setView(mView);
            final AlertDialog dialog = builder.create();

            blogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!mlogin.getText().toString().isEmpty()&&!mpassword.getText().toString().isEmpty())
                    {

                        Log.d("retro","before request");
                        String login, password;
                        login= mlogin.getText().toString();
                        password = mpassword.getText().toString();
                        Log.d("retro","before request 1.2");

                        String jsonAuth;
                        jsonAuth = "{\n" +
                                "\t\"login\":\""+login+"\",\n" +
                                "\t\"password\":\""+password+"\"\n" +
                                "}";




                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonAuth);

                        Call<AuthResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .authresp(requestBody);
                        call.enqueue(new Callback<AuthResponse>() {
                            @Override
                            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                if (response.code()==200) {

                                    progressBar.setProgress(50);

                                    String token = null ;
                                    token ="Bearer "+ response.body().getAccess_token();

                                   // Toast.makeText(MainActivity.this,"Token : "+token , Toast.LENGTH_SHORT).show();
                                    Log.d("Tracking", "Token : "+ token);
                                    ArrayList<Attendee> attendeeList = new ArrayList<Attendee>() ;

                                    attendeeList = dbHelper.ClData4EP();
                                    if (attendeeList.isEmpty()){
                                        String altitle = getString(R.string.emptydata_msg_title);
                                        String msg = getString(R.string.emptydata_msg);
                                        AlertCust alertCust = new AlertCust();
                                        alertCust.Show(altitle, msg, MainActivity.this);
                                        dialog.dismiss();
                                    }
                                    else SendNetworkRequest(token, attendeeList);
                                }
                                else Toast.makeText(MainActivity.this, "Error " + response.code(), Toast.LENGTH_SHORT).show();


                            }
                            @Override
                            public void onFailure(Call<AuthResponse> call, Throwable throwable) {
                                Toast.makeText(MainActivity.this,"Error with Access to Server",Toast.LENGTH_SHORT).show();
                            }

    private void SendNetworkRequest(final String token, final ArrayList<Attendee> attendeeList){

        //OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        OkHttpClient.Builder okhttpbuilder = okHttpClient.newBuilder();


        okhttpbuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder newRequest =  request.newBuilder().header("Authorization",token);
                newRequest = newRequest.addHeader("Content-Type","application/json");
                return chain.proceed(newRequest.build());
            }
        });


        Retrofit.Builder builder = new Retrofit.Builder()
               // .baseUrl("http://seminars.go.fxtm.com/v1/")
                .baseUrl("https://seminars.trunk.fxtm/v1/")
                .client(okhttpbuilder.build())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();




        Api client = null;
        client = retrofit.create(Api.class);
        Call<ArrayList<AttendeeResp>> call = client.sendAtt(attendeeList);

        call.enqueue(new Callback<ArrayList<AttendeeResp>>() {
                         @Override
                         public void onResponse(Call<ArrayList<AttendeeResp>> call, Response<ArrayList<AttendeeResp>> response) {
                             String msg="";
                             String altitle="";
                             if (response.code() ==200||response.code()==201){
                                 ArrayList<AttendeeResp> attendees;

                                 int rtrue_cnt=0;
                                 int rfalse_cnt=0;

                                 attendees = response.body();
                                 for (AttendeeResp h: attendees ) {
                                 if (h.getIs_attended()) rtrue_cnt++;
                                 else rfalse_cnt++;
                                 }
                                 msg= "All data has been sent to Server. ";
                                 msg = msg + " "+rtrue_cnt+" of "+(rfalse_cnt+rtrue_cnt)+" clients were marked as attended";
                                 altitle = "Good job!";
                                 progressBar.setProgress(100);

                                 Home_fragment home_fragment = new Home_fragment();
                                 FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                                 manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                                         home_fragment).commit();
                                 dbHelper.clear_eventid();
                                 dbHelper.clear_clientsData();
                                 }
                                 else
                                     {
                                 altitle = "Connection error";
                                 msg = msg + " Data hasn't been sent. Error "+ response.code();
                                 progressBar.setProgress(100);
                                     }

                             dialog.dismiss();

                             AlertCust alertCust = new AlertCust();
                             alertCust.Show(altitle, msg, MainActivity.this);

                         }

                         @Override
                         public void onFailure(Call<ArrayList<AttendeeResp>> call, Throwable throwable) {
                             Toast.makeText(MainActivity.this,"Something went wrong." , Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                         }
        });

    }

});



                    }

                }


            });


            dialog.show();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
