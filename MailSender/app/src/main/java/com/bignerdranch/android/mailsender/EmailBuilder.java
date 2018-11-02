package com.bignerdranch.android.mailsender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailBuilder {

    private Properties mProperties;
    private Session mSession;
    private Message mMessage;
    private String mContent;
    private static final String TAG = "MailSenderActivity";

    public EmailBuilder() {
        super();
        this.mProperties = new Properties();
    }

    public void setProperties(String host, String post) {
        this.mProperties.put("mail.smtp.host", host);
        this.mProperties.put("mail.smtp.post", post);
        //this.mProperties.put("mail.smtp.auth", true);

        this.mSession = Session.getInstance(mProperties);
        this.mMessage = new MimeMessage(mSession);
        this.mContent = "ok";

    }

    public void setReceiver(String[] receiver) throws MessagingException {
        javax.mail.Address[] addresses = new InternetAddress[receiver.length];
        for(int i=0;i<receiver.length;i++){
            addresses[i] = new InternetAddress(receiver[i]);
        }
        this.mMessage.setRecipients(Message.RecipientType.TO, addresses);
    }

    public void setMessage(String from, String title, String content) throws AddressException, MessagingException {
        this.mMessage.setFrom(new InternetAddress(from));
        this.mMessage.setSubject(title);
        this.mMessage.setText(content);
    }

    public void sendEmail(String host, String account, String pwd) throws MessagingException{
        this.mMessage.setSentDate(new Date());
        this.mMessage.saveChanges();

        Transport transport = mSession.getTransport("smtp");
        transport.connect(host, account, pwd);
        transport.sendMessage(mMessage, mMessage.getAllRecipients());
        transport.close();
    }

}
