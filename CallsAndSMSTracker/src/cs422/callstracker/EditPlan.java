package cs422.callstracker;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import cs422.callstracker.model.DBAdapter;
import cs422.callstracker.model.DatabaseOpenHelper;

public class EditPlan extends Activity {
	public DBAdapter model;
	ContentValues values = new ContentValues();
	ContentValues Favourate = new ContentValues();
	public TelephonyManager telepMngr;
	public String PhoneNumber = null;
	boolean flag = false;
	boolean callerId = false, Free_Incomming = false, result = true, passed = true,
			SMS_plan = false;
	String Service, starting_time_hour, starting_time_minute, ending_time_hour,
			ending_time_minute;
	String first, second , three , four , five , six , seven , eight, nine, ten ;
	EditText firsth , secondth , third , forth , fifth , sixth , seventh , eighth , ninth , tenth ;
	public Bundle globalBundle = null;
	
		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.editplan);
			//updateEditTextEditing(false);
			globalBundle = savedInstanceState;
			model = new DBAdapter(this);
			telepMngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			PhoneNumber = telepMngr.getLine1Number();
		
			if(PhoneNumber == null) {
				Toast.makeText(
						this,
						"Phone Number Not Found \n Your device doesnot contain SIM Card or Phone Number. \n This Application is only for phone devices.",
						Toast.LENGTH_LONG).show();
				onStop();
			}
			TextView phoneNumb = (TextView) findViewById(R.id.Phone_Number);
			phoneNumb.setText(PhoneNumber);
		
			setValues();

		
		}
	
		public void btnUpdate(View view) 
		{
			getValues();
			result = Tests();
			passed = favoriteTest();
			if (result && passed) 
			{
				model.insert(DatabaseOpenHelper.TBL_STORAGE, values);
				
				if (((Checkable) findViewById(R.id.Fab10)).isChecked()
					|| ((Checkable) findViewById(R.id.Fab5)).isChecked())
				{
					model.insert(DatabaseOpenHelper.TBL_FAVORITE, Favourate);
				}
				else
				{
					model.delete(DatabaseOpenHelper.TBL_FAVORITE);
				}
			Intent openStarting = new Intent(this, CallsTrackerActivity.class);
			openStarting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
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
			startActivity(openStarting);
			}
			
		  	Intent EditPlan = new Intent (getApplicationContext(),Telephony_ActiveServices.class);
		  	EditPlan.putExtra("cs422.callstracker.EditPlan", globalBundle);
	    	this.startService(new Intent(getApplicationContext(),Telephony_ActiveServices.class));
		}
		private void updateEditTextMinutes(boolean flag)
		{
			if (((Checkable) findViewById(R.id.Caller_Id)).isChecked() || ((Checkable) findViewById(R.id.Incoming)).isChecked()) 
			{
				this.flag = true;
				setEditingInEditText((EditText) findViewById(R.id.Total_Minutes));
				setEditingInEditText((EditText) findViewById (R.id.Minutes_Used));
			}
			else 
			{
				this.flag = flag;
				EditText total_minutes = (EditText) findViewById(R.id.Total_Minutes);
				EditText minutes_used = (EditText) findViewById (R.id.Minutes_Used);
				setEditingInEditText(total_minutes);
				setEditingInEditText(minutes_used);
				if(flag == false)
				{
					total_minutes.setText(null);
					minutes_used.setText(null);
				}
			}
		}
		
		public void updateEditabilityOfMinutes(View view)
		{
			
				flag=!flag;
				updateEditTextMinutes(flag);
			
		}
		
		private void setEditingInEditText(EditText etComment)
		{
	        etComment.setEnabled(flag);
		}
		
		
		
		public void updateEditabilityOfSMS(View view)
		{
			
				flag=!flag;
				updateEditTextSMS(flag);
			
		}
		
		private void updateEditTextSMS(boolean flag) {
			
			if ( ((Checkable) findViewById(R.id.SMS_Plan)).isChecked()) {
			this.flag = true;
			setEditingInEditText((EditText) findViewById(R.id.Total_SMS));
			setEditingInEditText((EditText) findViewById (R.id.SMS_Used));
			}
			else 
			{
				this.flag = false;
				EditText total_sms = (EditText) findViewById(R.id.Total_SMS);
				EditText sms_used = (EditText) findViewById (R.id.SMS_Used);
				setEditingInEditText(total_sms);
				setEditingInEditText(sms_used);
				total_sms.setText(null);
				sms_used.setText(null);
			}
		}
		
		private void updateEditTextFavorite(boolean flag)
		{
			if(((Checkable) findViewById(R.id.Fab5)).isChecked() && ((Checkable) findViewById(R.id.Fab10)).isChecked()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				 builder.setTitle("Clicked Both Fab5 and Fab10");	 
				 builder.setInverseBackgroundForced(true);
				 builder.setMessage("Want to Add Fab10 instead of Fab5")
				        .setCancelable(true)
				        .setPositiveButton("Fab10", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				            	CheckBox set1 = (CheckBox) findViewById(R.id.Fab5);
				            	set1.setChecked(false);
				            	CheckBox set = (CheckBox) findViewById(R.id.Fab10);
				            	set.setChecked(true);
				            	setfab10();
				            	dialog.dismiss();
			                	}
				        })
				       .setNegativeButton("Fab5", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	CheckBox set = (CheckBox) findViewById(R.id.Fab10);
		            	set.setChecked(false);
		            	CheckBox set1 = (CheckBox) findViewById(R.id.Fab5);
		            	set1.setChecked(true);
		            	setfab5();
		            	dialog.cancel();
		            }
		        }).show(); 
			}
			
			if (((Checkable) findViewById(R.id.Fab5)).isChecked()) 
			{
				setfab5();
			}
			else if (((Checkable) findViewById(R.id.Fab10)).isChecked()) 
			{
				setfab10();
			}
			else
			{
				this.flag = false;
				firsth = (EditText) findViewById(R.id.FirstNum);
				secondth = (EditText) findViewById (R.id.SecondNum);
				third = (EditText) findViewById(R.id.ThirdNum);
				forth = (EditText) findViewById (R.id.ForthNum);
				fifth = (EditText) findViewById(R.id.FifthNum);
				sixth = (EditText) findViewById(R.id.SixthNum);
				seventh = (EditText) findViewById (R.id.SeventhNum);
				eighth = (EditText) findViewById(R.id.EighthNum);
				ninth = (EditText) findViewById (R.id.NinethNum);
				tenth = (EditText) findViewById(R.id.TenthNum);
				setEditingInEditText(firsth);
				setEditingInEditText(secondth);
				setEditingInEditText(third);
				setEditingInEditText(forth);
				setEditingInEditText(fifth);
				setEditingInEditText(sixth);
				setEditingInEditText(seventh);
				setEditingInEditText(eighth);
				setEditingInEditText(ninth);
				setEditingInEditText(tenth);
					firsth.setText(null);
					secondth.setText(null);
					third.setText(null);
					forth.setText(null);
					fifth.setText(null);
					sixth.setText(null);
					seventh.setText(null);
					eighth.setText(null);
					ninth.setText(null);
					tenth.setText(null);
			}
		}
		private void setfab5(){
			this.flag = true;
			setEditingInEditText((EditText) findViewById(R.id.FirstNum));
			setEditingInEditText((EditText) findViewById (R.id.SecondNum));
			setEditingInEditText((EditText) findViewById (R.id.ThirdNum));
			setEditingInEditText((EditText) findViewById (R.id.ForthNum));
			setEditingInEditText((EditText) findViewById (R.id.FifthNum));
			this.flag = false;
			setEditingInEditText((EditText) findViewById(R.id.SixthNum));
			setEditingInEditText((EditText) findViewById (R.id.SeventhNum));
			setEditingInEditText((EditText) findViewById (R.id.EighthNum));
			setEditingInEditText((EditText) findViewById (R.id.NinethNum));
			setEditingInEditText((EditText) findViewById (R.id.TenthNum));
		}
		private void setfab10() {
			this.flag = true;
			setEditingInEditText((EditText) findViewById(R.id.FirstNum));
			setEditingInEditText((EditText) findViewById (R.id.SecondNum));
			setEditingInEditText((EditText) findViewById (R.id.ThirdNum));
			setEditingInEditText((EditText) findViewById (R.id.ForthNum));
			setEditingInEditText((EditText) findViewById (R.id.FifthNum));
			setEditingInEditText((EditText) findViewById(R.id.SixthNum));
			setEditingInEditText((EditText) findViewById (R.id.SeventhNum));
			setEditingInEditText((EditText) findViewById (R.id.EighthNum));
			setEditingInEditText((EditText) findViewById (R.id.NinethNum));
			setEditingInEditText((EditText) findViewById (R.id.TenthNum));
			
		}

		public void updateEditabilityOfFavorite(View view)
		{
			
				flag=!flag;
				updateEditTextFavorite(flag);
			
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
				values.put("FAV_TEN", "0");
			}
			else if (((Checkable) findViewById(R.id.Fab10)).isChecked())
			{
				values.put("FAV_TEN", "1");
				values.put("FAV_FIVE", "0");
			}
			else
			{
				values.put("FAV_FIVE", "0");
				values.put("FAV_TEN", "0");
			}
			if (((Checkable) findViewById(R.id.Fab5)).isChecked()) 
			{
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
		public boolean setValues() 
		{
			// TODO Auto-generated method stub
			if (!model.isEmpty(DatabaseOpenHelper.TBL_STORAGE)) 
			{
				Cursor cursor = model.getValues(DatabaseOpenHelper.TBL_STORAGE);
				cursor.moveToFirst();

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
				{	
					weekends.setChecked(true);
				}
				else
				{
					weekends.setChecked(false);
				}	
				CheckBox CallerId = (CheckBox) findViewById(R.id.Caller_Id);
				if (cursor.getString(11).equals("1"))
				{
					CallerId.setChecked(true);
				}
				else
				{
					CallerId.setChecked(false);
				}
				CheckBox Incomming = (CheckBox) findViewById(R.id.Incoming);
				if (cursor.getString(12).equals("1"))
				{
					Incomming.setChecked(true);
					updateEditTextMinutes(true);
				}
				else
				{	
					Incomming.setChecked(false);
					updateEditTextMinutes(false);
				}	
				EditText Tot_Mins = (EditText) findViewById(R.id.Total_Minutes);
				Tot_Mins.setText(cursor.getString(13));
				EditText usedMins = (EditText) findViewById(R.id.Minutes_Used);
				usedMins.setText(cursor.getString(14));
				CheckBox SMS_Plan = (CheckBox) findViewById(R.id.SMS_Plan);
				if (cursor.getString(15).equals("1"))
				{
					SMS_Plan.setChecked(true);
					updateEditTextSMS(true);
				}
				else
				{
					SMS_Plan.setChecked(false);
					updateEditTextSMS(false);
				}
				CheckBox Free_SMS = (CheckBox) findViewById(R.id.Free_Incomming_SMS);
				if (cursor.getString(16).equals("1"))
				{
					Free_SMS.setChecked(true);
				}
				else
				{
					Free_SMS.setChecked(false);
				}
				EditText totSMS = (EditText) findViewById(R.id.Total_SMS);
				totSMS.setText(cursor.getString(17));
				EditText usedSMS = (EditText) findViewById(R.id.SMS_Used);
				usedSMS.setText(cursor.getString(18));
				EditText City = (EditText) findViewById(R.id.city);
				City.setText(cursor.getString(19));
				CheckBox Fav_5 = (CheckBox) findViewById(R.id.Fab5);
				if (cursor.getString(20).equals("1"))
				{
					Fav_5.setChecked(true);
					updateEditTextFavorite(true);
				}
				else
				{
					Fav_5.setChecked(false);
					updateEditTextFavorite(false);
				}
				CheckBox Fav_10 = (CheckBox) findViewById(R.id.Fab10);
				if (cursor.getString(21).equals("1"))
				{
					Fav_10.setChecked(true);
					updateEditTextFavorite(true);
				}
				else
				{
					Fav_10.setChecked(false);
					updateEditTextFavorite(false);
				}
				cursor.close();
				if (!model.isEmpty(DatabaseOpenHelper.TBL_FAVORITE)) 
				{
					Cursor cursor1 = model
						.getValues(DatabaseOpenHelper.TBL_FAVORITE);
					cursor1.moveToFirst();
					if (cursor1.getString(1).equals("5"))
					{
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
					} 
					else if (cursor1.getString(1).equals("10")) 
					{
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
					}
					cursor1.close();
				}
				return true;
			}
			else {
				updateEditTextMinutes(false);
				updateEditTextSMS(false);
				updateEditTextFavorite(false);
				return false;
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
		return true;
		}
		protected boolean favoriteTest () {
			if (((Checkable) findViewById(R.id.Fab5)).isChecked()) {
			firsth = (EditText) findViewById(R.id.FirstNum);
			first = firsth.getText().toString();
			if (first.length() == 0) {
				
			}
			else if(first.length() != 10 || first.contains("+") || first.contains("-") || first.contains("*") || first.contains("#"))
				{

					Toast.makeText(
						this,
						"Cannot Proces First Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			secondth = (EditText) findViewById(R.id.SecondNum);
			second = secondth.getText().toString();
				if (second.length() == 0) {
					
				}
				else if(second.length() != 10 || second.contains("+") || second.contains("-") || second.contains("*") || second.contains("#"))
				{

					Toast.makeText(
						this,
						"Cannot Proces Second Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			third = (EditText) findViewById(R.id.ThirdNum);
			three = third.getText().toString();
			if (three.length() == 0) {
				
			}
			else if(three.length() != 10 || three.contains("+") || three.contains("-") || three.contains("*") || three.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Third Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			forth = (EditText) findViewById(R.id.ForthNum);
			four = forth.getText().toString();
			if (four.length() == 0) {
				
			}
			else if(four.length() != 10 || four.contains("+") || four.contains("-") || four.contains("*") || four.contains("#"))
				{
						Toast.makeText(
								this,
								"Cannot Proces Forth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
								Toast.LENGTH_LONG).show();
						return false; 
				}
			fifth = (EditText) findViewById(R.id.FifthNum);
			five = fifth.getText().toString();
			if (five.length() == 0) {
				
			}
			else if(five.length() != 10 || five.contains("+") || five.contains("-") || five.contains("*") || five.contains("#"))
				{
						Toast.makeText(
									this,
									"Cannot Proces Fifth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
									Toast.LENGTH_LONG).show();
						return false; 
				}
			}	
			if (((Checkable) findViewById(R.id.Fab10)).isChecked() && !((Checkable) findViewById(R.id.Fab5)).isChecked()) {
			firsth = (EditText) findViewById(R.id.FirstNum);
			first = firsth.getText().toString();
			if (first.length() == 0) {
				
			}
			else if(first.length() != 10 || first.contains("+") || first.contains("-") || first.contains("*") || first.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces First Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			secondth = (EditText) findViewById(R.id.SecondNum);
			second = secondth.getText().toString();
			if (second.length() == 0) {
				
			}	
			else if(second.length() != 10 || second.contains("+") || second.contains("-") || second.contains("*") || second.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Second Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			third = (EditText) findViewById(R.id.ThirdNum);
			three = third.getText().toString();
			if (three.length() == 0) {
				
			}	
			else if(three.length() != 10 || three.contains("+") || three.contains("-") || three.contains("*") || three.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Third Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			forth = (EditText) findViewById(R.id.ForthNum);
			four = forth.getText().toString();
			if (four.length() == 0) {
				
			}
			else if(four.length() != 10 || four.contains("+") || four.contains("-") || four.contains("*") || four.contains("#"))
				{
						Toast.makeText(
								this,
								"Cannot Proces Forth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
								Toast.LENGTH_LONG).show();
						return false; 
				}
			fifth = (EditText) findViewById(R.id.FifthNum);
			five = fifth.getText().toString();
			if (five.length() == 0) {
				
			}
			else if(five.length() != 10 || five.contains("+") || five.contains("-") || five.contains("*") || five.contains("#"))
				{
						Toast.makeText(
									this,
									"Cannot Proces Fifth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
									Toast.LENGTH_LONG).show();
						return false; 
				}
			sixth= (EditText) findViewById(R.id.SixthNum);
			six = sixth.getText().toString();
			if (six.length() == 0) {
				
			}
			else if(six.length() != 10 || six.contains("+") || six.contains("-") || six.contains("*") || six.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Sixth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			seventh = (EditText) findViewById(R.id.SeventhNum);
			seven = seventh.getText().toString();
			if (seven.length() == 0) {
				
			}
			else if(seven.length() != 10 || seven.contains("+") || seven.contains("-") || seven.contains("*") || seven.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Seventh Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			eighth = (EditText) findViewById(R.id.EighthNum);
			eight = eighth.getText().toString();
			if (eight.length() == 0) {
				
			}
			else if(eight.length() != 10 || eight.contains("+") || eight.contains("-") || eight.contains("*") || eight.contains("#"))
				{
					Toast.makeText(
						this,
						"Cannot Proces Eighth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits.  \n If had less numbers add: \n 1234567890 instead to the rest.",
						Toast.LENGTH_LONG).show();
					return false; 
				}
			ninth = (EditText) findViewById(R.id.NinethNum);
			nine = forth.getText().toString();
			if (nine.length() == 0) {
				
			}
			else if(nine.length() != 10 || nine.contains("+") || nine.contains("-") || nine.contains("*") || nine.contains("#"))
				{
						Toast.makeText(
								this,
								"Cannot Proces Ninth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
								Toast.LENGTH_LONG).show();
						return false; 
				}
			tenth = (EditText) findViewById(R.id.TenthNum);
			ten = tenth.getText().toString();
			if (ten.length() == 0) {
				
			}
			else if(ten.length() != 10 || ten.contains("+") || ten.contains("-") || ten.contains("*") || ten.contains("#"))
				{
						Toast.makeText(
									this,
									"Cannot Proces Tenth Number \n Your Number Must be 10 Digits only. \n contains nothing but digits. \n If had less numbers add: \n 1234567890 instead to the rest.",
									Toast.LENGTH_LONG).show();
						return false; 
				}
			}	
		return true;
		}
}
