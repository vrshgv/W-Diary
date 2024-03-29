package com.losya.wdiarypersonal.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.losya.wdiarypersonal.R;
import com.losya.wdiarypersonal.R;
import com.losya.wdiarypersonal.bean.DiaryBean;
import com.losya.wdiarypersonal.event.StartUpdateDiaryEvent;
import com.losya.wdiarypersonal.utils.GetDate;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<DiaryBean> mDiaryBeanList;
    private int mEditPosition = -1;

    public DiaryAdapter(Context context, List<DiaryBean> mDiaryBeanList){
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDiaryBeanList = mDiaryBeanList;
    }
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryViewHolder(mLayoutInflater.inflate(R.layout.item_rv_diary, parent, false));
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, final int position) {



        String dateSystem = GetDate.getDate().toString();
        if(mDiaryBeanList.get(position).getDate().equals(dateSystem)){
            holder.mIvCircle.setImageResource(R.drawable.circle_orange);
        }

        holder.mTvDate.setText(mDiaryBeanList.get(position).getDate());
        holder.mTvTitle.setText(mDiaryBeanList.get(position).getTitle());
        holder.mTvContent.setText("" + mDiaryBeanList.get(position).getContent());
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String downloadType = SP.getString("downloadType","1");
        boolean themeType = (boolean) SP.getBoolean("btheme", Boolean.parseBoolean("false"));



        if (themeType) {
            holder.mTvDate.setBackgroundColor(Color.parseColor("#223344"));
            holder.mTvTitle.setBackgroundColor(Color.parseColor("#223344"));
            holder.mTvContent.setBackgroundColor(Color.parseColor("#223344"));
            holder.mLlControl.setBackgroundColor(Color.parseColor("#223344"));
            holder.mLlTitle.setBackgroundColor(Color.parseColor("#223344"));
            holder.mTvDate.setTextColor(Color.parseColor("#d3d3d3"));
            holder.mTvContent.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvTitle.setTextColor(Color.parseColor("#FFFFFF"));
            holder.view3.setBackgroundColor(Color.parseColor("#223344"));
            holder.view6.setBackgroundResource(R.drawable.ready);
            holder.viewR.setBackgroundColor(Color.parseColor("#223344"));
            holder.mLl.setBackgroundColor(Color.parseColor("#223344"));
            holder.mIvCircle.setBackgroundColor(Color.parseColor("#223344"));
            holder.need1.setBackgroundColor(Color.parseColor("#223344"));
            holder.need2.setBackgroundColor(Color.parseColor("#223344"));
            holder.mRlEdit.setBackgroundColor(Color.parseColor("#223344"));
        } else {
            holder.mTvDate.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTvTitle.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTvContent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTvContent.setTextColor(Color.parseColor("#223344"));
            holder.mTvTitle.setTextColor(Color.parseColor("#223344"));
            holder.mTvDate.setTextColor(Color.parseColor("#808080"));
            holder.view6.setBackgroundResource(R.drawable.linear_style);
            holder.mLlTitle.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.viewR.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.view3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mLl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mLlControl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mIvCircle.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.need1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.need2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mRlEdit.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        switch (downloadType){
            case "1":
                holder.mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
                holder.mTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                holder.mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                break;
            case "2":
                holder.mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
                holder.mTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                holder.mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                break;
            case "3":
                holder.mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,31);
                holder.mTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);
                holder.mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);

                break;
            default:
                holder.mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
                holder.mTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                holder.mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        }
        holder.mIvEdit.setVisibility(View.INVISIBLE);
        if(mEditPosition == position){
            holder.mIvEdit.setVisibility(View.VISIBLE);
        }else {
            holder.mIvEdit.setVisibility(View.GONE);
        }
        holder.mLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mIvEdit.getVisibility() == View.VISIBLE){
                    holder.mIvEdit.setVisibility(View.GONE);
                }else {
                    holder.mIvEdit.setVisibility(View.VISIBLE);
                }
                if(mEditPosition != position){
                    notifyItemChanged(mEditPosition);
                }
                mEditPosition = position;
            }
        });

        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartUpdateDiaryEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaryBeanList.size();
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView mTvDate;
        TextView mTvTitle;
        TextView mTvContent;
        ImageView mIvEdit;
        LinearLayout mLlTitle;
        LinearLayout mLl;
        ImageView mIvCircle;
        LinearLayout mLlControl;
        RelativeLayout mRlEdit;
        RelativeLayout viewR;
        LinearLayout view3;
        LinearLayout view6;
        LinearLayout need1;
        LinearLayout need2;
        DiaryViewHolder(View view){
            super(view);
            viewR = (RelativeLayout) view.findViewById(R.id.item_rl_edit);
            view6 = (LinearLayout) view.findViewById(R.id.itemLine);
            view3 = (LinearLayout) view.findViewById(R.id.numberPadLayout);
            mIvCircle = (ImageView) view.findViewById(R.id.main_iv_circle);
            mTvDate = (TextView) view.findViewById(R.id.main_tv_date);
            mTvTitle = (TextView) view.findViewById(R.id.main_tv_title);
            mTvContent = (TextView) view.findViewById(R.id.main_tv_content);
            mIvEdit = (ImageView) view.findViewById(R.id.main_iv_edit);
            mLlTitle = (LinearLayout) view.findViewById(R.id.main_ll_title);
            mLl = (LinearLayout) view.findViewById(R.id.item_ll);
            mLlControl = (LinearLayout) view.findViewById(R.id.item_ll_control);
            mRlEdit = (RelativeLayout) view.findViewById(R.id.item_rl_edit);
            need1 = (LinearLayout) view.findViewById(R.id.need1);
            need2 = (LinearLayout) view.findViewById(R.id.need2);

        }
    }
}