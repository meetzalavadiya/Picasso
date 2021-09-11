package com.mynewacc.dreamteam11.cricket.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mynewacc.dreamteam11.cricket.Utils.SessionUtils;
import com.mynewacc.dreamteam11.R;

import java.util.List;

public class TabViewAdapter extends RecyclerView.Adapter<TabViewAdapter.MyViewHolder> {
    Activity activity;
    List<String> list;
    OnItemClickListener mItemClickListener;
    String tabValue;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return (long) i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_cricket;

        public MyViewHolder(View view) {
            super(view);
            TextView textView = (TextView) view.findViewById(R.id.tv_cricket);
            this.tv_cricket = textView;
            textView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (TabViewAdapter.this.mItemClickListener != null) {
                TabViewAdapter.this.mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public TabViewAdapter(Activity activity2, List<String> list2) {
        this.list = list2;
        this.activity = activity2;
        this.tabValue = SessionUtils.getInstance(activity2).getTabValue();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_view, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        String str = this.list.get(i);
        myViewHolder.tv_cricket.setText(str);
        if (SessionUtils.getInstance(this.activity).getTabValue().equalsIgnoreCase(str)) {
            myViewHolder.tv_cricket.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            myViewHolder.tv_cricket.setBackgroundResource(R.drawable.tab_bg_selected);
            myViewHolder.tv_cricket.setAlpha(0.8f);
            return;
        }
        myViewHolder.tv_cricket.setTextColor(activity.getResources().getColor(R.color.white));
        myViewHolder.tv_cricket.setBackgroundResource(R.drawable.tab_bg);
        myViewHolder.tv_cricket.setAlpha(0.6f);

    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }
}
