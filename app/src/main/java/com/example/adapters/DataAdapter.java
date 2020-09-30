package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dxq.R;
import com.example.interfaces.RecyclerItemClickListener;
import com.example.models.Data;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private Context context;
    private List<Data> listData;
    private RecyclerItemClickListener itemClickListener;

    public DataAdapter(Context context, List<Data> listData, RecyclerItemClickListener itemClickListener) {
        this.context = context;
        this.listData = listData;
        this.itemClickListener = itemClickListener;
    }

//    @Override
//    public int getItemViewType(int position) {
//        Data item = listData.get(position);
//        if (item.getAge() > 25) {
//            return 1;
//        } else {
//            return 2;
//        }
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        return new MyViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data item = listData.get(position);
        holder.tvAge.setText(String.valueOf(item.getAge()));
        holder.tvName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvAge;
        private RecyclerItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView, RecyclerItemClickListener itemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(view, getAdapterPosition());
        }
    }
}
