/**
 * 
 */
package cs422.callstracker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cs422.callstracker.model.DBAdapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OutgoingCall_Handler extends BroadcastReceiver {

	public String outgoingNumber = null;
	
	public Context globalContext;
	public Intent globalIntent;
	
	public Intent OutgoingCall_Service;
	
	public final String FILENAME = "Data_File";
	public String temp_String = null;
	public byte[] temp_byteArray = null;
	public FileOutputStream fos;
	
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
	public String timeFormat = "%02d:%02d:%02d";
	public String callDuration = null;
	
    public TelephonyManager telephonyManager;
    public int state = 0;
    public PhoneStateListener phoneStateListener;
	
    @Override
    public void onReceive(Context context,Intent intent)
    {
    	outgoingNumber = this.getResultData();		//phone number of present outgoing call
    	
    	globalIntent = intent;
    	globalContext = context;
    	
    	temp_String = outgoingNumber; //"Outgoing Number:" + outgoingNumber;

    	try
    	{
			fos = context.openFileOutput ( FILENAME , context.MODE_PRIVATE );
		}
    	catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    	
    	OutgoingCall_Service = new Intent (context,Telephony_ActiveServices.class);
    	OutgoingCall_Service.putExtra("cs422.callstracker.OutgoingCall_Handle", intent.getExtras());
    	context.startService(new Intent(context,Telephony_ActiveServices.class));
    			
    	DBAdapter model = new DBAdapter(globalContext);
    	
    	cursor = model.getValues(FAVORITE_TABLE);
    	
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
    		//temp_String += ";"+"Favorite Plan:Yes";
    		
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
    	}
    	
    	else
    	{
    		//temp_String += ";"+"Favorite Plan:No";
    	}
    	
    	temp_byteArray = temp_String.getBytes();
    	try 
    	{
			fos.write(temp_byteArray);
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    	
    }

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
    
    public Bundle globalBundle = null;
    public String phoneState = null; 
    public boolean flag = false;
    
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
}