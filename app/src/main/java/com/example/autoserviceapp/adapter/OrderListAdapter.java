package com.example.autoserviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.autoserviceapp.R;
import com.example.autoserviceapp.model.Order;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private LayoutInflater inflater;
    private int layout;
    private List<Order> orderList;

    public OrderListAdapter(Context context, int resource, List<Order> orders) {
        super(context, resource, orders);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.orderList = orders;
    }
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final OrderListAdapter.ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new OrderListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (OrderListAdapter.ViewHolder) convertView.getTag();
        }
        final Order order = orderList.get(position);

        viewHolder.nameWork.setText(order.getWork());
        viewHolder.nameStatus.setText(order.getStatus());

        return convertView;
    }
    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }

    private class ViewHolder {
        TextView nameWork;
        TextView nameStatus;
        ViewHolder(View view){
            nameWork = view.findViewById(R.id.workView);
            nameStatus = view.findViewById(R.id.statusView);
        }
    }
}
