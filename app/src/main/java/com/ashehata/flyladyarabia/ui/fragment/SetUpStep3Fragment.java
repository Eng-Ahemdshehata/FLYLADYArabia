package com.ashehata.flyladyarabia.ui.fragment;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.BaseFragment;
import com.ashehata.flyladyarabia.ui.activity.HomeActivity;
import com.ashehata.flyladyarabia.utility.AppUtility;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpStep3Fragment extends BaseFragment {

    private Button button ;
    private Date myDate ;
    private int hour ;
    private int min ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_set_up_step3, container, false);
        setUpActivity();

        button = view.findViewById(R.id.set_up_3_fragment_btn_select);

        setTimeButton();

        return view;
    }

    private void setTimeButton() {
        AppPreference.readDate(getContext(),AppPreference.MY_PREVIOUS_DATE);
        if(myDate == null){
            hour = 10 ;
            min = 30 ;
        }else {
            hour = myDate.getHours();
            min = myDate.getMinutes();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show time picker dialog
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        AppUtility.storeTime(getActivity(),selectedHour,selectedMinute);

                        // write to AppPreference
                        AppPreference.writeBoolean(AppPreference.SKIP_KEY,true);

                        // intent to home activity
                        intentHomeActivity();

                        Date date = new Date();
                        date.setHours(selectedHour);
                        date.setMinutes(selectedMinute);
                        date.setSeconds(0);

                        // Start scheduling
                        AppUtility.startScheduling(getContext(),date);

                    }
                }, hour,
                        min,
                        false); //Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();
            }
        });
    }

    private void intentHomeActivity(){
        Intent mainIntent = new Intent(getContext() , HomeActivity.class );
        startActivity(mainIntent);
        getActivity().finish();
    }

    @Override
    public void onBack() {
        AppUtility.ReplaceFragment(getFragmentManager(),new SetUpStep2Fragment(),R.id.splash_activity_fl_display);
    }
}