package com.example.autoserviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.autoserviceapp.R;
import com.example.autoserviceapp.model.Vehicle;

import java.util.List;

public class VehicleListAdapter extends ArrayAdapter<Vehicle> {
    private LayoutInflater inflater;
    private int layout;
    private List<Vehicle> vehicleList;

    public VehicleListAdapter(Context context, int resource, List<Vehicle> vehicles) {
        super(context, resource, vehicles);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.vehicleList = vehicles;
    }
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final VehicleListAdapter.ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new VehicleListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (VehicleListAdapter.ViewHolder) convertView.getTag();
        }
        final Vehicle vehicle = vehicleList.get(position);

        viewHolder.nameMark.setText(vehicle.getMark());
        viewHolder.nameModel.setText(vehicle.getModel());

        return convertView;
    }
    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }

    private class ViewHolder {
        TextView nameMark;
        TextView nameModel;
        ViewHolder(View view){
            nameMark = view.findViewById(R.id.text1);
            nameModel = view.findViewById(R.id.text2);
        }
    }
}
