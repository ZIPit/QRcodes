package ziphome.myapplication;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Clientslist_fragment extends Fragment {

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

        DBHelper dbHelper;
        dbHelper = new DBHelper(getContext());
        ArrayList<ClientData> listCldata ;
        listCldata = new ArrayList<>();

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

            Log.d("listview_track", "after" + listCldata.get(0).getMyfxtm_id());


            v = inflater.inflate(R.layout.clientslist_fragment, container, false);

            myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            ClListAdapter recycleradapter = new ClListAdapter(getContext(), listCldata);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRecyclerView.setAdapter(new ClListAdapter(getContext(), listCldata));
        }




        return v;


    }

}
