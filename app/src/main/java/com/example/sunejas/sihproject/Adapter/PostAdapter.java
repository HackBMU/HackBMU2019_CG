package com.example.sunejas.sihproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.Models.EventDetails;
import com.example.sunejas.sihproject.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHoder> {
    List<EventDetails> list;
    Context context;

    public PostAdapter(List<EventDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_doctor_post,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        EventDetails mylist = list.get(position);
        Long date=mylist.getDate();
        Toast.makeText(context, "pahoch gye", Toast.LENGTH_SHORT).show();
        Log.d("logger", "pahoch gye");
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(date);
        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;

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
