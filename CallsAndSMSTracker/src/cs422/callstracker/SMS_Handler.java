/**
 * 
 */
package cs422.callstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * @author Rohan
 *
 */
public class SMS_Handler extends BroadcastReceiver {
	
	public Context globalContext_sms = null;
	public Intent globalIntent_sms = null;
	public int resultValue = 0;
	public TelephonyManager telephonyManager_sms = null;
	public SmsManager smsManager = null;
	public SmsMessage smsMessage_Received = null;
	public SmsMessage smsMessages[] = null;
	public String SMS_SENT_ACTION = "SMS_SENT_ACTION";
	public String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	public int simState = 5;
	public Bundle bundle = null;
	public static final String smsQuery = "@echo";
	public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	public Object[] pdus = null;
	
	public String msg = null;
	public String to = null;
	public String smsReceiverNumber = null;
	public String smsReceivedBody = null;
	
	public void onReceive (Context context, Intent intent)
	{
			globalIntent_sms = intent;
			globalContext_sms = context;
			
			if( intent.getAction().equals(SMS_RECEIVED) )
			{
				pdus = (Object[]) bundle.get("pdus");
				smsMessages = new SmsMessage[pdus.length];
				for(int i=0 ; i<smsMessages.length ; i++)
				{
					smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					
				}
				for (SmsMessage msg : smsMessages)
				{
					smsReceivedBody = msg.getDisplayMessageBody();
					smsReceiverNumber = msg.getOriginatingAddress();		//SMS receiver's number is fetched and stored
				}
				
			  	Intent SMS_Handler = new Intent (globalContext_sms,Telephony_ActiveServices.class);
			  	SMS_Handler.putExtra("cs422.callstracker.SMS_Handler", intent.getExtras());
		    	context.startService(new Intent(globalContext_sms,Telephony_ActiveServices.class));
			}
			
	}

}
