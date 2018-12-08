package com.example.zhuyu.phx_final;

import android.util.Log;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class MailSenderUtils{

    private static final String HOSTNAME = "smtp.gmail.com";
    private static final int SMTPPORT = 465;
    private static final String RECEIVER_ADDRESS = "zhuyuli@bu.edu";
    private static final String SENDER_ADDRESS = "zhuyulicfc@gmail.com";
    private static final String SENDER_PASSWORD = "Blue14theColor";
    private String subject;
    private String text;
    private static final String TAG = "MailSenderActivity";


    public MailSenderUtils(String subject, String text) {
        this.subject = subject;
        this.text = text;
        Log.d(TAG, text);
    }

    public void sendEmail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleEmail email = new SimpleEmail();
                    email.setHostName(HOSTNAME);
                    email.setSmtpPort(SMTPPORT);
                    email.setAuthenticator(new DefaultAuthenticator(SENDER_ADDRESS, SENDER_PASSWORD));
                    email.setSSLOnConnect(true);
                    email.setFrom(SENDER_ADDRESS);
                    email.setSubject(subject);
                    email.setMsg(text);
                    email.addTo(RECEIVER_ADDRESS);
                    email.send();
                } catch (Exception e) {
                    Log.d(TAG, "this time" + e.toString());
                }
            }
        }).start();


    }




}
