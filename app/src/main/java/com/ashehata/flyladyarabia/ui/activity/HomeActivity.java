package com.ashehata.flyladyarabia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.fragment.AboutMeFragment;
import com.ashehata.flyladyarabia.ui.fragment.ChartFragment;
import com.ashehata.flyladyarabia.ui.fragment.MainFragment;
import com.ashehata.flyladyarabia.ui.fragment.NotificationFragment;
import com.ashehata.flyladyarabia.ui.fragment.SettingsFragment;
import com.ashehata.flyladyarabia.utility.AppUtility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import com.ashehata.flyladyarabia.BuildConfig;

import java.util.Date;


public class HomeActivity extends AppCompatActivity
    implements OnSharedPreferenceChangeListener
        , NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    createFragment(getString(R.string.title_home),mainFragment);
                    return true;

                case R.id.navigation_notifications:

                    createFragment(getString(R.string.title_notifications),notificationFragment);
                    return true;

                case R.id.navigation_chart:
                    createFragment(getString(R.string.title_chart),chartFragment);

                    return true;

                case R.id.navigation_settings:

                    createFragment(getString(R.string.title_settings),settingsFragment);

                    return true;

            }
            return false;
        }
    };



    //*****
    final Fragment mainFragment = new MainFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    final Fragment notificationFragment = new NotificationFragment();
    final Fragment chartFragment = new ChartFragment() ;
    final Fragment aboutMeFragment = new AboutMeFragment() ;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = mainFragment;
    //**************
    TextView head , userNametxt;
    SharedPreferences sharedPreferences ;
    BottomNavigationView navView ;
    ImageView imageView ;
    String userName ;
    int userIcon ;
    NavigationView navigationView ;
    MenuItem menuItem ;
    DrawerLayout drawer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set app language
        AppUtility.setLanguage(this);


        // Inflates the XML file
        setContentView(R.layout.activity_home);


        // Getting user name from userNameET
        userName = AppPreference.readString(this,AppPreference.USER_NAME,"My name") ;

        // Getting user icon from userNameET
        userIcon = AppPreference.readInt(this,AppPreference.USER_ICON,R.drawable.ic_face_1);

        // set activity name
        setTitle("");
        head = findViewById(R.id.home_activity_tv_head);

        // Display fragments
        displayFragment();

        // inflates navigation view UI
        naviViewUi();

        /*
        * Get notification num from adapter and passing it for method below that
        *  inflates bottom navigation view UI
         */
        bottomNaviViewUi();

        // shared AppPreference listener
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Getting intent from Notification

        Intent intent = getIntent() ;
        if(intent!=null && intent.getAction() != null){
            Log.v("intent","done");
            String action = intent.getAction() ;

            if(action.equals("notification")){

                navView.setSelectedItemId(R.id.navigation_notifications);
            }

        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (active.getTag().equals("1")){
                //Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
                super.onBackPressed();

            }else {

                navView.setSelectedItemId(R.id.navigation_home);
            }

        }
        Log.v("Tag",active.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_about_app :

                break ;

            case R.id.nav_follow_us :
                followUsOnFacebook();

                break;
            case R.id.nav_share:
                shareApp();

                break;
            case R.id.nav_star :
                rateApp();


                break;
            case R.id.nav_about_me :
                createFragment(getString(R.string.about_programmer),aboutMeFragment);

                break;
            case R.id.nav_version_name :
                return false ;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void followUsOnFacebook() {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
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

    private void rateApp() {

        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + this.getPackageName()));
            startActivity(sendIntent);


        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }


    }

    private void shareApp() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + this.getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    private void naviViewUi(){

        createToolAndDrawer();

        // Getting the nav header ui
        View view = navigationView.getHeaderView(0);



        // set user icon
        setUserIcon(view);

        //set user name
        setUserName(view);

        // set version name
        setVersionName();

        // Change navigation drawer icon
        changeDrawerIcon();

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setVersionName() {

        // Getting parent menu
        Menu menu = navigationView.getMenu();

        String versionName = BuildConfig.VERSION_NAME;
        menuItem = menu.findItem(R.id.nav_version_name);
        menuItem.setCheckable(false);
        menuItem.setTitle(getString(R.string.version_name,versionName));


    }

    private void createToolAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUserIcon(View view) {
        imageView = view.findViewById(R.id.home_activity_iv_pic);
        imageView.setImageResource(userIcon);
    }

    private void setUserName(View view) {
        userNametxt= view.findViewById(R.id.home_activity_tv_user_name);
        userNametxt.setText(userName);
    }

    private void changeDrawerIcon() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navi_menu);
    }

    private void bottomNaviViewUi(){

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //navView.setSelectedItemId(R.id.navigation_home);

        /*
        // Notification Badge
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        badge = LayoutInflater.from(this)
                .inflate(R.layout.unread_message_layout,bottomNavigationMenuView ,false);
        TextView tv = badge.findViewById(R.id.tvUnreadChats);
        tv.setText("");
        itemView.addView(badge);
        */
    }


    private void restartActivity(){
        Intent intent = getIntent() ;
        finish();
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        Log.v("Home Activity"," "+s);
        Context context = HomeActivity.this ;


        if(s.equals(AppPreference.LANGUAGE_KEY) ){
            //Log.v("Home Activity"," "+s.intern());
            //Log.v("Home Activity"," "+s.length());
            restartActivity();

        }
        if (s.equals(AppPreference.USER_NAME)){


            // Getting user name from userNameET again
            userName = AppPreference.readString(context,AppPreference.USER_NAME,"My name") ;
            settingsFragment.userNameET.setSummary(userName);

            userNametxt.setText(userName);



        }
        if( s.equals(AppPreference.MY_PREVIOUS_DATE)  ){

            Log.v("my date","changed");

            // Getting values from userNameET
            Date date = AppPreference.readDate(this,AppPreference.MY_PREVIOUS_DATE) ;
            date.getHours();
            date.getMinutes();



            // Start new scheduling
            AppUtility.startScheduling(context,date);


        }

        if(s.equals(AppPreference.USER_ICON)){
            // red user icon
            int newIcon = AppPreference.readInt(this,AppPreference.USER_ICON,userIcon);
            imageView.setImageResource(newIcon);


        }
        if (s.equals(AppPreference.NOTIFICATION_KEY)){
            Log.v("notification_home",AppPreference.readBoolean(this,AppPreference.NOTIFICATION_KEY) +" ");


        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);

    }

    private void createFragment(String heahName,Fragment fragment){
        // Set activity title
        head.setText(heahName);
        fm.beginTransaction().hide(active).show(fragment).commit();
        active = fragment;


    }
    private void displayFragment(){
        fm.beginTransaction().add(R.id.home_activity_fl_display, settingsFragment, "4").hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.home_activity_fl_display, notificationFragment, "2").hide(notificationFragment).commit();
        fm.beginTransaction().add(R.id.home_activity_fl_display, chartFragment,"3").hide(chartFragment).commit();
        fm.beginTransaction().add(R.id.home_activity_fl_display, aboutMeFragment,"about_me").hide(aboutMeFragment).commit();
        fm.beginTransaction().add(R.id.home_activity_fl_display, mainFragment, "1").commit();

        if(active.getTag().equals("1")){
            head.setText(getString(R.string.title_home));
        }

    }


}
