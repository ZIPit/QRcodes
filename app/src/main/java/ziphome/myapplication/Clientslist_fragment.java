package ziphome.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        Log.d("listview_track","after"+listCldata.get(0).getMyfxtm_id());


        v = inflater.inflate(R.layout.clientslist_fragment, container, false);
        // Inflate the layout for this fragment
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        ClListAdapter recycleradapter = new ClListAdapter(getContext(),listCldata);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(new ClListAdapter(getContext(),listCldata));





        return v;


    }

}
