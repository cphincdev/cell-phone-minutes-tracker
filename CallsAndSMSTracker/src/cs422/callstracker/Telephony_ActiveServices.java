package cs422.callstracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;
import cs422.callstracker.model.DBAdapter;

public class Telephony_ActiveServices extends Service {
	
	public Context globalContext;
	public Intent globalIntent;
	
	public final String FILENAME = "Data_File";
	public String temp_String = null;
	public byte[] temp_byteArray = null;
	public FileInputStream fis;
	
	//public DBAdapter model;
	public Cursor cursor;
	public final String FAVORITE_TABLE = "FAVORITE_NUMBER";
	
	public String eveningTime = null;
	public boolean weekendFree = false;
	public boolean favoriteNumbers_Plan = false;

	public boolean favorite_5 = false;
	public boolean favorite_10 = false;
	
	public String favoriteNumber1 = null;
	public String favoriteNumber2 = null;
	public String favoriteNumber3 = null;
	public String favoriteNumber4 = null;
	public String favoriteNumber5 = null;
	public String favoriteNumber6 = null;
	public String favoriteNumber7 = null;
	public String favoriteNumber8 = null;
	public String favoriteNumber9 = null;
	public String favoriteNumber10 = null;
	
	public int seconds = 0;
	public int minutes = 0;
	public int hours = 0;
	public String timeFormat = "%02d-%02d:%02d";
	public String callDuration = null;
	
    public TelephonyManager telephonyManager;
    public int state = 0;
    public PhoneStateListener phoneStateListener;
	
    public String outgoingNumber = null;
	public InputStream f;
	public char[] temp_char=null;
	
	public Bundle globalBundle = null;
    public boolean flag = false;
	
	public Bundle phoneBundle;
	public Context globalContext_call;
	public Intent globalIntent_call;
	public String phoneState = null;
	public String incomingNumber = null;
	public final int NOTIFICATION_ID = 1 ;
	
	public String serviceName = null;
	public String service_state = null;
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
	public void onCreate(){
		
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		globalIntent = intent;
		globalContext = getApplicationContext();
		
		if(globalIntent.hasExtra("cs422.callstracker.OutgoingCall_Handle"));
		{
			try {
				Outgoing_CallHandler();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//IBind
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		if ((flags & START_FLAG_RETRY ) ==0 ){
			try {
				Outgoing_CallHandler();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else{
			//todo
		}
		//return Service.START_NOT_STICKY;	//causes to end/stop the service when onStartCommand is completed
		return Service.START_STICKY; 		//causes to explicitly stop and start the service from the activity
	}

	
	public void Outgoing_CallHandler() throws IOException
	{
		fis = getApplicationContext().openFileInput(FILENAME);

		f = new FileInputStream(FILENAME);
		
		int size = f.available();  
		
		for (int i=0; i < size; i++) 
		{ 
			temp_char[i]=((char) f.read()); 
		} 
		
		outgoingNumber = temp_char.toString();
				
		
		
		DBAdapter model = new DBAdapter(this);

		
    	Cursor cursor = model.getValues(FAVORITE_TABLE);
		cursor.moveToFirst();
		
    	if( cursor.getString(0).toString().equals("True") )
    	{
    		favoriteNumbers_Plan = true;
    	}
    	else
    	{
    		favoriteNumbers_Plan = false;
    	}
    	
    	if (favoriteNumbers_Plan)
    	{
    		temp_String += ";"+"Favorite Plan:Yes";
    		
	    	if( cursor.getString(1).equals("5") )
	    	{
	    		favorite_5 = true;
	    	}
	    	else// if (cursor.getString(1).equals("10"))
	    	{
	    		favorite_10 = true;
	    	}

	    	favoriteNumber1 = cursor.getString(2).toString();
	    	favoriteNumber2 = cursor.getString(3).toString();
	    	favoriteNumber3 = cursor.getString(4).toString();
	    	favoriteNumber4 = cursor.getString(5).toString();
	    	favoriteNumber5 = cursor.getString(6).toString();
	    	
	    	if(favorite_10)
	    	{
		    	favoriteNumber6 = cursor.getString(7).toString();
		    	favoriteNumber7 = cursor.getString(8).toString();
		    	favoriteNumber8 = cursor.getString(9).toString();
		    	favoriteNumber9 = cursor.getString(10).toString();
		    	favoriteNumber10 = cursor.getString(11).toString();
		    	
		    	if( !(outgoingNumber.equals(favoriteNumber1)) || 
		    			outgoingNumber.equals(favoriteNumber2) ||
		    			outgoingNumber.equals(favoriteNumber3) ||
		    			outgoingNumber.equals(favoriteNumber4) ||
		    			outgoingNumber.equals(favoriteNumber5) ||
		    			outgoingNumber.equals(favoriteNumber6) ||
		    			outgoingNumber.equals(favoriteNumber7) ||
		    			outgoingNumber.equals(favoriteNumber8) ||
		    			outgoingNumber.equals(favoriteNumber9) ||
		    			outgoingNumber.equals(favoriteNumber10) )
		    	{
		    		//temp_String += ";"+"Is in Favorite 10:Yes";
		    	}
	    	}
	    	
	    	else if( !(outgoingNumber.equals(favoriteNumber1)) || 
		    			outgoingNumber.equals(favoriteNumber2) ||
		    			outgoingNumber.equals(favoriteNumber3) ||
		    			outgoingNumber.equals(favoriteNumber4) ||
		    			outgoingNumber.equals(favoriteNumber5) )
	    	{
	    		//temp_String += ";"+"Is in Favorite 5:Yes";
	    	}
	    	
	    	cursor.close();
	    	
	    	startTimer();
	    	
	    		    }
	  
    	
    	Cursor cursor1 = model.getValues("TBL_STORAGE");
		cursor1.moveToFirst();
    	
    	String usedMins = cursor1.getString(14);
    	
    	int usedMins_i = Integer.parseInt(usedMins);
    	

    	StringTokenizer st = new StringTokenizer(callDuration);
    	 
        StringTokenizer st2 = new StringTokenizer(callDuration,"-");	
 
        String minutes = null;
        
    	while(st2.hasMoreElements()){
    		minutes=st2.nextElement().toString();
    	}

	    int minutes_i = Integer.parseInt(minutes);	
	    	
	    int updated_mins;
	    
	    updated_mins = usedMins_i-minutes_i;
	    	
	    String newMins = Integer.toString(updated_mins);

	    
		contentV.put("USED_MINS", newMins);
    	
    	model.insert("TBL_STORAGE", contentV);
    	cursor1.close();
	    	
    	}
public ContentValues contentV = null;
    public void startTimer()
    {
    	while(callEnded())
    	{
    		seconds += 1;
        	if(seconds > 60)
        	{
        		seconds = 0;
        		minutes += 1;		
        		if(minutes >60 )
        		{
        			minutes = 0;
        			hours += 1;
        		}
        	}
    	}
    	callDuration = String.format(timeFormat, hours,minutes,seconds);
    }   	
    
  
    
    public boolean callEnded()
    {
    	phoneStateListener = new PhoneStateListener()
    	{
    		public void onCallStateChanged (int state,String phoneNumber) {}
    	};
    	
    	PhoneStateListener callStateListener = new PhoneStateListener()
    	{
    		public void onCallStateChanged (int state, String phoneNumber)
    		{
    			state = telephonyManager.getCallState();
    			globalBundle = globalIntent.getExtras();
    			if(globalBundle !=  null)
    			{
    				phoneState = globalIntent.getStringExtra(TelephonyManager.EXTRA_STATE);
    				if (state == telephonyManager.CALL_STATE_OFFHOOK)
    				{
    					if(state == telephonyManager.CALL_STATE_IDLE)
    					{
    						flag = true;
    					}
    				}
    				else if (state ==  telephonyManager.CALL_STATE_RINGING || state == telephonyManager.CALL_STATE_IDLE)
    				{
    					flag = false;
    				}
    			}
    		}
    	};
    	telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    	return flag;
    }
    public void Incomming_Calls(String result_phone_number)
	{
		Call_Tracker();
		
		PhoneStateListener callStateListener = new PhoneStateListener()
    	{
    		public void onCallStateChanged (int state, String phoneNumber)
    		{
    			state = telephonyManager.getCallState();
    			globalBundle = globalIntent.getExtras();
    			if(globalBundle !=  null)
    			{
    				phoneState = globalIntent.getStringExtra(TelephonyManager.EXTRA_STATE);
    				if(state == telephonyManager.CALL_STATE_IDLE)
    				{
    					if (state == telephonyManager.CALL_STATE_OFFHOOK)
    					{
    						flag = true;
    					}
    				}
    				else if (state ==  telephonyManager.CALL_STATE_RINGING || state == telephonyManager.CALL_STATE_IDLE)
    				{
    					flag = false;
    				}
    			}
    		}
    	};
    	telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		
		
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
	
	

