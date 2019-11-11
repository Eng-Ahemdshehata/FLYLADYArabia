package com.ashehata.flyladyarabia.ui.fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.adapters.IconAdapter;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.utility.AppUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {

    public  Preference userNameET;

    RecyclerView recyclerView ;
    List<Integer> listIcons ;
    IconAdapter.ListItemClickListener itemClickListener ;
    GridLayoutManager gridLayoutManager ;
    View badge ;
    RelativeLayout oldLayout;
    int userIcon ;
    TimePickerDialog mTimePicker;
    Date myDate ;
    int hour ;
    int min ;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.settings, rootKey);


        try {

            findPreference("user").setIconSpaceReserved(false);
            findPreference("app").setIconSpaceReserved(false);
            findPreference("time").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(final Preference preference) {

                    Date myCurrentDate = AppUtility.getCurrentTime();

                    //Log.v("myCurrentDate",myCurrentDate.getHours()+"" );

                    // Read from userNameET to check the date
                    Date myPreviousDate =  AppPreference.readDate(getContext(),AppPreference.MY_PREVIOUS_DATE) ;

                    Log.v("myPreviousDate",myPreviousDate.getHours() +"");

                    if(myCurrentDate.getHours() < myPreviousDate.getHours()){

                        // show time picker dialog
                        createAlertDialog();

                    } else if (myCurrentDate.getHours() == myPreviousDate.getHours()){

                        if(myCurrentDate.getMinutes() < myPreviousDate.getMinutes()){
                            // show time picker dialog
                            createAlertDialog();
                        }else {

                            AppUtility.createToast(getContext()
                                    , getString(R.string.cant_change)
                                    , Toast.LENGTH_LONG);


                        }

                    }else
                        AppUtility.createToast(getContext()
                                , getString(R.string.cant_change)
                                ,Toast.LENGTH_LONG);


                    return true ;
                }
            });




            findPreference("icon").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    createIcons();

                    // red user icon
                    userIcon = AppPreference.readInt(getContext(),AppPreference.USER_ICON,userIcon);

                    setItemClickListner();

                    createAlertDialogIcon();

                    //Log.v("notification_settings",AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_KEY) +" ");
                    //Log.v("notification_sound",AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_SOUND_KEY) +" ");



                    return true;
                }
            });


            if(AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_KEY)){
                findPreference(getString(R.string.notification_sound_key)).setEnabled(true);
            }else
                findPreference(getString(R.string.notification_sound_key)).setEnabled(false);


            findPreference(getString(R.string.notification_key)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Preference soundPreference = findPreference(getString(R.string.notification_sound_key)) ;
                    //Log.v("notification_settings",AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_KEY) +" ");
                    //Log.v("notification_sound",AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_SOUND_KEY) +" ");

                    if( AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_KEY)){

                        soundPreference.setEnabled(false);
                        Log.v("notification",AppPreference.readBoolean(getContext(),AppPreference.NOTIFICATION_KEY) +" ");

                    }else
                        soundPreference.setEnabled(true);


                    return true;
                }
            });



             userNameET = findPreference("user_name") ;

            userNameET.setSummary(AppPreference.readString(getContext()
                    ,AppPreference.USER_NAME
                    ,"My name"));






        }catch (Exception e){

        }


    }

    private void createAlertDialogIcon() {
        View view = View.inflate(getContext(),R.layout.recycler_view_user_icon,null);
        recyclerView = view.findViewById(R.id.set_up_fragment_rv_icons);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new IconAdapter(getContext(),listIcons,itemClickListener));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle(getString(R.string.choose_icon));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Save user icon
                AppPreference.writeInt(AppPreference.USER_ICON,userIcon);
            }
        });
        builder.show();
    }

    private void setItemClickListner() {
        itemClickListener = new IconAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemResId, View view) {

                RelativeLayout newLayout = (RelativeLayout) view ;

                if(oldLayout !=null){
                    oldLayout.removeView(badge);
                }

                //RelativeLayout.LayoutParams layoutParams =new RelativeLayout.LayoutParams(getContext(),  );
                newLayout.addView(badge,new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));


                oldLayout = newLayout ;
                userIcon = clickedItemResId ;



            }
        };

    }

    private void createAlertDialog() {

        myDate = AppPreference.readDate(getContext(),AppPreference.MY_PREVIOUS_DATE);
        hour = myDate.getHours();
        min = myDate.getMinutes();
        if(myDate == null){
            hour = 10 ;
            min = 30 ;
        }
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    AppUtility.storeTime(getActivity(),selectedHour,selectedMinute);
                Date date = new Date();
                date.setHours(selectedHour);
                date.setMinutes(selectedMinute);
                date.setSeconds(0);

                AppPreference.writeDate(AppPreference.MY_PREVIOUS_DATE,date);


            }
        },hour,min, false); //Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

    private void createIcons() {
        badge = View.inflate(getContext(),R.layout.unread_message_layout,null);
        oldLayout = null ;

        listIcons = new ArrayList<>();
        listIcons.add(R.drawable.ic_face_1);
        listIcons.add(R.drawable.ic_face_2);
        listIcons.add(R.drawable.ic_face_3);
        listIcons.add(R.drawable.ic_face_4);
        listIcons.add(R.drawable.ic_face_5);
        listIcons.add(R.drawable.ic_face_6);




    }


}
