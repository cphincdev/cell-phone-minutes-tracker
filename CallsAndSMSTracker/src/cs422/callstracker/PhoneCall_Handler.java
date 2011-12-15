/**
 * 
 */
package cs422.callstracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Rohan
 *
 */
public class PhoneCall_Handler extends BroadcastReceiver{
	
	public Bundle phoneBundle;
	public Context globalContext_call;
	public Intent globalIntent_call;
	public String phoneState = null;
	public String incomingNumber = null;
	public final int NOTIFICATION_ID = 1 ;
	
	public String serviceName = null;
	public String service_state = null;
	public static TelephonyManager telephonyManager; 
	public int phoneType = 1; //1 = PHONE_TYPE_GSM taking it as GSM from initial stage
			//telephonyManager.PHONE_TYPE_NONE;	//for tablets or device with no Radio 	
	public String deviceID = null; 
	public String device_OSVersion = null; 

	public String networkCountry = null;
	public String networkOperatorID = null;
	public String networkOperatorName = null;
	public int simState = 5; //5 = SIM_STATE_READY taking initially as SIM is present
	public String SIMStateMsg = null;
	public String displayMsg = null;
	
	public GsmCellLocation gsmLocation;
	public CdmaCellLocation cdmaLocation;
	
	public boolean isitGSM = false;
	public boolean isitCDMA = false;
	
	public boolean callForwarding_State = false;
	
	@Override
	public void onReceive(Context context,Intent intent)
	{
		String incoming_number = null;
		String phone_state = null;
		String netwrk_country = null;
		String gsm_cell_location = null;
		String cdma_cell_location = null;
		int sim_state = 5;
		String service_State = null;
		boolean call_ForwardingState = false;
		
		boolean GSM = true;
		boolean CDMA = true;
		
		globalIntent_call = intent;
		globalContext_call = context;
		
		Incomming_Calls(getResultData());
		
		Call_Tracker();
		
		sim_state = simState;
		
		if (sim_state == TelephonyManager.SIM_STATE_READY)
		{
			call_ForwardingState = callForwarding_State;
			displayMsg = " SIM card is present ";
			
			if ( call_ForwardingState == true )
			{
				incoming_number = incomingNumber;
				phone_state = phoneState;
				netwrk_country = networkCountry;
				GSM = isitGSM;
				CDMA = isitCDMA;
				if(GSM == true)
				{
					gsm_cell_location = gsmLocation.toString();
					displayMsg = displayMsg + "Incoming Number: " + incoming_number +
								"State of Phone: " + phone_state + "Phone Type: GSM" +
								"Cell Location: " + gsm_cell_location;
				}
				else if (CDMA == true)
				{
					cdma_cell_location = cdmaLocation.toString();
					displayMsg = displayMsg + " Incoming Number: " + incoming_number +
								" State of Phone: " + phone_state + " Phone Type: CDMA " +
								" Cell Location: " + gsm_cell_location;
				}
			}
			else 
			{
				displayMsg = "All the calls are currently forwarded to other number";
			}
			 notificationOnTop(context,displayMsg);
			 Toast_msg(context,displayMsg);
		}
		
		else if (sim_state == TelephonyManager.SIM_STATE_ABSENT)
		{
			displayMsg = SIMStateMsg;
			 notificationOnTop(context,displayMsg);
			 Toast_msg(context,displayMsg);
		}
		else if (sim_state == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 sim_state == TelephonyManager.SIM_STATE_NETWORK_LOCKED ||
				 sim_state == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 sim_state == TelephonyManager.SIM_STATE_PUK_REQUIRED)
		{
			displayMsg = SIMStateMsg;
			 notificationOnTop(context,displayMsg);
			 Toast_msg(context,displayMsg);
		}
		deviceID = telephonyManager.getDeviceId(); //this uses permission READ_PHONE_STATE
		device_OSVersion = telephonyManager.getDeviceSoftwareVersion();
		
	  	Intent PhoneCall_Handler = new Intent (context,Telephony_ActiveServices.class);
    	PhoneCall_Handler.putExtra("cs422.callstracker.PhoneCall_Handler", intent.getExtras());
    	context.startService(new Intent(context,Telephony_ActiveServices.class));
		
	}

	
	public void Incomming_Calls(String result_phone_number)
	{
		
	}
	
	
	/*
	 * FOR INCOMMING CALLS ---- ONLY
	 * Tracks Cellular changes if occured dynamically for 
	 * 		SIM card state - whether present or not or locked
	 * 		then accordingly fetched following		
	 * 			Phone State - whether RINGING or IN CALL (OFF HOOK) or IDLE
	 *			Cellular Type - whether GSM or CDMA
	 *			Location of GSM or Location of CDMA phone
	 *			State of Cellular Service - whether available or not
	 * 
	 */
	public void Call_Tracker(){
		//need to define own PhoneStateListener, hence interfaces is being called
		PhoneStateListener phoneStateListener = new PhoneStateListener() 
		{
			public void onCallForwardingIndicatorChanged(boolean cfi){}
			public void onCallStateChanged (int state, String incomingnumber){}
			public void onCellLocationChanged (CellLocation cellLocation){}
			public void onServiceStateChanged (ServiceState serviceState){}
		};
		
		simState =  simState;
		
		if (simState == TelephonyManager.SIM_STATE_READY)
		{
			serviceName=globalContext_call.TELEPHONY_SERVICE;
			telephonyManager = (TelephonyManager)globalContext_call.getSystemService(serviceName);
			 phoneType(globalContext_call);
			phoneType =  phoneType;
		
			
			networkCountry =  networkCountry;
			networkOperatorID =  networkOperatorID;
			networkOperatorName =  networkOperatorName;
			
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR|
									PhoneStateListener.LISTEN_CALL_STATE | 
									PhoneStateListener.LISTEN_CELL_LOCATION |
									PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
									PhoneStateListener.LISTEN_SERVICE_STATE |
									PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
			
			//Cellphone call state changed
			PhoneStateListener callStateListener = new PhoneStateListener ()
			{
				public void onCallStateListener (int state,String incomingnumber)
				{
					phoneBundle=globalIntent_call.getExtras();
					
					if(phoneBundle != null)
					{
						phoneState = globalIntent_call.getStringExtra(telephonyManager.EXTRA_STATE);
						if(telephonyManager.CALL_STATE_RINGING == state)
						{
							incomingNumber = incomingnumber;		
							// notificationOnTop(globalContext_call,incomingNumber);
							//displayMsg = "Incoming Number is :";
							// Toast_msg(globalContext_call,displayMsg+incomingNumber);
							Log.i("Broadcast Recieved", "incomming call"+incomingNumber);
						}
						else if(telephonyManager.CALL_STATE_IDLE == state)
						{
							// notificationOnTop(globalContext_call,displayMsg);
							// Toast_msg(globalContext_call, displayMsg);
							Log.i("Broadcast Recieved", "phone is idle right now");
						}
						else if(telephonyManager.CALL_STATE_OFFHOOK == state)
						{
							//displayMsg = "Phone is currently in call";
							// notificationOnTop(globalContext_call,displayMsg);
							// Toast_msg(globalContext_call, displayMsg);
							Log.i("Broadcast Recieved", "dialling/active/hold");
						}
					}	
				}
			};
			telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
			
			isitGSM =  GSM;
			isitCDMA =  CDMA;
			
			//tracking changes in Users Cell Location constantly
			PhoneStateListener cellLocationListener = new PhoneStateListener ()
			{
					public void onCellLocationListener (CellLocation cellLocation)
					{
						if(isitGSM == true)
						{
							gsmLocation = (GsmCellLocation) cellLocation;
						}
						else if(isitCDMA == true)
						{
							cdmaLocation = (CdmaCellLocation) cellLocation;
						}
					}
			};
	
			telephonyManager.listen(cellLocationListener, PhoneStateListener.LISTEN_CELL_LOCATION);
			
			PhoneStateListener serviceStateListener = new PhoneStateListener() 
			{
				public void onServiceStateChanged(ServiceState serviceState)
				{
					if(serviceState.getState() == ServiceState.STATE_IN_SERVICE)
					{
						service_state = serviceState.getOperatorAlphaLong();
						// Toast_msg(globalContext_call, displayMsg);
						// notificationOnTop(globalContext_call, displayMsg);
					}
					else if (serviceState.getState() == ServiceState.STATE_OUT_OF_SERVICE)
					{
						service_state = serviceState.getOperatorAlphaLong() + " Phone is out of service.";
						// Toast_msg(globalContext_call, displayMsg);
						// notificationOnTop(globalContext_call, displayMsg);
					}
					else if (serviceState.getState() == ServiceState.STATE_POWER_OFF)
					{
						service_state = serviceState.getOperatorAlphaLong() + " Phone is on Air-Plane Mode";
						 Toast_msg(globalContext_call, displayMsg);
						 notificationOnTop(globalContext_call, displayMsg);
					}
					else if (serviceState.getState() == ServiceState.STATE_EMERGENCY_ONLY)
					{
						service_state = serviceState.getOperatorAlphaLong() + "Phone is under EMERGENCY ONLY STATE";
						 Toast_msg(globalContext_call, displayMsg);
						 notificationOnTop(globalContext_call, displayMsg);
					}
				}
			};
			
			telephonyManager.listen(serviceStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
			
			PhoneStateListener forwardcallStateChanged = new PhoneStateListener() 
			{
				public void onCallForwardingIndicatorChanged (boolean cfi)
				{
					callForwarding_State = cfi;
				}
			};
			
			telephonyManager.listen(forwardcallStateChanged, PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR);
		}

		else if (simState == TelephonyManager.SIM_STATE_ABSENT)
		{
			SIMStateMsg = "SIM card is not inserted";
			// notificationOnTop(globalContext_call,displayMsg);
			// Toast_msg(globalContext_call,displayMsg);
		}
		else if (simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_NETWORK_LOCKED ||
				 simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_PUK_REQUIRED)
		{
			SIMStateMsg = "something is wrong with your SIM card";
			// notificationOnTop(globalContext_call,displayMsg);
			// Toast_msg(globalContext_call,displayMsg);
		}
	}
	public boolean GSM = true;
	public boolean CDMA = false;
	
	public void phoneType(Context context)
	//very doubtful about this code..if problem occurs it might be due to getSystemService
	{

		serviceName=context.TELEPHONY_SERVICE;
		telephonyManager = (TelephonyManager)context.getSystemService(serviceName);
		phoneType = telephonyManager.getPhoneType();
		simState = telephonyManager.getSimState();
		
		if(phoneType == TelephonyManager.PHONE_TYPE_CDMA)
		{
			CDMA = true;
			displayMsg="Phone is on CDMA Band";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (phoneType == TelephonyManager.PHONE_TYPE_GSM)
		{
			GSM = true;
			displayMsg="Phone is on GSM Band";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (phoneType == TelephonyManager.PHONE_TYPE_NONE)
		{
			GSM = false;
			CDMA = false;
			displayMsg="Phone Radio not present. This app is not meant for devices apart from phones";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);			
		}
		else if (simState == TelephonyManager.SIM_STATE_ABSENT)
		{
			displayMsg="SIM card is not inserted";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_NETWORK_LOCKED ||
				 simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_PUK_REQUIRED)
		{
			displayMsg = "something is wrong with your SIM card";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		
		deviceID = telephonyManager.getDeviceId(); //this uses permission READ_PHONE_STATE
		device_OSVersion = telephonyManager.getDeviceSoftwareVersion(); 
		
	}
	
	public void phone_Country(Context context)
	{
		networkCountry = telephonyManager.getNetworkCountryIso();
		networkOperatorID = telephonyManager.getNetworkOperator();
		networkOperatorName = telephonyManager.getNetworkOperatorName();
	}
	
	public void Toast_msg(Context context,String msg){
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}
	
	public void notificationOnTop(Context context,String string)
	{
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(android.R.drawable.sym_action_call,"CallSMSTracker Notification",System.currentTimeMillis());
		
		PendingIntent notification_Content_PendingIntent = PendingIntent.getActivity(context,0,new Intent(context,PhoneCall_Handler.class),0);
		
		notification.setLatestEventInfo(context, "Call And SMS Tracker", string ,notification_Content_PendingIntent);
		
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
}