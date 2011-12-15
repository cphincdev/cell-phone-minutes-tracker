/**
 * 
 */
package cs422.callstracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * @author Rohan
 *
 */
public class SMS_Receiver extends BroadcastReceiver {
	
	public Context globalContext_sms = null;
	public Intent globalIntent_sms = null;
	public int resultValue = 0;
	public TelephonyManager telephonyManager_sms = null;
	public SmsManager smsManager = null;
	public SmsMessage smsMessage = null;
	public String SMS_SENT_ACTION = "SMS_SENT_ACTION";
	public String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	public int simState = 5;
	
	
	public void onReceive (Context context, Intent intent)
	{
		globalContext_sms = context;
		globalIntent_sms = intent;
		
		simState = telephonyManager_sms.getSimState();
		
		if(simState == TelephonyManager.SIM_STATE_READY)
		{
			resultValue = getResultCode();
			
		}
		
	}

}
