package ziphome.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClListAdapter extends RecyclerView.Adapter<ClListAdapter.MyViewHolder> {


    public ClListAdapter(Context mContext, ArrayList<ClientData> mlistCL) {
        this.mContext = mContext;
        this.mlistCL = mlistCL;
        Log.d("adptr","Constructor " + mlistCL.get(0).getClName());
    }

    Context mContext;
    List<ClientData> mlistCL;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        Log.d("adptr","onCreateViewHolder ");
        v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("adptr","onBindViewHolder ");
        holder.tv_myfxtmid.setText(mlistCL.get(position).getMyfxtm_id());
        holder.tv_cl_name.setText(mlistCL.get(position).getClName());
        holder.tv_email.setText(mlistCL.get(position).geteMail());
//        holder.tv_Event_id.setText(mlistCL.get(position).getSeminarID());

    }

    @Override
    public int getItemCount() {
        return mlistCL.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_myfxtmid;
        private TextView tv_cl_name;
        private TextView tv_email;
        private TextView tv_Event_id;


        public MyViewHolder(View itemView) {

            super(itemView);
            Log.d("adptr","MyViewHolder ");
            tv_myfxtmid = (TextView) itemView.findViewById(R.id.tvMyfxtmid);
            tv_cl_name = (TextView) itemView.findViewById(R.id.tvClName);
            tv_email = (TextView) itemView.findViewById(R.id.tvEmail);
//            tv_Event_id=(TextView) itemView.findViewById(R.id.tvEventID);

        }
    }


}