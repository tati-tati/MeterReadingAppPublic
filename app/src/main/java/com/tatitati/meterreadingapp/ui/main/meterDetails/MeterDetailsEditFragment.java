package com.tatitati.meterreadingapp.ui.main.meterDetails;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterType;
import com.tatitati.meterreadingapp.database.database_objects.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;


public class MeterDetailsEditFragment extends Fragment {
    private View view;
    private MeterDetailsFragmentModel meterDetailsFragmentModel;
    private TextView meter_details_edit_title;
    private EditText meterNumber;
    private TextView selected_type;
    private TextView createDate;
    private TextView selected_service;
    private Button btn_saveMeter;
    private int mDay;
    private int mMonth;
    private int mYear;
    private MeterType type;
    private Meter meter;
    private boolean isNew;

    public MeterDetailsEditFragment(MeterType type, boolean isNew){
        this.type = type;
        this.isNew = isNew;
    }

    public MeterDetailsEditFragment(Meter meter, boolean isNew) {
        this.meter = meter;
        this.isNew = isNew;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meter_details_edit, container, false);
        meterDetailsFragmentModel = new MeterDetailsFragmentModel(view.getContext());

        meter_details_edit_title = view.findViewById(R.id.meter_details_edit_title);
        meterNumber = view.findViewById(R.id.new_meter_number);
        selected_type = view.findViewById(R.id.selected_meter_type);
        createDate = view.findViewById(R.id.meter_create_date);
        selected_service = view.findViewById(R.id.selected_meter_service);
        btn_saveMeter = view.findViewById(R.id.btn_save_meter);

        if(!isNew) fillViewData(); else selected_type.setText(type.getType());

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String str = day + "/" + month + "/" + year;
                mDay = day;
                mMonth = month;
                mYear = year;
                createDate.setText(str);
            }
        };



        createDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meterDetailsFragmentModel.openDateDialog(dateSetListener);
            }
        });

        selected_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meterDetailsFragmentModel.openServiceDialog();
            }
        });

        meterDetailsFragmentModel.getSelectedService().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                selected_service.setText(s);
            }
        });

        meterDetailsFragmentModel.getMeterState().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showPopupSuccess(s);
            }
        });

        meterDetailsFragmentModel.getMeterResult().observe(this, new Observer<MeterResult>() {
            @Override
            public void onChanged(MeterResult meterResult) {
                if(meterResult == null) return;
                if (meterResult.getError() != null){
                    showPopupError(meterResult.getError());
                    return;
                }
                if (meterResult.getSuccess() != null){
                    showPopupSuccess("Счетчик"
//                            + meterResult.getSuccess().getMeterNumber()
                            + " успешно создан");
                    return;
                }

            }
        });

        btn_saveMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = meterDetailsFragmentModel.findService(selected_service.getText().toString());
                meterDetailsFragmentModel.saveMeter(mDay, mMonth, mYear, meterNumber.getText().toString(), type, service);
            }
        });

        return view;
    }

    void fillViewData(){
        meter_details_edit_title.setText("Изменение счетчика");
        meterNumber.setText(meter.getMeterNumber());
        selected_type.setText(meter.getMeterType().getType());
        if (meter.getCreateDate() == null) return;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        createDate.setText(formatter.format(new Date(meter.getCreateDate())));
//        selected_service.setText(meter.getmService().getServiceName());
    }

    void showPopupError(Integer error){
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    void showPopupSuccess(String success){
        Toast.makeText(getContext(), success, Toast.LENGTH_LONG).show();
    }

}
