package cs422.callstracker;

import cs422.callstracker.model.DBAdapter;
import cs422.callstracker.model.DatabaseOpenHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPlan extends Activity {
	DBAdapter model;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editplan);
        
}
	public void btnUpdate(View view) {
		ContentValues values = new ContentValues();

		values.put("PHONE_NUM", "000-000-0000");
		DatePicker cycleDate = (DatePicker) findViewById(R.id.BillCycle);
		values.put("BILL_CYCLE", cycleDate.getYear() + "-" + cycleDate.getMonth() + "-" + cycleDate.getDayOfMonth());
		if(((Checkable) findViewById(R.id.Caller_Id)).isChecked())
		values.put("CALLER_ID", "1");
		else
		values.put("CALLER_ID", "0");
		if( ((Checkable) findViewById(R.id.Incoming)).isChecked())
		values.put("FREE_INCOMMING", "1");
		else
		values.put("FREE_INCOMMING", "0");
		values.put("PLAN_MINS", ((EditText) findViewById(R.id.Total_Minutes)).getText().toString());
		values.put("USED_MINS", ((EditText) findViewById(R.id.Minutes_Used)).getText().toString());
		if(((Checkable) findViewById(R.id.SMS_Plan)).isChecked())
		values.put("SMS_PLAN", "1");
		else 
		values.put("SMS_PLAN", "0");
		if( ((Checkable) findViewById(R.id.Free_Incomming_SMS)).isChecked())
		values.put("SMS_INCOMMING", "1");
		else
		values.put("SMS_INCOMMING", "0");
		values.put("TOTAL_SMS", ((EditText) findViewById(R.id.Total_SMS)).getText().toString());
		values.put("USED_SMS", ((EditText) findViewById(R.id.SMS_Used)).getText().toString());
		values.put("CITY", ((EditText) findViewById(R.id.city)).getText().toString());
		if (((Checkable) findViewById(R.id.Fab5)).isChecked())
		values.put("FAV_FIVE", "1");
		else
		values.put("FAV_FIVE", "0");
		if (((Checkable) findViewById(R.id.Fab10)).isChecked())
		values.put("FAV_TEN", "1");
		else 
		values.put("FAV_TEN", "0");
		model.insert(DatabaseOpenHelper.TBL_STORAGE, values);
		if(((Checkable) findViewById(R.id.Fab5)).isChecked()){
			ContentValues Favourate = new ContentValues();
			Favourate.put("FAV_CHECK","True");
			Favourate.put("FAV_NO","5");
			Favourate.put("FAV_NUMBS_1", ((EditText)findViewById(R.id.FirstNum)).getText().toString());
			Favourate.put("FAV_NUMBS_2", ((EditText)findViewById(R.id.SecondNum)).getText().toString());
			Favourate.put("FAV_NUMBS_3", ((EditText)findViewById(R.id.ThirdNum)).getText().toString());
			Favourate.put("FAV_NUMBS_4", ((EditText)findViewById(R.id.ForthNum)).getText().toString());
			Favourate.put("FAV_NUMBS_5", ((EditText)findViewById(R.id.FifthNum)).getText().toString());
			model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
		}
		else if(((Checkable) findViewById(R.id.Fab10)).isChecked()){
			ContentValues Favourate = new ContentValues();
			Favourate.put("FAV_CHECK","True");
			Favourate.put("FAV_NO","10");
			Favourate.put("FAV_NUMBS_1", ((EditText)findViewById(R.id.FirstNum)).getText().toString());
			Favourate.put("FAV_NUMBS_2", ((EditText)findViewById(R.id.SecondNum)).getText().toString());
			Favourate.put("FAV_NUMBS_3", ((EditText)findViewById(R.id.ThirdNum)).getText().toString());
			Favourate.put("FAV_NUMBS_4", ((EditText)findViewById(R.id.ForthNum)).getText().toString());
			Favourate.put("FAV_NUMBS_5", ((EditText)findViewById(R.id.FifthNum)).getText().toString());
			Favourate.put("FAV_NUMBS_6", ((EditText)findViewById(R.id.SixthNum)).getText().toString());
			Favourate.put("FAV_NUMBS_7", ((EditText)findViewById(R.id.SeventhNum)).getText().toString());
			Favourate.put("FAV_NUMBS_8", ((EditText)findViewById(R.id.EighthNum)).getText().toString());
			Favourate.put("FAV_NUMBS_9", ((EditText)findViewById(R.id.NinethNum)).getText().toString());
			Favourate.put("FAV_NUMBS_10", ((EditText)findViewById(R.id.TenthNum)).getText().toString());
			model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
		}
		else{
		}
		Intent openStarting = new Intent(this, CallsTrackerActivity.class);
		openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(openStarting);
	}
	
	public void btnCancel(View view) {
		Intent openStarting = new Intent(this, CallsTrackerActivity.class);
		openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(openStarting);
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
