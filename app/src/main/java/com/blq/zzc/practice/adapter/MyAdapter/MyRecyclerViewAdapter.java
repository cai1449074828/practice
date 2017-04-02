package com.blq.zzc.practice.adapter.MyAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blq.zzc.practice.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder>{
    private List<String> list;
    private Activity activity;
    public MyRecyclerViewAdapter(Activity activity, List<String> list){
        this.activity=activity;
        this.list=list;
    }
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecyclerViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_another2,parent,false),activity);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
//            ((TextView)holder.itemView.findViewById(R.id.textView_another2)).setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
