package com.example.alpha01;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BT_ListAdapter_Device extends ArrayAdapter<BT_Device> {
    Activity activity;
    int layoutResourceID;
    ArrayList<BT_Device> devices;

    public BT_ListAdapter_Device (Activity activity, int resource, ArrayList<BT_Device> objects) {
        super (activity.getApplicationContext(), resource, objects);

        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        if (convertView == null){
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }
        return convertView;
    }
}