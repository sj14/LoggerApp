package org.hsrw.mobilecomputing.loggerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.activities.LogActivity;
import org.hsrw.mobilecomputing.loggerapp.activities.RecordActivity;

/**
 * Created by simon on 28.04.16.
 * Maps an LogElement to the listView
 */
public class LogElementAdapter extends ArrayAdapter<LogElement> {

    Context context;
    int layoutResourceId;
    LogElement data[] = null;

    public LogElementAdapter(Context context, int layoutResourceId, LogElement[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LogElementHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LogElementHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDescription = (TextView)row.findViewById(R.id.txtDescription);

            row.setTag(holder);
        }
        else
        {
            holder = (LogElementHolder)row.getTag();
        }

        LogElement myListElement = data[position];
        holder.txtTitle.setText(myListElement.getName());
        holder.txtDescription.setText(myListElement.getDate().toString());
        //holder.imgIcon.setImageResource(myListElement.icon);

        return row;
    }

    static class LogElementHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDescription;
    }


}

