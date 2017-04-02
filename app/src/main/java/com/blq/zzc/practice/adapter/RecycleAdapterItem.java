package com.blq.zzc.practice.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.model.DailyBean;
import com.blq.zzc.practice.utils.DateUtil;
import com.blq.zzc.practice.utils.WeekUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RecycleAdapterItem extends RecyclerView.Adapter<RecycleAdapterItem.ItemViewHolder>{
    private Context context;
    private List<DailyBean> list;
    public RecycleAdapterItem(Context context, List<DailyBean> list){
        this.context=context;
        this.list=list;
    }
    private static int ITEM_TIME=0;
    private static int ITEM=1;
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
        {
            return ITEM_TIME;
        }
        else if (list.get(position).getDate().equals(list.get(position-1).getDate())) return ITEM;
        else return ITEM_TIME;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_TIME)
        return new ItemTimeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_daily_list_time, parent, false));
        else return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_daily_list, parent, false));
    }
    void setDailyDate(ItemViewHolder holder, DailyBean dailyBean){
        holder.mTitle.setText(dailyBean.getTitle());
        List<String> images = dailyBean.getImages();
        if (images != null && images.size() > 0)
        {
            Glide.with(context).load(images.get(0)).placeholder(R.drawable.account_avatar).into(holder.mPic);
        }
        boolean multipic = dailyBean.isMultipic();
        if (multipic)
        {

            holder.mMorePic.setVisibility(View.VISIBLE);
        } else
        {
            holder.mMorePic.setVisibility(View.GONE);
        }
        if (!dailyBean.isRead())
        {
            holder.mTitle.setTextColor(ContextCompat.getColor(context, R.color.color_unread));
        } else
        {
            holder.mTitle.setTextColor(ContextCompat.getColor(context, R.color.color_read));
        }
    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
//        DailyBean dailyBean = list.get(position);
//        if (holder.getItemViewType()==ITEM_TIME){
        DailyBean dailyBean = list.get(position);
        if (dailyBean == null)
        {
            return;
        }

        if (holder instanceof ItemTimeViewHolder)
        {
            setDailyDate(holder, dailyBean);
            ItemTimeViewHolder itemTimeViewHolder = (ItemTimeViewHolder) holder;
            String timeStr = "";
            if (position == 0)
            {
                timeStr = "今日热闻";
            } else
            {
                timeStr = DateUtil.formatDate(dailyBean.getDate()) + "  " + WeekUtil.getWeek(dailyBean.getDate());
            }
            itemTimeViewHolder.textView_time.setText(timeStr);
        }
        else{
            setDailyDate(holder, dailyBean);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void updateData(List<DailyBean> dailys)
    {

        this.list = dailys;
        notifyDataSetChanged();
    }
    public void addData(List<DailyBean> dailys)
    {

        if (this.list == null)
        {
            updateData(dailys);
        } else
        {
            this.list.addAll(dailys);
            notifyDataSetChanged();
        }
    }
    public class ItemTimeViewHolder extends ItemViewHolder
    {
        @Bind(R.id.item_time)
        TextView textView_time;
        public ItemTimeViewHolder(View view)
        {
            super(view);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.card_view)
        CardView mLayout;
        @Bind(R.id.item_image)
        ImageView mPic;

        @Bind(R.id.item_title)
        TextView mTitle;

        @Bind(R.id.item_more_pic)
        ImageView mMorePic;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
