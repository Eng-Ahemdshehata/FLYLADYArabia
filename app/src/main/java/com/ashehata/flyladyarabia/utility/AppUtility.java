package com.ashehata.flyladyarabia.utility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashehata.flyladyarabia.BuildConfig;
import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.activity.HomeActivity;
import com.ashehata.flyladyarabia.ui.activity.SplashActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppUtility {

    public static String ACTION_PENDING = "action";
    private static Toast toast;

    public static void setLanguage(Context context){
        String lang = AppPreference.readString(context, AppPreference.LANGUAGE_KEY,AppPreference.DEFAULT_LANGUAGE_KEY);
        //Toast.makeText(context, lang, Toast.LENGTH_SHORT).show();

        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang.toLowerCase())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }

    public static void storeTime(Activity context , int selectedHour, int selectedMinute){
        // store hours into userNameET
        Date date = new Date( );
        date.setHours(selectedHour);
        date.setMinutes(selectedMinute);
        AppPreference.writeDate(AppPreference.MY_PREVIOUS_DATE,date);
        // display message to inform the user

        int newHour ;
        if(selectedHour > 12){
            newHour = selectedHour - 12 ;
        }else {
            newHour = selectedHour ;
        }
        String period = "" ;
        if( 12== selectedHour ){
            period = "ظهـراً";

        }else if ( 0 <= selectedHour && selectedHour  <= 11  ){
            period = "صباحـاً";

        }else if(12 < selectedHour && selectedHour  <= 23 ){
            period = "مسـاءً";

        }
        String message =context.getString(R.string.display_message)+" "+selectedMinute+" : "+newHour+" "+period ;
        createToast(context,message,Toast.LENGTH_SHORT);
        Log.v("time",message);

    }



    public static void sendNotification(Context context){

        Uri defaultSoundUri ;

        // Read from userNameET if notification sound enabled or disabled
        boolean b = AppPreference.readBoolean(context,AppPreference.NOTIFICATION_SOUND_KEY);

        if(b){
            defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Log.v("noti",b+"");

        }else {
            Log.v("noti",b+"");

            defaultSoundUri= null ;
        }

        // Sending intent to launch your activity
        Intent notificationIntent = new Intent(context, HomeActivity.class);
        notificationIntent.setAction("notification");

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                // cancel the notification when clicked by the user
                PendingIntent.FLAG_UPDATE_CURRENT);


        // Notification reference
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification properties
        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.logo)
                .setLargeIcon(BitmapFactory.decodeResource(res,R.drawable.slider2))
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("مهمات اليوم ")
                .setContentText("اضغطي لمزيد من التفاصيل");

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notify00"
            ,"channel"
            ,NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);
        }
        */

        Notification n = builder.build();

        /*
        n.defaults|= Notification.DEFAULT_SOUND;
        n.defaults|= Notification.DEFAULT_LIGHTS;
        n.defaults|= Notification.DEFAULT_VIBRATE;
        */

        //n.defaults = Notification.DEFAULT_ALL;

        nm.notify(0,n);
    }
    public static void startScheduling(Context context ,Date myDate) {

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY,myDate.getHours());
        c.set(Calendar.MINUTE,myDate.getMinutes());
        c.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlertReceiver.class);
        intent.setAction(ACTION_PENDING);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        /*
        // Launch pending intent after
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        }
        */

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP
                ,c.getTimeInMillis()
                ,AlarmManager.INTERVAL_DAY
                ,pendingIntent);

    }
    public static void stopScheduling(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        alarmManager.cancel(pendingIntent);
    }

    public static Date getCurrentTime(){
        Date date = new Date();


        //Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
        //cal.setTime(date);
        /*
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        */
        //String myCurrentDate = hour+min+second+"";

        return date ;
    }



    public static void ReplaceFragment(FragmentManager supportFragmentManager, Fragment fragment, int container_id
           ) {

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.pop_enter,R.anim.pop_exit);
        transaction.replace(container_id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        /*
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
        */
    }
    public static void createToast(Context context , String title , int duration){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, title, duration);
        toast.show();

    }
    public static void restartActivity(Activity activity,Class<?> cls){
        activity.finish();
        activity.overridePendingTransition(0,0);
        activity.startActivity(new Intent(activity, cls));
        activity.overridePendingTransition(0,0);
    }
    public static void shareApp(Activity activity) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }

    public static void followUsOnFacebook(Activity activity) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(activity);
        facebookIntent.setData(Uri.parse(facebookUrl));
        activity.startActivity(facebookIntent);
    }

    //method to get the right URL to use in the intent
    public static String getFacebookPageURL(Context context) {
        String FACEBOOK_URL = "https://www.facebook.com/flyladyarabia/?ref=br_rs";

        String FACEBOOK_PAGE_ID = "FLY LADY Arabia";
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static void rateApp(Activity activity){

        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + activity.getPackageName()));
            activity.startActivity(sendIntent);


        } catch (android.content.ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static String getVersionName() {

        return BuildConfig.VERSION_NAME;
    }
}