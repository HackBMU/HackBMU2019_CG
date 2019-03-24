package com.example.sunejas.sihproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.Models.EventDetails;
import com.example.sunejas.sihproject.PatientTrackpage;
import com.example.sunejas.sihproject.R;
import com.example.sunejas.sihproject.SingleEventDoctorActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHoder> {
    List<EventDetails> list;
    Context context;
    SharedPreferences pref;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public PostAdapter(List<EventDetails> list, Context context) {
        this.list = list;
        this.context = context;
        pref = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_doctor_post,parent,false);
        MyHoder myHoder = new MyHoder(view);

        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, final int position) {
        final EventDetails mylist = list.get(position);
        final String type = pref.getString("role", null);
        holder.closeUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=mylist.getUserkey();
                Intent intent;
                if(type.equals("patient")) {
                    intent = new Intent(context, PatientTrackpage.class);
                } else {
                    intent = new Intent(context, SingleEventDoctorActivity.class);
                    intent.putExtra("userid", id);
                }
                context.startActivity(intent);
            }
        });


        Long date=mylist.getDate();
        if (list.get(position).getAssignedDoc() != 0) {
            holder.assignedTo.setText("Assigned to: " + String.valueOf(list.get(position).getAssignedDoc()));
        } else {
            holder.assignedTo.setText("Assigned to: None");
        }
        holder.name.setText(list.get(position).getName());
        Toast.makeText(context, list.get(position).getCloseupImage(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(list.get(position).getCloseupImage()).into(holder.closeUpImage);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(date);
        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView name,date,assignedTo;
        ImageView closeUpImage;


        public MyHoder(View itemView) {
            super(itemView);
            closeUpImage=itemView.findViewById(R.id.post_image);
            name = (TextView) itemView.findViewById(R.id.vname);
            date= (TextView) itemView.findViewById(R.id.post_date);
            assignedTo= (TextView) itemView.findViewById(R.id.assigned_doctor);

        }
    }
}
