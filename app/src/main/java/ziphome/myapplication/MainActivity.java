package ziphome.myapplication;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

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
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                    DBHelper dbHelper;
                    dbHelper = new DBHelper(this);

                    if (dbHelper.addData(mas,"0")) {
                     //   Log.d("AddMas","222" );
                        Log.d("Insert", "Inserted Successfully");
//                        imageView.setImageResource(R.drawable.okimg);
//                        StatusTV.setText("has been added successfully");

                    }
                    else {Log.d("Insert", "Insert Failed");
//                        imageView.setImageResource(R.drawable.failure);
//                        StatusTV.setText("Oops :( Scan failed");
                    }





                } catch (JSONException e) { e.printStackTrace(); }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            Toast.makeText(this,"event_id",Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.attenders) {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.title_clients_list);

            Clientslist_fragment clientsListFragment = new Clientslist_fragment();
            FragmentManager manager = MainActivity.this.getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                    clientsListFragment).commit();
            Log.d("jalo","onNavigationItemSelected ");

            Toast.makeText(this,"Attenders",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.insert_client) {
            Intent intent = new Intent(MainActivity.this, Man_ClInsert_activity.class);
            startActivity(intent);

            Toast.makeText(this,"Insert Client",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Tools toolsFragment = new Tools();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                    toolsFragment,
                    toolsFragment.getTag()).commit();


            Toast.makeText(this,"nav_manage",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"nav_send",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
