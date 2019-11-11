package com.ashehata.flyladyarabia.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.ashehata.flyladyarabia.ui.BaseFragment;
import com.ashehata.flyladyarabia.utility.AppUtility;


public class SetUpFragment extends BaseFragment {

    Button button ;
    EditText editText ;
    public String userName;
    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_up,container,false);

        textView = getActivity().findViewById(R.id.splash_activity_tv_title);
        textView.setText(getString(R.string.set_up_settings));
        setUpActivity();
        findViews(view);


        return view;
    }



    private void findViews(View view) {
        button = view.findViewById(R.id.set_up_fragment_btn_next);
        editText = view.findViewById(R.id.set_up_fragment_et_user_name);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        if(userName != null){
            editText.setText(userName);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Make Edit text Focusable
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);

        setNextBtn();


        super.onViewCreated(view, savedInstanceState);
    }

    private void setNextBtn() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserName();
            }
        });
    }

    private boolean getUserName() {
        // Getting user name from ET
        userName =editText.getText().toString() ;
        if (userName.isEmpty()){
            AppUtility.createToast(getActivity(),"يجب ادخال الاسم", Toast.LENGTH_SHORT);
            return false ;
        }else {

            // Write user name at AppPreference
            AppPreference.writeString(AppPreference.USER_NAME,userName);

            AppUtility.ReplaceFragment(getFragmentManager(),new SetUpStep2Fragment(),R.id.splash_activity_fl_display);
            return true ;
        }


    }

    @Override
    public void onBack() {
        AppUtility.ReplaceFragment(getFragmentManager(),new SliderFragment(),R.id.splash_activity_fl_display);


    }


}
