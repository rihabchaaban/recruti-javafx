package models;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SMSSender {


    public void  send_SMS (String username) {


        String ACCOUNT_SID = "ACab569d0751cfecf76b4ef50f3ce78bda";
        String AUTH_TOKEN = "bbc6c50781e89399620d83b2e57cacee";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21658099715"),
                        new com.twilio.type.PhoneNumber("+18582510960"),
                        "Recruti :"+username+"your signed up to our app successfully").create();

        System.out.println(message.getSid());


    }
}
