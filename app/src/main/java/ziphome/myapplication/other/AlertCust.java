package ziphome.myapplication.other;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ziphome.myapplication.Activities.MainActivity;

public class AlertCust {
    public void Show (final String  title, final String message, Context context) {

        AlertDialog.Builder dial = new AlertDialog.Builder(context);
        dial.setTitle(title);
        dial.setMessage(message);


        dial.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog al = dial.create();
        al.show();
    }
        public void Show (final String type, Context context ) {
            String title="";
            String message="";

            AlertDialog.Builder dial = new AlertDialog.Builder(context);
            if (type == "event_empty")
            {
                title = "Event ID is empty";
                message = "Please set-up Event ID";
            }
            else if (type == "wrong_pwd")
            {
                title = "Oops..";
                message = "Wrong password. Sorry :(";
            }
            if (!title.equals("")) {
                dial.setTitle(title);
                dial.setMessage(message);


            dial.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        AlertDialog al = dial.create();
        al.show();
            }
    };







}
