package com.blq.zzc.practice.adapter.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.ui.activity.ForumActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/26.
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.textView_another2)
    TextView textView;
    @OnClick(R.id.cardView_another2)void cardView_another2_OnClick(){
        activity.startActivity(new Intent(activity, ForumActivity.class));
    }
    private Context activity;
    public MyRecyclerViewHolder(View itemView, Context activity) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.activity=activity;
    }
}
