package com.bignerdranch.android.mailsender;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class MailSenderActivity extends AppCompatActivity {

    private TextView mContactUsTextView;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mQuestionTextView;
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mQuestionEditText;
    private Button mSubmitButton;

    private static final String TAG = "MailSenderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mail_sender);

        mContactUsTextView = findViewById(R.id.contact_us_text_view);
        mNameTextView = findViewById(R.id.name_text_view);
        mEmailTextView = findViewById(R.id.email_text_view);
        mQuestionTextView = findViewById(R.id.question_text_view);

        mNameEditText = findViewById(R.id.name_edit_text);
        mEmailEditText = findViewById(R.id.email_edit_text);
        mQuestionEditText = findViewById(R.id.question_edit_text);

        mSubmitButton = findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String msgToSend = "Feedback Info:\n\n" + "Name: " + mNameEditText.getText().toString()
                                    + "\n\nEmail: " + mEmailEditText.getText().toString() + "\n\nQuestion/Comment: " + mQuestionEditText.getText().toString();
                            SimpleEmail email = new SimpleEmail();
                            email.setHostName("smtp.gmail.com");
                            email.setSmtpPort(465);
                            email.setAuthenticator(new DefaultAuthenticator("sender gmail address", "password"));
                            email.setSSLOnConnect(true);
                            email.setFrom("sender gmail address");
                            email.setSubject("User FeedBack");
                            email.setMsg(msgToSend);
                            Log.d(TAG, msgToSend);
                            email.addTo("receiver gmail address");
                            email.send();
                            Log.d(TAG, "Send successa");
                        } catch (EmailException e) {
                            Log.d(TAG, "this time" + e.toString());
                        }
                    }
                }).start();
            }
        });
    }


}
