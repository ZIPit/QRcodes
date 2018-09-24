package ziphome.myapplication.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ziphome.myapplication.Activities.MainActivity;
import ziphome.myapplication.ClListAdapter;
import ziphome.myapplication.ClientData;
import ziphome.myapplication.DBHelper;
import ziphome.myapplication.MyAttendeeInterface;
import ziphome.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Clientslist_fragment extends Fragment  {

    View v;
    private RecyclerView myRecyclerView;

    private ArrayList<ClientData> listCldata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//
//        for(int i=1;i<20;i++){
//            ClientData client = new ClientData("Client"+i);
//            listCldata.add(client);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.clientslist_fragment, container, false);
        DBHelper dbHelper;
        dbHelper = new DBHelper(getContext());
        ArrayList<ClientData> listCldata ;
        listCldata = new ArrayList<>();
        TextView tvEventid = (TextView) v.findViewById(R.id.tvEventid);
        tvEventid.setText(dbHelper.getEvent());

        Log.d("listview_track","before");
        listCldata= dbHelper.clientlistview();

        if  ( listCldata.isEmpty())
        {Log.d("track_null", "null list");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.list_empty_t);
            builder.setMessage(R.string.list_empty_bd);
            builder.setCancelable(false);
            builder.setNegativeButton(R.string.list_empty_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();


        }
        else {
//            FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(Clientslist_fragment.this);
//            integrator.setBeepEnabled(false);
//            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//            integrator.setPrompt("Let's Go!");
//            integrator.initiateScan();

            Log.d("listview_track", "after" + listCldata.get(0).getMyfxtm_id());




            myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
           // ClListAdapter recycleradapter = new ClListAdapter(getContext(), listCldata);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRecyclerView.setAdapter(new ClListAdapter(getContext(), listCldata));
        }



        return v;


    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper dbHelper;
        dbHelper = new DBHelper(getContext());
        listCldata= dbHelper.clientlistview();
       // if (!listCldata.isEmpty()&&this.myRecyclerView!=null) {
        if (!listCldata.isEmpty()&&this.myRecyclerView!=null) {
            this.myRecyclerView.setAdapter(new ClListAdapter(getContext(), listCldata));
        }
        else if (!listCldata.isEmpty()&&this.myRecyclerView==null){
            //this.myRecyclerView.setAdapter(new ClListAdapter(getContext(), listCldata));

            Clientslist_fragment clientsListFragment = new Clientslist_fragment();
            FragmentManager manager = this.getFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayoutforfragment,
                    clientsListFragment,"clfrag").commit();

           // recycleradapter.refreshClList();

        }



    }


}
