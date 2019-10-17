package com.tatitati.meterreadingapp.ui.main.meters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterType;
import com.tatitati.meterreadingapp.ui.main.meterDetails.MeterDetailsEditFragment;
import com.tatitati.meterreadingapp.ui.main.meterDetails.MeterDetailsItemFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetersListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Map<String, String>> groupList;
    private ArrayList<ArrayList<Map<String, String>>> childList;
    private Context context;
    private FragmentManager fragmentManager;
    private Map<String, MeterType> meterTypeMap;
    private Map<String, Meter> meterMap;

    public MetersListAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meter_group, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);

        View btn_meter_group = convertView.findViewById(R.id.btn_meter_group);
        btn_meter_group.setFocusable(false);
        btn_meter_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeterDetailsEditFragment fragment = (MeterDetailsEditFragment) fragmentManager.findFragmentByTag("MeterDetailsEditFragment");
                if (fragment == null) {
                }
                Map<String, String> map = (HashMap) getGroup(groupPosition);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new MeterDetailsEditFragment(meterTypeMap.get(map.get("meterType")), true),"MeterDetailsEditFragment")
                            .commit();
            }
        });

        Map<String, String> map = (HashMap) getGroup(groupPosition);
        textGroup.setText(map.get("meterType"));

        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meter_group_child, null);
        }

        Map<String, String> map = (HashMap) getChild(groupPosition, childPosition);
        Meter meter = meterMap.get(map.get("meterNumber"));

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        ImageButton btn_open_meter_details = convertView.findViewById(R.id.btn_open_meter_details);
        textChild.setText(("Номер счетчика: " + meter.getMeterNumber()));

        btn_open_meter_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeterDetailsItemFragment fragment = (MeterDetailsItemFragment) fragmentManager.findFragmentByTag("MeterDetailsItemFragment");
                if (fragment == null) {
                }
                Map<String, String> map = (HashMap) getChild(groupPosition, childPosition);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MeterDetailsItemFragment(meterMap.get(map.get("meterNumber"))),"MeterDetailsItemFragment")
                        .commit();
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void setGroupList(ArrayList<Map<String, String>> groupList) {
        this.groupList = groupList;
    }

    public void setChildList(ArrayList<ArrayList<Map<String, String>>> childList) {
        this.childList = childList;
    }

    public void setMeterTypeMap(Map<String, MeterType> map){
        this.meterTypeMap = map;
    }

    public void setMeterMap(Map<String, Meter> map){
        this.meterMap = map;
    }

}
