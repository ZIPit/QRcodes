package ziphome.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ziphome.myapplication.Activities.Attendee_item;
import ziphome.myapplication.Fragments.Clientslist_fragment;

public class ClListAdapter extends RecyclerView.Adapter<ClListAdapter.MyViewHolder> {


    public ClListAdapter(Context mContext, ArrayList<ClientData> mlistCL) {
        this.mContext = mContext;
        this.mlistCL = mlistCL;
       if (!mlistCL.isEmpty()) {
            Log.d("adptr", "Constructor " + mlistCL.get(0).getClName());
        }
        else Log.d("adptr","Constructor2 "  );
    }

    public void refreshClList() {

        this.mlistCL.clear();
        this.mlistCL.addAll(mlistCL);
        notifyDataSetChanged();
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
//        holder.tv_email.setText(mlistCL.get(position).geteMail());
        holder.tv_rowid.setText(mlistCL.get(position).getIdIndex());
        holder.tv_ClType.setText(mlistCL.get(position).getExtType());
//        holder.tv_Event_id.setText(mlistCL.get(position).getSeminarID());
        holder.setClickListener(new MyAttendeeInterface() {
        @Override
        public void onClick(View v, int pos, boolean b) {
      //  Toast.makeText(mContext, "#" + pos + " - " + mlistCL.get(pos).clName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, Attendee_item.class);
            intent.putExtra("clid",mlistCL.get(pos).getMyfxtm_id());
            intent.putExtra("clName", mlistCL.get(pos).getClName());
            intent.putExtra("clType", mlistCL.get(pos).getExtType());
            intent.putExtra("clEmail",mlistCL.get(pos).geteMail());
            mContext.startActivity(intent);


    }
});


    }



    @Override
    public int getItemCount() {
        return mlistCL.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    implements  View.OnClickListener{

        private TextView tv_myfxtmid;
        private TextView tv_cl_name;
        private TextView tv_email;
        private TextView tv_Event_id;
        private TextView tv_rowid;
        private TextView tv_ClType;
        private MyAttendeeInterface clickListener;

        public MyViewHolder(View itemView) {

            super(itemView);
            Log.d("adptr","MyViewHolder ");
            tv_myfxtmid = (TextView) itemView.findViewById(R.id.tvMyfxtmid);
            tv_cl_name = (TextView) itemView.findViewById(R.id.tvClName);
         //   tv_email = (TextView) itemView.findViewById(R.id.tvEmail);
            tv_rowid = (TextView) itemView.findViewById(R.id.tvRowid);
            tv_ClType = (TextView) itemView.findViewById(R.id.tvClType);

            itemView.setOnClickListener(this);


//            tv_Event_id=(TextView) itemView.findViewById(R.id.tvEventID);

//            LinearLayout ll;
//
//                    ll = (LinearLayout) itemView.findViewById(R.id.list_item);
//                    ll.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ((Clientslist_fragment) ).userItemClick(getAdapterPosition());
//                        }
                    }
        public void setClickListener(MyAttendeeInterface myAttendeeInterface) {
            this.clickListener=myAttendeeInterface;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        };

        }
    }

