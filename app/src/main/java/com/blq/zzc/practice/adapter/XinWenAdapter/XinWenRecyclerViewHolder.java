package com.blq.zzc.practice.adapter.XinWenAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.ui.activity.ForumActivity;
import com.blq.zzc.practice.ui.activity.XinWenDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/26.
 */
public class XinWenRecyclerViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.xinwen_title)
    TextView textView;
    @Bind(R.id.xinwen_imageview)
    ImageView imageView;
    @Bind(R.id.XinWen_CardView)
    CardView cardView;
    private Context activity;
    public XinWenRecyclerViewHolder(View itemView, Context activity) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.activity=activity;
    }
}
