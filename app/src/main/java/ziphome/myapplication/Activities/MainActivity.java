package ziphome.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import ziphome.myapplication.Fragments.Clientslist_fragment;
import ziphome.myapplication.DBHelper;
import ziphome.myapplication.Man_ClInsert_activity;
import ziphome.myapplication.R;
import ziphome.myapplication.api.RetrofitClient;
import ziphome.myapplication.models.AuthResponse;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setBeepEnabled(false);
                qrScan.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                qrScan.setPrompt("Let's Go!");
                qrScan.initiateScan();
                //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews


                    String[] mas = new String[]{
                            obj.getString("myfxtm_id"),
                            obj.getString("name"),
                            obj.getString("email"),
                            obj.getString("seminar_id")
                    };

                    Log.d("AddMas",mas[0]+" "+mas[1]+" "+mas[2] );
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                    DBHelper dbHelper;
                    dbHelper = new DBHelper(this);
                    String clName;
                    final char dm = (char) 34;
                    int res;
                    res=0;
                    Log.d("AddMas","starting3.." );
                    res = dbHelper.valiadate(mas);
                    if (res==0) {
                        clName = dbHelper.addData(mas, "0");
                        Log.d("Insert", "222 " + clName + "");
                        if (!clName.equals("")) {
                            //   Log.d("AddMas","222" );
                            Log.d("Insert", dm + clName + dm + " has been added Successfully");
                            //Toasty.success(this,"Client "+clName+" has been added Successfully",Toast.LENGTH_LONG).show();

                            LayoutInflater inflater = getLayoutInflater();

                            View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.showCustom));
                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            TextView text = toastLayout.findViewById(R.id.tv_cust);
                            text.setText(clName + " has been joined to Seminar ");
                            toast.setView(toastLayout);

                            toast.show();


                            //                        imageView.setImageResource(R.drawable.okimg);
//                        StatusTV.setText("has been added successfully");

                        } else {
                            Log.d("Insert", "Insert Failed");
                            Toasty.error(this, "Insert Failed", Toast.LENGTH_SHORT).show();
//
                        }
                    }
                    else
                        {
                            Log.d("validate","is "+ res);
                            switch (res) {
                                case 1 :
                                    Toasty.error(this, "Failed. Client ID number is invalid", Toast.LENGTH_LONG).show();
                                    break;
                                case 2 :
                                    Toasty.error(this, "Failed. Client ID number is not a number", Toast.LENGTH_LONG).show();
                                case 3 :
                                    Toasty.error(this, "Failed. Seminar ID from QR code is not Matched with Current Seminar ID", Toast.LENGTH_LONG).show();
                                    break;
                                case 4 :
                                    Toasty.error(this, "Failed. Client with MyFXTM ID "+mas[0]+" is alredy joined to Seminar",Toast.LENGTH_LONG).show();
                                    break;
                                case 5 :
                                    Toasty.error(this, "Failed. Seminar ID was not chosen in Mobile App. Please set it up",Toast.LENGTH_LONG).show();
                                    break;

                            }
                        }




                } catch (JSONException e) {
                    Log.d("Insert","333");
                    Toasty.error(this,"Insert Failed. QR code has Invalid structure",Toast.LENGTH_SHORT).show();

                    e.printStackTrace(); }
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
        if (id == R.id.action_settings) {
            return true;
        }

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
                    clientsListFragment).commit();
            Log.d("jalo","onNavigationItemSelected ");

          //  Toast.makeText(this,"Attenders",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.insert_client) {
            Intent intent = new Intent(MainActivity.this, Man_ClInsert_activity.class);
            startActivity(intent);

  //          Toast.makeText(this,"Insert Client",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
//            Tools toolsFragment = new Tools();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
//                    toolsFragment,
//                    toolsFragment.getTag()).commit();
            Intent intent = new Intent(MainActivity.this, ToolsActivity.class);
            startActivity(intent);


            Toast.makeText(this,"nav_manage",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.login_layout,null);
            final EditText login = (EditText) mView.findViewById(R.id.et_login);
            final EditText password = (EditText) mView.findViewById(R.id.et_password);
            Button blogin = (Button) mView.findViewById(R.id.btn_login);
            login.setText("manager");
            password.setText("Pass1234");

            blogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!login.getText().toString().isEmpty()&&!password.getText().toString().isEmpty())
                    {
                        String loginStr, passwordStr;
                        loginStr= login.getText().toString();
                        passwordStr = password.getText().toString();

                        Toast.makeText(MainActivity.this,"All OK to proceed", Toast.LENGTH_SHORT).show();

                        Call<AuthResponse> call = RetrofitClient.getInstance().getApi().authresp(loginStr, passwordStr);


                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "PLease fill login & password fields",Toast.LENGTH_LONG).show();
                    }
                }
            });

            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
