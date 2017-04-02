package com.blq.zzc.practice.adapter.XinWenAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.model.XinWenBean;
import com.blq.zzc.practice.ui.activity.XinWenDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class XinWenRecyclerViewAdapter extends RecyclerView.Adapter<XinWenRecyclerViewHolder>{
    private List<XinWenBean> list;
    private Activity activity;
    public XinWenRecyclerViewAdapter(Activity activity, List<XinWenBean> list){
        this.activity=activity;
        this.list=list;
    }
    @Override
    public XinWenRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XinWenRecyclerViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_xinwen,parent,false),activity);
    }

    @Override
    public void onBindViewHolder(final XinWenRecyclerViewHolder holder, final int position) {
        XinWenBean xinWenBean=list.get(position);
        holder.textView.setText(xinWenBean.getTitle());
        List<String> listImage=xinWenBean.getImagesUrl();
        if (listImage.size()>0){
            Glide.with(activity).load(listImage.get(0)).placeholder(R.drawable.account_avatar).into(
                    new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            holder.imageView.setImageDrawable(resource);
                        }
                    });
            System.out.println(position+":"+listImage.get(0));
        }
        else {
            holder.imageView.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(),R.drawable.account_avatar));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("按下");
                Intent intent=new Intent(activity,XinWenDetailActivity.class);
                intent.putExtra("detailHtml",list.get(position).getDetailHtml());
                if (list.get(position).getImagesUrl().size()>0)
                    intent.putExtra("image",list.get(position).getImagesUrl().get(0));
                intent.putExtra("title",list.get(position).getTitle());
                activity.startActivity(intent);
            }
        });

//            ((TextView)holder.itemView.findViewById(R.id.textView_another2)).setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
