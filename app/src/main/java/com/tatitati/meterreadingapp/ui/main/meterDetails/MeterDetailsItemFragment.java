package com.tatitati.meterreadingapp.ui.main.meterDetails;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeterDetailsItemFragment extends Fragment {

    private static final int CAMERA_REQUEST = 0;
    private static final int EMAIL_REQUEST = 0;
    public static final int RESULT_OK = -1;
    private View view;
    private MeterItemModel meterItemModel;
    private FragmentManager fragmentManager;
    private TextView curr_meter_number;
    private TextView curr_last_meterReading_date;
    private TextView curr_last_meterReading_value;
    private Button btn_show_meter_history_one;
    private Button btn_edit_meter;
    private Button btn_delete_meter;
    private EditText input_meter_new_value;
    private ImageButton btn_open_camera;
    private Button btn_send_meter_value;
    private Meter meter;

    public MeterDetailsItemFragment(Meter meter) {
        this.meter = meter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meter_details, container, false);
        meterItemModel = new MeterItemModel(view.getContext(), meter);
        fragmentManager = getFragmentManager();

        curr_meter_number = view.findViewById(R.id.curr_meter_number);
        curr_last_meterReading_date = view.findViewById(R.id.curr_last_meterReading_date);
        curr_last_meterReading_value = view.findViewById(R.id.curr_last_meterReading_value);
        btn_show_meter_history_one = view.findViewById(R.id.btn_show_meter_history_one);
        btn_edit_meter = view.findViewById(R.id.btn_edit_meter);
        btn_delete_meter = view.findViewById(R.id.btn_delete_meter);
        input_meter_new_value = view.findViewById(R.id.input_meter_new_value);
        btn_open_camera = view.findViewById(R.id.btn_open_camera);
        btn_send_meter_value = view.findViewById(R.id.btn_send_meter_value);

        fillMeterData();

        meterItemModel.getMeterResult().observe(this, new Observer<MeterResult>() {
            @Override
            public void onChanged(MeterResult meterResult) {
                if (meterResult.getError() != null){
                    showError(meterResult.getError());
                    return;
                }
                if (meterResult.getSuccess() != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });

        btn_show_meter_history_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_edit_meter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MeterDetailsEditFragment(meter, false),"MeterDetailsEditFragment")
                        .commit();
            }
        });

        btn_open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btn_send_meter_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meter.getmService() == null) {
                    Service service = new Service();
                    service.setEmail("ukuniverse1@email.com");
                    meter.setmService(service);
                };
                if (input_meter_new_value.getText() == null) return;
                Intent emailIntent = meterItemModel.getEmailIntent(meter, input_meter_new_value.getText().toString());
                startActivity(Intent.createChooser(emailIntent,"Отправка письма..."));
//                meterItemModel.openServiceDialog(input_meter_new_value.getText().toString());
            }
        });


        btn_show_meter_history_one.setCursorVisible(false);
        return view;
    }

    void fillMeterData(){
        curr_meter_number.setText(("Счетчик №" + meter.getMeterNumber()));

        MeterReading lastReading = meterItemModel.getLastMeterReading(meter);
        if (lastReading == null) return;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        curr_last_meterReading_date.setText(formatter.format(new Date(lastReading.getDate())));
        curr_last_meterReading_value.setText(lastReading.getValue().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            //firebase
            FirebaseVisionImage imageFirebase = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVisionDocumentTextRecognizer detector = FirebaseVision.getInstance()
                    .getCloudDocumentTextRecognizer();
            //recognizer
            detector.processImage(imageFirebase)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionDocumentText>() {
                        @Override
                        public void onSuccess(FirebaseVisionDocumentText result) {
                            String resultText = result.getText();
                            input_meter_new_value.setText(resultText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            input_meter_new_value.setText("-1");
                        }
                    });
        }
    }

    void showError(Integer err){
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }
}

