package com.example.autoserviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.autoserviceapp.R;
import com.example.autoserviceapp.model.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private int layout;
    private List<User> userList;

    public UserListAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.userList = users;
    }
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        viewHolder.nameView.setText(user.getUsername());
        viewHolder.IdView.setText(Long.toString(user.getId()));

        return convertView;
    }
    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }

    private class ViewHolder {
         TextView nameView;
         TextView IdView;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.nameView);
            IdView = view.findViewById(R.id.IdView);
        }
    }
}
