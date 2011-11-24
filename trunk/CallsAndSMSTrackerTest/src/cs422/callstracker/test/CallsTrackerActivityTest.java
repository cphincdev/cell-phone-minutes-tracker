package cs422.callstracker.test;

import cs422.callstracker.CallsTrackerActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class CallsTrackerActivityTest extends ActivityInstrumentationTestCase2<CallsTrackerActivity> {
	CallsTrackerActivity mActivity;
    public TextView total_minutes, Used_minutes, Total_sms, Used_sms, remaining_minutes, remaining_sms;
    public String T_mins, U_mins, T_sms, U_sms, R_mins, R_sms, R_M, R_S;
    public int rem_mins, rem_sms;
	  public CallsTrackerActivityTest() {
	      super("cs422.callstracker", CallsTrackerActivity.class);
	    }
	  protected void setUp() throws Exception {
	        super.setUp();
	        mActivity = this.getActivity();
	        total_minutes = (TextView) mActivity.findViewById(cs422.callstracker.R.id.tot_mins);
	        Log.d("total_minutes", "total minutes"+total_minutes.getText().toString());
	        Used_minutes = (TextView) mActivity.findViewById(cs422.callstracker.R.id.used_mins);
	        Total_sms = (TextView) mActivity.findViewById(cs422.callstracker.R.id.tot_sms);
	        Used_sms = (TextView) mActivity.findViewById(cs422.callstracker.R.id.used_sms);
	        Log.d("total_minutes", "total minutes"+Used_sms.getText().toString());
	        remaining_minutes = (TextView) mActivity.findViewById(cs422.callstracker.R.id.rem_mins);
	        Log.d("total_minutes", "total minutes"+remaining_minutes.getText().toString());
	        remaining_sms = (TextView) mActivity.findViewById(cs422.callstracker.R.id.rem_sms);
	        Log.d("total_minutes", "total minutes"+remaining_sms.getText().toString());
	        T_mins =  total_minutes.getText().toString();
	        U_mins = Used_minutes.getText().toString();
	        T_sms = Total_sms.getText().toString();
	        U_sms = Used_sms.getText().toString();
	        R_mins = remaining_minutes.getText().toString();
	        R_sms = remaining_sms.getText().toString();
	        Log.d("remaining Minutes", R_mins);
	        Log.d("remaining sms", R_sms);
	        rem_mins = Integer.parseInt(T_mins) - Integer.parseInt(U_mins); 
	        
	        rem_sms = Integer.parseInt(T_sms) - Integer.parseInt(U_sms);
	       
	        R_M = Integer.toString(rem_mins);
	        Log.d("remaining Minutes", R_M);
	        R_S = Integer.toString(rem_sms);
	        Log.d("remaining sms", R_S);
	        
	  }
	     public void testPreconditions() {
	  	      assertNotNull(total_minutes);
	  	      assertNotNull(Used_minutes);
	  	      assertNotNull(Total_sms);
	  	      assertNotNull(Used_sms);
	  	      assertNotNull(remaining_minutes);
	  	      assertNotNull(remaining_sms);
	  	    }
	  	 public void testText() {
	  		assertNotSame(T_mins, U_mins);
	  		assertNotSame(T_sms,U_sms);
	  		assertEquals(R_M,R_mins);
	  		assertEquals(R_S, R_sms);
	  	    }
	        
}
