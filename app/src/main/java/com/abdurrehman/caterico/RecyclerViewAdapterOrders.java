package com.abdurrehman.caterico;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterOrders extends RecyclerView.Adapter<RecyclerViewAdapterOrders.MyViewHolder> {

    private List<String> Order_Ids;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Order_Id;
        public TextView Order_Title;
        public ImageView Details;

        Context ctx;

        public MyViewHolder(View v, Context ct) {
            super(v);
            Order_Id = v.findViewById(R.id.order_id);
            Order_Title = v.findViewById(R.id.order_title);
            Details = v.findViewById(R.id.iv);
            ctx = ct;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapterOrders(List<String> myDataset) {
        Order_Ids = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v,parent.getContext());
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.Order_Id.setText(Order_Ids.get(position).split("-")[0]);
        holder.Order_Title.setText(Order_Ids.get(position).split("-")[1]);
        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(holder.ctx,OrderDetails.class);
                Order.putExtra("Order_Id",Order_Ids.get(position).split("-")[0]);
                Order.putExtra("Type",Order_Ids.get(position).split("-")[2]);
                Order.putExtra("Username",Order_Ids.get(position).split("-")[3]);
                holder.ctx.startActivity(Order);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Order_Ids.size();
    }
}
