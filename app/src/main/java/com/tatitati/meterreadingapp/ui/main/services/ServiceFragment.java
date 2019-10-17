package com.tatitati.meterreadingapp.ui.main.services;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.ui.main.IOnBackPressed;

public class ServiceFragment extends Fragment implements IOnBackPressed {
    private Button addService;
    private AlertDialog.Builder ad;
    private View view;
    private ServiceFragmentModel serviceFragmentModel;
    private ListView services;
    private FragmentManager fragmentManager;
    private AddServiceFragment addServiceFragment;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_services1, container, false);
        serviceFragmentModel = new ServiceFragmentModel(view.getContext());
        fragmentManager = getFragmentManager();
        addService = view.findViewById(R.id.btn_add_new_service);
        addServiceFragment = new AddServiceFragment();

        services = view.findViewById(R.id.services_list);

        services.setAdapter(serviceFragmentModel.getServiceListAdapter());

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.container, addServiceFragment, "AddServiceFragment").commit();
            }
        });
        return view;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

//    void showPopupError(Integer error){
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
//    }
//
//    void showPopupSuccess(String success){
//        Toast.makeText(getContext(), success, Toast.LENGTH_LONG).show();
//    }
}
