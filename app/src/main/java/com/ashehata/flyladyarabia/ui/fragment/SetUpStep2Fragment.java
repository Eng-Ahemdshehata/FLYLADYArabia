package com.ashehata.flyladyarabia.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.adapters.IconAdapter;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.BaseFragment;
import com.ashehata.flyladyarabia.utility.AppUtility;
import java.util.ArrayList;
import java.util.List;

public class SetUpStep2Fragment extends BaseFragment {

    Button button ;
    RecyclerView recyclerView ;
    List<Integer> listIcons ;
    GridLayoutManager gridLayoutManager ;
    View badge ;
    RelativeLayout oldLayout;
    int userIcon = 0 ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_up_step2, container, false) ;

        setUpActivity();

        findViews(view);

        createIcons();

        // Passing data to recycler view
        setData();

        nextBtn();
        return view ;
    }

    private void nextBtn() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               setIcon();

            }
        });
    }

    private boolean setIcon() {
        if(userIcon==0){
            AppUtility.createToast(getActivity(),"يجب اختيار صورة", Toast.LENGTH_SHORT);

            return false ;
        }else {
            // Save user icon
            AppPreference.writeInt(AppPreference.USER_ICON,userIcon);
            AppUtility.ReplaceFragment(getFragmentManager(),new SetUpStep3Fragment(),R.id.splash_activity_fl_display);
            return true ;
        }

    }

    private void createIcons() {

        listIcons = new ArrayList<>();
        listIcons.add(R.drawable.ic_face_1);
        listIcons.add(R.drawable.ic_face_2);
        listIcons.add(R.drawable.ic_face_3);
        listIcons.add(R.drawable.ic_face_4);
        listIcons.add(R.drawable.ic_face_5);
        listIcons.add(R.drawable.ic_face_6);


    }

    private void findViews(View view) {
        button = view.findViewById(R.id.set_up_2_fragment_btn_next);
        recyclerView = view.findViewById(R.id.set_up_fragment_rv_icons);
        badge = View.inflate(getContext(),R.layout.unread_message_layout,null);

        oldLayout = null ;


    }

    private void setData() {


        IconAdapter iconAdapter = new IconAdapter(getContext(), listIcons, new IconAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex, View view) {
                RelativeLayout newLayout = (RelativeLayout) view ;


                if(oldLayout !=null){
                    oldLayout.removeView(badge);
                }

                //RelativeLayout.LayoutParams layoutParams =new RelativeLayout.LayoutParams(getContext(),  );
                newLayout.addView(badge,new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));



                oldLayout = newLayout ;
                userIcon = clickedItemIndex ;

            }
        });
        gridLayoutManager = new GridLayoutManager(getContext(),3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(iconAdapter);


    }

    @Override
    public void onBack() {

        SetUpFragment setUpFragment = new  SetUpFragment() ;
        setUpFragment.userName = AppPreference.readString(getContext(),AppPreference.USER_NAME,"");
        AppUtility.ReplaceFragment(getFragmentManager(),setUpFragment ,R.id.splash_activity_fl_display);

    }
}
