package cs422.callstracker;

import cs422.callstracker.model.DBAdapter;
import cs422.callstracker.model.DatabaseOpenHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditPlan extends Activity {
	DBAdapter model;
	public String PhoneNumber=null;
	public TelephonyManager telepMngr;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editplan);
		model = new DBAdapter(this);
		telepMngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
    	PhoneNumber = telepMngr.getLine1Number();
    	
    	TextView phoneNumb =(TextView)findViewById(R.id.Phone_Number);
    	phoneNumb.setText(PhoneNumber);
		
    	if (!model.isEmpty(DatabaseOpenHelper.TBL_STORAGE)) {
			Cursor cursor = model.getValues(DatabaseOpenHelper.TBL_STORAGE);
			cursor.moveToFirst();

			//TextView phone = (TextView) findViewById(R.id.Phone_Number);
			//phone.setText(cursor.getString(1));
			EditText service = (EditText) findViewById(R.id.Service_Provider);
			service.setText(cursor.getString(2));
			DatePicker date = (DatePicker) findViewById(R.id.BillCycle);
			int dateString1 = Integer.parseInt(cursor.getString(3));
			int dateString2 = Integer.parseInt(cursor.getString(4));
			int dateString3 = Integer.parseInt(cursor.getString(5));
			date.updateDate(dateString1, dateString2, dateString3);
			TimePicker startingTime = (TimePicker) findViewById(R.id.StartingTime);
			int TimeString1 = Integer.parseInt(cursor.getString(6));
			int TimeString2 = Integer.parseInt(cursor.getString(7));
			startingTime.setCurrentHour(TimeString1);
			startingTime.setCurrentMinute(TimeString2);
			TimePicker endingTime = (TimePicker) findViewById(R.id.EndingTime);
			int TimeString3 = Integer.parseInt(cursor.getString(8));
			int TimeString4 = Integer.parseInt(cursor.getString(9));
			endingTime.setCurrentHour(TimeString3);
			endingTime.setCurrentMinute(TimeString4);
			CheckBox weekends = (CheckBox) findViewById(R.id.WeekendsFree);
			if (cursor.getString(10).equals("1"))
				weekends.setChecked(true);
			else
				weekends.setChecked(false);
			CheckBox CallerId = (CheckBox) findViewById(R.id.Caller_Id);	
			if (cursor.getString(11).equals("1"))
				CallerId.setChecked(true);
			else
				CallerId.setChecked(false);
			CheckBox Incomming = (CheckBox) findViewById(R.id.Incoming);
			if (cursor.getString(12).equals("1"))
				Incomming.setChecked(true);
			else
				Incomming.setChecked(false);
			EditText Tot_Mins = (EditText) findViewById(R.id.Total_Minutes);
			Tot_Mins.setText(cursor.getString(13));
			EditText usedMins = (EditText) findViewById(R.id.Minutes_Used);
			usedMins.setText(cursor.getString(14));
			CheckBox SMS_Plan = (CheckBox) findViewById(R.id.SMS_Plan);
			if (cursor.getString(15).equals("1"))
				SMS_Plan.setChecked(true);
			else
				SMS_Plan.setChecked(false);
			CheckBox Free_SMS = (CheckBox) findViewById(R.id.Free_Incomming_SMS);
			if (cursor.getString(16).equals("1"))
				Free_SMS.setChecked(true);
			else
				Free_SMS.setChecked(false);
			EditText totSMS = (EditText) findViewById(R.id.Total_SMS);
			totSMS.setText(cursor.getString(17));
			EditText usedSMS = (EditText) findViewById(R.id.SMS_Used);
			usedSMS.setText(cursor.getString(18));
			EditText City = (EditText) findViewById(R.id.city);
			City.setText(cursor.getString(19));
			CheckBox Fav_5 = (CheckBox) findViewById(R.id.Fab5);
			if (cursor.getString(20).equals("1"))
				Fav_5.setChecked(true);
			else
				Fav_5.setChecked(false);
			CheckBox Fav_10 = (CheckBox) findViewById(R.id.Fab10);
			if (cursor.getString(21).equals("1"))
				Fav_10.setChecked(true);
			else
				Fav_10.setChecked(false);
			cursor.close();
			if (!model.isEmpty(DatabaseOpenHelper.TBL_FAVORITE)) {
				Cursor cursor1 = model.getValues(DatabaseOpenHelper.TBL_FAVORITE);
				cursor1.moveToFirst();
				if (cursor1.getString(1).equals("5")) {
					EditText Num1 = (EditText) findViewById(R.id.FirstNum);
					Num1.setText(cursor1.getString(2));
					EditText Num2 = (EditText) findViewById(R.id.SecondNum);
					Num2.setText(cursor1.getString(3));
					EditText Num3 = (EditText) findViewById(R.id.ThirdNum);
					Num3.setText(cursor1.getString(4));
					EditText Num4 = (EditText) findViewById(R.id.ForthNum);
					Num4.setText(cursor1.getString(5));
					EditText Num5 = (EditText) findViewById(R.id.FifthNum);
					Num5.setText(cursor1.getString(6));
				} else if (cursor1.getString(1).equals("10")) {
					EditText Num1 = (EditText) findViewById(R.id.FirstNum);
					Num1.setText(cursor1.getString(2));
					EditText Num2 = (EditText) findViewById(R.id.SecondNum);
					Num2.setText(cursor1.getString(3));
					EditText Num3 = (EditText) findViewById(R.id.ThirdNum);
					Num3.setText(cursor1.getString(4));
					EditText Num4 = (EditText) findViewById(R.id.ForthNum);
					Num4.setText(cursor1.getString(5));
					EditText Num5 = (EditText) findViewById(R.id.FifthNum);
					Num5.setText(cursor1.getString(6));
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setText(cursor1.getString(7));
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setText(cursor1.getString(8));
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setText(cursor1.getString(8));
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setText(cursor1.getString(10));
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setText(cursor1.getString(11));
					cursor1.close();
				} else {
					EditText Num1 = (EditText) findViewById(R.id.FirstNum);
					Num1.setEnabled(false);
					EditText Num2 = (EditText) findViewById(R.id.SecondNum);
					Num2.setEnabled(false);
					EditText Num3 = (EditText) findViewById(R.id.ThirdNum);
					Num3.setEnabled(false);
					EditText Num4 = (EditText) findViewById(R.id.ForthNum);
					Num4.setEnabled(false);
					EditText Num5 = (EditText) findViewById(R.id.FifthNum);
					Num5.setEnabled(false);
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setEnabled(false);
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setEnabled(false);
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setEnabled(false);
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setEnabled(false);
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setEnabled(false);
				}
			}
		}

		//thread.start();	
	}

	public void btnUpdate(View view) {
		ContentValues values = new ContentValues();

		values.put("PHONE_NUM", PhoneNumber);
		values.put("SERVICE_PROVIDER",((EditText) findViewById(R.id.Service_Provider)).getText()
						.toString());
		DatePicker cycleDate = (DatePicker) findViewById(R.id.BillCycle);
		values.put("BILL_CYCLE_YEAR", "" + cycleDate.getYear());
		values.put("BILL_CYCLE_MONTH", "" + cycleDate.getMonth());
		values.put("BILL_CYCLE_DAY", "" + cycleDate.getDayOfMonth());
		values.put("EVENING_START_HOUR",
				(((TimePicker) findViewById(R.id.StartingTime))
						.getCurrentHour().toString()));
		values.put("EVENING_START_MINUTE",
				(((TimePicker) findViewById(R.id.StartingTime))
						.getCurrentMinute().toString()));
		values.put("EVENING_END_HOUR",
				(((TimePicker) findViewById(R.id.EndingTime)).getCurrentHour()
						.toString()));
		values.put("EVENING_END_MINUTE",
				(((TimePicker) findViewById(R.id.EndingTime))
						.getCurrentMinute().toString()));
		if (((Checkable) findViewById(R.id.WeekendsFree)).isChecked())
			values.put("WEEKENDS_FREE", "1");
		else
			values.put("WEEKENDS_FREE", "0");
		if (((Checkable) findViewById(R.id.Caller_Id)).isChecked())
			values.put("CALLER_ID", "1");
		else
			values.put("CALLER_ID", "0");
		if (((Checkable) findViewById(R.id.Incoming)).isChecked())
			values.put("FREE_INCOMMING", "1");
		else
			values.put("FREE_INCOMMING", "0");
		values.put("PLAN_MINS", ((EditText) findViewById(R.id.Total_Minutes))
				.getText().toString());
		values.put("USED_MINS", ((EditText) findViewById(R.id.Minutes_Used))
				.getText().toString());
		if (((Checkable) findViewById(R.id.SMS_Plan)).isChecked())
			values.put("SMS_PLAN", "1");
		else
			values.put("SMS_PLAN", "0");
		if (((Checkable) findViewById(R.id.Free_Incomming_SMS)).isChecked())
			values.put("SMS_INCOMMING", "1");
		else
			values.put("SMS_INCOMMING", "0");
		values.put("TOTAL_SMS", ((EditText) findViewById(R.id.Total_SMS))
				.getText().toString());
		values.put("USED_SMS", ((EditText) findViewById(R.id.SMS_Used))
				.getText().toString());
		values.put("CITY", ((EditText) findViewById(R.id.city)).getText()
				.toString());
		if (((Checkable) findViewById(R.id.Fab5)).isChecked())
			values.put("FAV_FIVE", "1");
		else
			values.put("FAV_FIVE", "0");
		if (((Checkable) findViewById(R.id.Fab10)).isChecked())
			values.put("FAV_TEN", "1");
		else
			values.put("FAV_TEN", "0");
		model.insert(DatabaseOpenHelper.TBL_STORAGE, values);
		if (((Checkable) findViewById(R.id.Fab5)).isChecked()) {
			ContentValues Favourate = new ContentValues();
			Favourate.put("FAV_CHECK", "True");
			Favourate.put("FAV_NO", "5");
			Favourate.put("FAV_NUMBS_1",
					((EditText) findViewById(R.id.FirstNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_2",
					((EditText) findViewById(R.id.SecondNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_3",
					((EditText) findViewById(R.id.ThirdNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_4",
					((EditText) findViewById(R.id.ForthNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_5",
					((EditText) findViewById(R.id.FifthNum)).getText()
							.toString());
			model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
		} else if (((Checkable) findViewById(R.id.Fab10)).isChecked()) {
			ContentValues Favourate = new ContentValues();
			Favourate.put("FAV_CHECK", "True");
			Favourate.put("FAV_NO", "10");
			Favourate.put("FAV_NUMBS_1",
					((EditText) findViewById(R.id.FirstNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_2",
					((EditText) findViewById(R.id.SecondNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_3",
					((EditText) findViewById(R.id.ThirdNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_4",
					((EditText) findViewById(R.id.ForthNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_5",
					((EditText) findViewById(R.id.FifthNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_6",
					((EditText) findViewById(R.id.SixthNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_7",
					((EditText) findViewById(R.id.SeventhNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_8",
					((EditText) findViewById(R.id.EighthNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_9",
					((EditText) findViewById(R.id.NinethNum)).getText()
							.toString());
			Favourate.put("FAV_NUMBS_10",
					((EditText) findViewById(R.id.TenthNum)).getText()
							.toString());
			model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
		} else {
		}
		Intent openStarting = new Intent(this, CallsTrackerActivity.class);
		openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(openStarting);
	}

	public void btnCancel(View view) {
		Intent openStarting = new Intent(this, CallsTrackerActivity.class);
		openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (model.isEmpty(DatabaseOpenHelper.TBL_STORAGE)) {
			Toast.makeText(this, "NO data found\nPlease Enter your data",
					Toast.LENGTH_LONG).show();
		} else
			startActivity(openStarting);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	/*Thread thread = new Thread()
	{
	    @Override
	    public void run() {
	        while(true) {
				if (((Checkable) findViewById(R.id.Fab5)).isChecked()) {
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setEnabled(false);
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setEnabled(false);
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setEnabled(false);
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setEnabled(false);
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setEnabled(false);
				} else if (((Checkable) findViewById(R.id.Fab10))
						.isChecked()){
					
				} else {
					handler.handleMessage(null);
				}
			}
	    }
	};	
	  private Handler handler = new Handler() {
          public void handleMessage(String msg) {
        	  if (msg.equals("5")) {
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setEnabled(false);
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setEnabled(false);
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setEnabled(false);
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setEnabled(false);
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setEnabled(false);
				} else if (msg.equals("10")){
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setEnabled(true);
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setEnabled(true);
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setEnabled(true);
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setEnabled(true);
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setEnabled(true);
				} else {
					EditText Num1 = (EditText) findViewById(R.id.FirstNum);
					Num1.setEnabled(false);
					EditText Num2 = (EditText) findViewById(R.id.SecondNum);
					Num2.setEnabled(false);
					EditText Num3 = (EditText) findViewById(R.id.ThirdNum);
					Num3.setEnabled(false);
					EditText Num4 = (EditText) findViewById(R.id.ForthNum);
					Num4.setEnabled(false);
					EditText Num5 = (EditText) findViewById(R.id.FifthNum);
					Num5.setEnabled(false);
					EditText Num6 = (EditText) findViewById(R.id.SixthNum);
					Num6.setEnabled(false);
					EditText Num7 = (EditText) findViewById(R.id.SeventhNum);
					Num7.setEnabled(false);
					EditText Num8 = (EditText) findViewById(R.id.EighthNum);
					Num8.setEnabled(false);
					EditText Num9 = (EditText) findViewById(R.id.NinethNum);
					Num9.setEnabled(false);
					EditText Num10 = (EditText) findViewById(R.id.TenthNum);
					Num10.setEnabled(false);
				}
              
          }
	  };*/
}
