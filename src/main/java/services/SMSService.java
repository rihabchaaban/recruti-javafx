package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    // Initialize Twilio API credentials
    public static final String ACCOUNT_SID = "ACe25bcb7da31fdb2601c19575f7e18268";
    public static final String AUTH_TOKEN = "2fb3be0c83f139e842f63879dcfb1bbf";

    public static void sendSMS(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Specify the phone number you want to send the SMS from
        String from = "+13343393115";

        // Send the SMS
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(from),
                        body)
                .create();

        // Print the message SID to the console
        System.out.println("Message SID: " + message.getSid());
    }
}
