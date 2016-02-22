package org.cgspine.rxjavademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;
import org.cgspine.rxjavademo.model.Gank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgspine on 16/1/15.
 */
public class GankAdapter extends BaseAdapter {
    private List<Gank> mGankList = new ArrayList<>();
    private Context mContext;

    public GankAdapter(Context context,List<Gank> ganks){
        mContext = context;
        if(ganks!=null){
            mGankList.addAll(ganks);
        }
    }

    public void refresh(List<Gank> ganks){
        mGankList.clear();
        if(ganks!=null){
            mGankList.addAll(ganks);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mGankList.size();
    }

    @Override
    public Gank getItem(int position) {
        return mGankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gank_list_item,null,false);
            holder = new ViewHolder();
            holder.whoTv = (TextView) convertView.findViewById(R.id.who);
            holder.publishAtTv = (TextView) convertView.findViewById(R.id.publish_at);
            holder.descTv = (TextView) convertView.findViewById(R.id.desc);
            holder.urlTv = (TextView) convertView.findViewById(R.id.url);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Gank gank = getItem(position);
        holder.whoTv.setText(gank.getWho());
        holder.publishAtTv.setText(gank.getPublishedAt());
        holder.descTv.setText(gank.getDesc());
        holder.urlTv.setText(gank.getUrl());
        return convertView;
    }

    class ViewHolder{
        TextView whoTv;
        TextView publishAtTv;
        TextView descTv;
        TextView urlTv;
    }
}
