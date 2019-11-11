package com.ashehata.flyladyarabia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.BaseFragment;
import com.ashehata.flyladyarabia.ui.activity.HomeActivity;
import com.ashehata.flyladyarabia.utility.AppUtility;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderFragment extends BaseFragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
   // String [] description = {"اهتمي بنظافـة بيتـكـ","نظمـي وقتك كــل يومـ","حافظي علي نظافه منزلـك"};

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            CircleImageView customView = (CircleImageView) getLayoutInflater().inflate(R.layout.circle_image, null);
            //set view attributes here
            customView.setImageResource(sampleImages[position]);
            return customView;
        }
    };


    TextView textView ;

    Button btn_skip ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider,container,false);
        slider(view);
        setUpActivity();
        textView = getActivity().findViewById(R.id.splash_activity_tv_title);
        textView.setText(getString(R.string.slider));

        btn_skip = view.findViewById(R.id.slider_fragment_btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetUpFragment setUpFragment = new SetUpFragment() ;
                setUpFragment.userName = AppPreference.readString(getContext(),AppPreference.USER_NAME,"");

                AppUtility.ReplaceFragment(getFragmentManager(),setUpFragment,R.id.splash_activity_fl_display);

            }
        });



        return view ;
    }
    private void slider(View view){
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);

    }

    private void intentHomeActivity(){
        Intent mainIntent = new Intent(getContext() , HomeActivity.class );
        startActivity(mainIntent);
        getActivity().finish();

    }
    @Override
    public void onBack() {
        getActivity().finish();

    }
}
