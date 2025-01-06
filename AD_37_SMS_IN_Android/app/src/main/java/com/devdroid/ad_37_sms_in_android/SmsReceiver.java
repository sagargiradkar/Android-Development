package com.devdroid.ad_37_sms_in_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

       Bundle bundle = intent.getExtras();

       Object[] smsObj = (Object[])bundle.get("pdus");

       for(Object sms : smsObj){
           SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])sms);
           String sender = smsMessage.getOriginatingAddress();
           String message = smsMessage.getMessageBody();
           Log.d("SmsReceiver", "Sender: " + sender + ", Message: " + message);

           //SmsManager.getDefault().sendTextMessage(sender, null, "Message Received", null, null);

           SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage("+918788179375", null, "Message Received", null, null);

       }
    }
}
