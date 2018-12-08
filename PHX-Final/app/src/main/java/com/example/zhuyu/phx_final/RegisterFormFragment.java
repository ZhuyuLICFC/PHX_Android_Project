package com.example.zhuyu.phx_final;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class RegisterFormFragment extends Fragment{

    private static final int REQ_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private boolean isFirstTime = true;
    private String userInfoPath;
    private static final String TAG = "MainActivity";
    private PermissionUtils permissionUtils;
    private Button mSubmitButton;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mEmailEditText;
    private EditText mCountryEditText;
    private EditText mStateEditText;
    private EditText mCityEditText;
    private CheckBox mCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PHX" + File.separator + "User" + File.separator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.main_register_form, container, false);
        mFirstNameEditText = v.findViewById(R.id.first_name_edit_text);
        mLastNameEditText = v.findViewById(R.id.last_name_edit_text);
        mEmailEditText = v.findViewById(R.id.email_edit_text);
        mCountryEditText = v.findViewById(R.id.country_edit_text);
        mCityEditText = v.findViewById(R.id.city_edit_text);
        mStateEditText = v.findViewById(R.id.state_edit_text);
        mCheckBox = v.findViewById(R.id.latest_info_checkBox);
        mSubmitButton = v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailSenderUtils mailSenderUtils = new MailSenderUtils("Registration Form", getUserInfo());
                mailSenderUtils.sendEmail();
                FileOperationUtils.createDirectory(FileOperationUtils.getUserDirectory());
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goToMain();

            }
        });

        return v;
    }

    public String getUserInfo() {
        return "First Name: " + mFirstNameEditText.getText().toString() + "\nLast Name: " +
                mLastNameEditText.getText().toString() + "\nEmail Address: " + mEmailEditText.getText().toString() + "\nCountry: " + mCountryEditText.getText().toString() +
                "State: " + mStateEditText.getText().toString() + "City: " + mCityEditText.getText().toString() + "\nWilling to receive news: " + mCheckBox.isChecked();
    }

}
