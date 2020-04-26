package com.example.spark;

import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Event> mEventList;
    private OnEventItemListener mOnEventItemListener;

    public RecyclerAdapter(ArrayList<Event> eventList, OnEventItemListener onEventItemListener) {
        mEventList = eventList;
        mOnEventItemListener = onEventItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mOnEventItemListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event currentItem = mEventList.get(position);

        holder.mImageView.setImageResource(currentItem.getImages().get(0));
        holder.mNameTextView.setText(currentItem.getName());
        holder.mDescTextView.setText(currentItem.getDescription());
        GregorianCalendar date = currentItem.getDate();
        String dateString = date.get(Calendar.MONTH) + 1 + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR);
        holder.mDateTextView.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mNameTextView;
        public TextView mDescTextView;
        public TextView mDateTextView;
        OnEventItemListener onEventItemListener;

        public ViewHolder(@NonNull View itemView, OnEventItemListener onEventItemListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mNameTextView = itemView.findViewById(R.id.textView);
            mDescTextView = itemView.findViewById(R.id.textView2);
            mDateTextView = itemView.findViewById(R.id.textView3);
            this.onEventItemListener = onEventItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventItemListener.onEventItemClick(getAdapterPosition());
        }
    }

    public interface OnEventItemListener {
        void onEventItemClick(int position);
    }
}