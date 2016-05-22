package org.hsrw.mobilecomputing.loggerapp.logusage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;

/**
 * Created by simon on 28.04.16.
 * Maps an LogCallElement to the listView
 */
public class LogUsageAdapter extends ArrayAdapter<LogUsageElement> {

    Context context;
    int layoutResourceId;
    LogUsageElement data[] = null;

    public LogUsageAdapter(Context context, int layoutResourceId, LogUsageElement[] data) {
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
            //holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtDescription = (TextView)row.findViewById(R.id.txtDescription);

            row.setTag(holder);
        }
        else
        {
            holder = (LogElementHolder)row.getTag();
        }

        LogUsageElement myListElement = data[position];
        holder.txtTitle.setText(myListElement.getPackageName());
        holder.txtDescription.setText(myListElement.getLastTimeUsed().toString());
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

