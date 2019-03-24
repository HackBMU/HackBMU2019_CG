package com.example.sunejas.sihproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunejas.sihproject.Models.DoctorDetails;
import com.example.sunejas.sihproject.R;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyHoder> {
    ArrayList<DoctorDetails> list;
    Context context;

    public DoctorAdapter(ArrayList<DoctorDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_featured_doctor, parent, false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        Log.d("prerna","coding all night");
        holder.name.setText(list.get(position).getmName());
        holder.specialization.setText(list.get(position).getmSpecialization());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHoder extends RecyclerView.ViewHolder {
        TextView name, specialization;

        public MyHoder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.doctor_name);
            specialization = (TextView) itemView.findViewById(R.id.doctor_specialization);
        }
    }
}
