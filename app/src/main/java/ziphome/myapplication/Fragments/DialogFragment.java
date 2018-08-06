package ziphome.myapplication.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.zip.Inflater;

import ziphome.myapplication.DBHelper;
import ziphome.myapplication.R;

public class DialogFragment extends android.app.DialogFragment {
    DBHelper dbHelper;
    EditText et_eventid;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



     //   et_eventid = inflater.findViewById(R.id.et_eventid);
        //dbHelper = new DBHelper(getActivity());


        //et_eventid.setText(dbHelper.getEvent());
        et_eventid.setText("test");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.eventid, null))
                // Add action buttons
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();


    }





}
