package cs422.callstracker;

import cs422.callstracker.model.DBAdapter;
import cs422.callstracker.model.DatabaseOpenHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ResetPlan extends Activity {
	DBAdapter model;
	ContentValues values = new ContentValues();
	ContentValues Favourate = new ContentValues();
	public TelephonyManager telepMngr;
	public String PhoneNumber = null;
	boolean callerId = false, Free_Incomming = false, result = true,
			SMS_plan = false;
	String Service, starting_time_hour, starting_time_minute, ending_time_hour,
			ending_time_minute;

		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.editplan);
			model = new DBAdapter(this);
			telepMngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			PhoneNumber = telepMngr.getLine1Number();

			TextView phoneNumb = (TextView) findViewById(R.id.Phone_Number);
			phoneNumb.setText(PhoneNumber);
		
		}
	
		public void btnUpdate(View view) 
		{
			getValues();
			result = Tests();
			if (result) 
			{
				model.insert(DatabaseOpenHelper.TBL_STORAGE, values);
				
				if (((Checkable) findViewById(R.id.Fab10)).isChecked()
					|| ((Checkable) findViewById(R.id.Fab5)).isChecked())
				{
					model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
				}
			Intent openStarting = new Intent(this, CallsTrackerActivity.class);
			openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(openStarting);
			}
		}
	
		public void btnCancel(View view) 
		{
			Intent openStarting = new Intent(this, CallsTrackerActivity.class);
			openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				if (model.isEmpty(DatabaseOpenHelper.TBL_STORAGE)) 
				{
						Toast.makeText(this, "NO data found\nPlease Enter your data",
								Toast.LENGTH_LONG).show();
				} 
				else
				{
						startActivity(openStarting);
				}		
		}

		protected void onPause() 
		{
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}

		protected void onStop() 
		{
			finish();
			super.onStop();
		}
	
		private void getValues() 
		{
			// TODO Auto-generated method stub
			values.put("PHONE_NUM", PhoneNumber);
			Service = ((EditText) findViewById(R.id.Service_Provider)).getText()
				.toString();
			values.put("SERVICE_PROVIDER", Service);
			DatePicker cycleDate = (DatePicker) findViewById(R.id.BillCycle);
			values.put("BILL_CYCLE_YEAR", "" + cycleDate.getYear());
			values.put("BILL_CYCLE_MONTH", "" + cycleDate.getMonth());
			values.put("BILL_CYCLE_DAY", "" + cycleDate.getDayOfMonth());
			starting_time_hour = (((TimePicker) findViewById(R.id.StartingTime))
				.getCurrentHour().toString());
			values.put("EVENING_START_HOUR", starting_time_hour);
			starting_time_minute = (((TimePicker) findViewById(R.id.StartingTime))
				.getCurrentMinute().toString());
			values.put("EVENING_START_MINUTE", starting_time_minute);
			ending_time_hour = (((TimePicker) findViewById(R.id.EndingTime))
				.getCurrentHour().toString());
			values.put("EVENING_END_HOUR", ending_time_hour);
			ending_time_minute = (((TimePicker) findViewById(R.id.EndingTime))
				.getCurrentMinute().toString());
			values.put("EVENING_END_MINUTE", ending_time_minute);
			if (((Checkable) findViewById(R.id.WeekendsFree)).isChecked())
			{
				values.put("WEEKENDS_FREE", "1");
			}
			else
			{
				values.put("WEEKENDS_FREE", "0");
			}
			if (((Checkable) findViewById(R.id.Caller_Id)).isChecked()) 
			{
				callerId = true;
				values.put("CALLER_ID", "1");
			} 
			else 
			{
				callerId = false;
				values.put("CALLER_ID", "0");
			}
			if (((Checkable) findViewById(R.id.Incoming)).isChecked()) 
			{
				Free_Incomming = true;
				values.put("FREE_INCOMMING", "1");
			}
			else
			{
				Free_Incomming = false;
				values.put("FREE_INCOMMING", "0");
			}
			values.put("PLAN_MINS", ((EditText) findViewById(R.id.Total_Minutes))
				.getText().toString());
			values.put("USED_MINS", ((EditText) findViewById(R.id.Minutes_Used))
				.getText().toString());
			if (((Checkable) findViewById(R.id.SMS_Plan)).isChecked()) 
			{
				SMS_plan = true;
				values.put("SMS_PLAN", "1");
			} 
			else
			{
				SMS_plan = false;
				values.put("SMS_PLAN", "0");
			}
			if (((Checkable) findViewById(R.id.Free_Incomming_SMS)).isChecked())
			{
				values.put("SMS_INCOMMING", "1");
			}
			else
			{
				values.put("SMS_INCOMMING", "0");
			}	
			values.put("TOTAL_SMS", ((EditText) findViewById(R.id.Total_SMS))
				.getText().toString());
			values.put("USED_SMS", ((EditText) findViewById(R.id.SMS_Used))
				.getText().toString());
			values.put("CITY", ((EditText) findViewById(R.id.city)).getText()
				.toString());
			if (((Checkable) findViewById(R.id.Fab5)).isChecked())
			{
				values.put("FAV_FIVE", "1");
			}
			else
			{
				values.put("FAV_FIVE", "0");
			}
			if (((Checkable) findViewById(R.id.Fab10)).isChecked())
			{
				values.put("FAV_TEN", "1");
			}
			else
			{
				values.put("FAV_TEN", "0");
			}
			if (((Checkable) findViewById(R.id.Fab5)).isChecked()) 
			{
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
			}
			else if (((Checkable) findViewById(R.id.Fab10)).isChecked()) 
			{
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

			} 
		}
		
		protected boolean Tests() 
		{
			if (((TimePicker) findViewById(R.id.StartingTime))
				.getCurrentHour()
				.toString()
				.equals(((TimePicker) findViewById(R.id.EndingTime))
						.getCurrentHour().toString())) 
			{
				Toast.makeText(
						this,
						"Evening Free Calling starting and ending time cannot be same \n Please correct it to continue",
						Toast.LENGTH_LONG).show();
				return false;
			}
			if (!callerId && !Free_Incomming && !SMS_plan) 
			{
				Toast.makeText(
						this,
						"No Calls Or SMS Plan entered \n Please Enter Plan information to continue",
						Toast.LENGTH_LONG).show();
				return false;
			}
			if (callerId || Free_Incomming) 
			{
				if (((EditText) findViewById(R.id.Total_Minutes)).getText()
					.toString().equals("")) 
				{
					Toast.makeText(
						this,
						"Total Minutes In Plan not Entered \n Please Enter Total Minutes To Continue",
						Toast.LENGTH_LONG).show();
					return false;
				}
			}	
			if (SMS_plan) 
			{
				if (((EditText) findViewById(R.id.Total_SMS)).getText().toString()
					.equals("")) 
				{
					Toast.makeText(
							this,
							"Total SMS In Plan not Entered \n Please Enter Total SMS To Continue",
							Toast.LENGTH_LONG).show();
					return false;

				}
			}
			if (!callerId
					&& !Free_Incomming
					&& !((EditText) findViewById(R.id.Total_Minutes)).getText()
						.toString().equals("")) 
			{
				Toast.makeText(
						this,
						"Cannot Process Calls Tracker \n Please Delete Total Minutes \n Calls cannot be tracked without Caller Id or Free Incomming in our plan",
						Toast.LENGTH_LONG).show();
				return false;
			}
			if (!SMS_plan
					&& !((EditText) findViewById(R.id.Total_SMS)).getText()
						.toString().equals("")) 
			{
				Toast.makeText(
						this,
						"SMS plan doesnot exists \n Please Delete Total SMS or add SMS plan",
						Toast.LENGTH_LONG).show();
				return false;
			}
			if (((Checkable) findViewById(R.id.Fab5)).isChecked()) 
			{
				if (!((EditText) findViewById(R.id.SixthNum)).getText().toString()
					.equals("")) 
				{
					Toast.makeText(
							this,
							"Cannot Proces Favourate Five Numbers \n You checked Five Favorate Number plan \n Change to 10 Favorate plan or delete extra numbers",
							Toast.LENGTH_LONG).show();
					return false;
				}
			}
			if (Service.equals("")) 
			{
				Toast.makeText(this,
						"No Service Provider Entered \n May cause Problem latter",
						Toast.LENGTH_LONG).show();
			}
			if (!callerId && !Free_Incomming) 
			{
				Toast.makeText(
					this,
					"Cannot Proces Calls Minutes Tracker \n Your Plan doesnot Consists Caller ID or Free Incomming ",
					Toast.LENGTH_LONG).show();
			}
			if (!SMS_plan) 
			{
				Toast.makeText(
						this,
						"No SMS Plan exists all SMS Sent or recieved may costs you money. If not free",
						Toast.LENGTH_LONG).show();
			}
		return true;
		}
}
