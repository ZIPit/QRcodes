package ziphome.myapplication.Fragments;


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

import ziphome.myapplication.ClListAdapter;
import ziphome.myapplication.ClientData;
import ziphome.myapplication.DBHelper;
import ziphome.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {

    View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {





          //  v = inflater.inflate(R.id.relativeLayoutforfragment, container, false);
        v = inflater.inflate(R.layout.home_fragment, container, false);


        return v;


    }

}
