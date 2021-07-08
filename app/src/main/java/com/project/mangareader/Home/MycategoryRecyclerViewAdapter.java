package com.project.mangareader.Home;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mangareader.R;

import java.util.List;


public class MycategoryRecyclerViewAdapter extends RecyclerView.Adapter<MycategoryRecyclerViewAdapter.ViewHolder> {
    private Context context;
    List<String> mValues;


    public MycategoryRecyclerViewAdapter(Context context, List<String> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, GeneraItemListActivity.class);
                intent.putExtra("genera", holder.mItem);
                ((Activity)context).finish();
                context.startActivity(intent);

            }


        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mContentView;
        public String mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (TextView) view.findViewById(R.id.ganra_item_category);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}