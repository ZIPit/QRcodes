package ziphome.myapplication.other;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ziphome.myapplication.Activities.MainActivity;

public final class FragmentIntentIntegrator extends IntentIntegrator {

    private final Fragment fragment;

    public FragmentIntentIntegrator(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

//    @Override
//    protected void startActivityForResult(Intent intent,   int code) {
//            fragment.startActivityForResult(intent, code);
//        Log.d("check","Okay");
//    }

   // @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
     //  .startActivityForResult(intent, code);
        Log.d("check","Okay");
        if (scanResult != null) {
            // handle scan result
        }
    }
}