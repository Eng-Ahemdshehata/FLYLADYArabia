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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(savedInstanceState == null){
            // Hide notification bar
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // set app language
            AppUtility.setLanguage(this);

            // Read from userNameET

            final boolean b = AppPreference.readBoolean(getApplicationContext(), AppPreference.SKIP_KEY);

            if(b){
                intentHomeActivity(SplashActivity.this);

            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.splash_activity_fl_display,new SliderFragment()).commit();
            }
        }
    }
    private void intentHomeActivity(Context context){
        Intent mainIntent = new Intent(context , HomeActivity.class );
        //mainIntent.setAction("main");
        startActivity(mainIntent);
        finish();

    }
}