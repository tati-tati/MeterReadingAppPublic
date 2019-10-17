package com.tatitati.meterreadingapp.ui.main.meters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tatitati.meterreadingapp.R;

public class MetersFragment extends Fragment {
    private View view;
    private MetersFragmentModel metersFragmentModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meters, container, false);
        metersFragmentModel = new MetersFragmentModel();

        MetersListAdapter adapter = metersFragmentModel.getArrayAdapter(getContext(), getFragmentManager());

        ExpandableListView expandableListView = view.findViewById(R.id.list_of_meters_type);
        expandableListView.setAdapter(adapter);

        return view;
    }
}
