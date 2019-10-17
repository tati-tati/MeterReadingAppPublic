package com.tatitati.meterreadingapp.ui.main.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.tatitati.meterreadingapp.R;

public class AddServiceFragment extends Fragment {

    private View view;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private Button btn_save;
    private AddServiceModel addServiceModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_service, container, false);
        editTextName = view.findViewById(R.id.add_service_name);
        editTextEmail = view.findViewById(R.id.add_service_email);
        editTextPhone = view.findViewById(R.id.add_service_phone);
        btn_save = view.findViewById(R.id.btn_save_service);

        addServiceModel = new AddServiceModel(getContext());
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceModel.checkValid(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPhone.getText().toString());
            }
        });

//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addServiceModel.checkValid(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString());
//            }
//        });

        addServiceModel.getServiceResult().observe(this, new Observer<ServiceResult>() {
            @Override
            public void onChanged(ServiceResult serviceResult) {
                if (serviceResult.getError() != null){
                    Toast.makeText(getContext(), serviceResult.getError(), Toast.LENGTH_SHORT);
                    return;
                }
                if (serviceResult.getSuccess() != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });


        return view;
    }
}
