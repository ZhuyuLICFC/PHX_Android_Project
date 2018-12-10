package com.example.zhuyu.phx_final;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuyu.phx_final.utils.MailSenderUtils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class FeedbackActivity extends AppCompatActivity {

    private TextView mContactUsTextView;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mOrganizationTextView;
    private TextView mQuestionTextView;


    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mOrganizationEditText;
    private EditText mQuestionEditText;
    private Button mSubmitButton;

    private static final String TAG = "MailSenderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feedback_activity);

        mContactUsTextView = findViewById(R.id.contact_us_text_view);
        mNameTextView = findViewById(R.id.name_text_view);
        mEmailTextView = findViewById(R.id.email_text_view);
        mOrganizationTextView = findViewById(R.id.organization_text_view);
        mQuestionTextView = findViewById(R.id.question_text_view);


        mNameEditText = findViewById(R.id.name_edit_text);
        mEmailEditText = findViewById(R.id.email_edit_text);
        mOrganizationEditText = findViewById(R.id.organization_edit_text);
        mQuestionEditText = findViewById(R.id.question_edit_text);

        mSubmitButton = findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgToSend = "Feedback Info:\n\n" + "Name: " + mNameEditText.getText().toString()
                        + "\n\nEmail: " + mEmailEditText.getText().toString() + "\n\nOrganization: " + mOrganizationEditText.getText().toString() + "\n\nQuestion/Comment: " + mQuestionEditText.getText().toString();
                MailSenderUtils mailSenderUtils = new MailSenderUtils("FeedBack", msgToSend);
                mailSenderUtils.sendEmail();
            }
        });
    }

}
