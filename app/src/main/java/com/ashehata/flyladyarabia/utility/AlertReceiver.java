package com.ashehata.flyladyarabia.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.room.AppDataBase;
import com.ashehata.flyladyarabia.data.room.notificationTable.NotificationEntity;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.fragment.SettingsFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        final Context mContext = context ;
        // Adding data to room DB

        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {

                if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
                    Date myCurrentDate = AppUtility.getCurrentTime();

                    // Read from userNameET to check the date
                    Date myPreviousDate =  AppPreference.readDate(context,AppPreference.MY_PREVIOUS_DATE) ;


                    if(myCurrentDate.getHours() >  myPreviousDate.getHours()){
                        //start scheduling if the time is passed
                        startScheduling(context);

                    } else if (myCurrentDate.getHours() == myPreviousDate.getHours()){

                        if(myCurrentDate.getMinutes() > myPreviousDate.getMinutes()){
                            startScheduling(context);
                        }else {

                        }
                    }else {
                        //start scheduling if the time isn't passed
                        startScheduling(context);
                    }

                }else if (intent.getAction().equals(AppUtility.ACTION_PENDING)) {
                    startScheduling(context);
                }
            }
        });
    }

    private void startScheduling(Context mContext) {

        List<NotificationEntity> allNotification = dailyWork() ;

        AppDataBase.getInstance(mContext).dataDao().insertAllNotification(allNotification);


        //Sending Notification
        if(AppPreference.readBoolean(mContext,AppPreference.NOTIFICATION_KEY)){

            AppUtility.sendNotification(mContext);

        }
    }

    public List<NotificationEntity> dailyWork(){


        List<NotificationEntity> allNotification = new ArrayList<>();

        allNotification.add(new NotificationEntity("ترتيب السرير بعد الاستيقاظ","",R.drawable.slider1,"يومي"));
        allNotification.add(new NotificationEntity("انقاذ غرفة في 10 دقائق","",R.drawable.slider2,"يومي"));
        allNotification.add(new NotificationEntity("تلميع الحوض قبل النوم","",R.drawable.slider3,"يومي"));


        return allNotification ;

    }

    public NotificationEntity weeklyWork(){


        Date date = AppUtility.getCurrentTime() ;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
        cal.setTime(date) ;


        int i = 0 ;

        /*
        switch (i){
            case Calendar. :
                    break ;
            case Calendar.SUNDAY :
                break;
            case Calendar.MONDAY :
                break;

        }

*/
        NotificationEntity notificationEntity = new NotificationEntity("الصالـة",
                "اضغطي لمعرفه طريقة التنظيـف",
                R.drawable.slider2,
                "اليومي");

        return notificationEntity ;

    }
}
