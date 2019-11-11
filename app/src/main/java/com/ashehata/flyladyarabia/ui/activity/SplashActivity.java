package com.ashehata.flyladyarabia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.ui.BaseActivity;
import com.ashehata.flyladyarabia.ui.fragment.SliderFragment;
import com.ashehata.flyladyarabia.utility.AppUtility;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(savedInstanceState == null){
            // Hide notification bar
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // set app language
            AppUtility.setLanguage(this);

            //getSupportFragmentManager().beginTransaction().add(R.id.splash_activity_fl_display,new SplashFragment()).commit();
            /* New Handler to start the Home-Activitye
             * and close this Splash-Screen after some seconds.*/

            // Read from userNameET

            final boolean b = AppPreference.readBoolean(getApplicationContext(), AppPreference.SKIP_KEY);

            if(b){

                intentHomeActivity(SplashActivity.this);

            }else {

                getSupportFragmentManager().beginTransaction().replace(R.id.splash_activity_fl_display,new SliderFragment()).commit();
            }
            /*
            final boolean b = AppPreference.readBoolean(getApplicationContext(), AppPreference.SKIP_KEY);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {

                    if(b){

                        intentHomeActivity(SplashActivity.this);

                    }else {

                        getSupportFragmentManager().beginTransaction().replace(R.id.splash_activity_fl_display,new SliderFragment()).commit();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);

            */

        }
    }
    private void intentHomeActivity(Context context){
        Intent mainIntent = new Intent(context , HomeActivity.class );
        //mainIntent.setAction("main");
        startActivity(mainIntent);
        finish();

    }


}
