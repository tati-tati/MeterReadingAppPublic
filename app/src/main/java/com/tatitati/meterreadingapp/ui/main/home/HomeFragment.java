package com.tatitati.meterreadingapp.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tatitati.meterreadingapp.R;

public class HomeFragment extends Fragment{
    private View view;
    private HomeFragmentModel homeFragmentModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        homeFragmentModel = new HomeFragmentModel(getContext());

        ListView historyList = view.findViewById(R.id.history_list);

        historyList.setAdapter(homeFragmentModel.getAdapter());

        return view;
    }

}
