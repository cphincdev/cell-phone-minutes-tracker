package cs422.callstracker.test;

import cs422.callstracker.EditPlan;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class EditPlanTest extends ActivityInstrumentationTestCase2<EditPlan> {
	EditPlan mActivity;
    EditText listview1, listview2, listview3, listview4, listview5, listview6, listview7;
    String string1, string2, string3, string4, string5, string6;
    int minutes, sms;
	  public EditPlanTest() {
	      super("ca422.calltracker", EditPlan.class);
	    }
	  protected void setUp() throws Exception {
	        super.setUp();
	        mActivity = this.getActivity();
	        listview1 = (EditText) mActivity.findViewById(cs422.callstracker.R.id.Total_Minutes);
	        listview2 = (EditText) mActivity.findViewById(cs422.callstracker.R.id.Minutes_Used);
	        listview3 = (EditText) mActivity.findViewById(cs422.callstracker.R.id.Total_SMS);
	        listview4 = (EditText) mActivity.findViewById(cs422.callstracker.R.id.SMS_Used);
	        listview5 = (EditText) mActivity.findViewById(cs422.callstracker.R.id.city);
	        string1 =  listview1.getText().toString();
	        string2 = listview2.getText().toString();
	        string3 = listview3.getText().toString();
	        string4 = listview4.getText().toString();
	        minutes = Integer.parseInt(string1) - Integer.parseInt(string2);
	        sms = Integer.parseInt(string3) - Integer.parseInt(string4);
	        string5 = Integer.toString(minutes);
	        string6 = Integer.toString(sms);
	        
	        
	  }
	     public void testPreconditions() {
	  	      assertNotNull(listview1);
	  	      assertNotNull(listview2);
	  	      assertNotNull(listview3);
	  	      assertNotNull(listview4);
	  	    }
	  	 public void testText() {
	  		assertNotSame(listview1, listview2);
	  		assertNotSame(listview3,listview4);
	  	    }
	        
}
