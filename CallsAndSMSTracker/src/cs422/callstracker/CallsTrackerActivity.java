package cs422.callstracker;

import cs422.callstracker.model.DBAdapter;
import cs422.callstracker.model.DatabaseOpenHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CallsTrackerActivity extends Activity {
	public DBAdapter model = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Intent EditPlan = new Intent (getApplicationContext(),Telephony_ActiveServices.class);
	  	EditPlan.putExtra("cs422.callstracker.CallsTrackerActivity", savedInstanceState);
    	this.startService(new Intent(this,Telephony_ActiveServices.class));
        setContentView(R.layout.main);
        model = new DBAdapter(this);
        if(model.isEmpty(DatabaseOpenHelper.TBL_STORAGE))
        {
        	startActivity(new Intent(this, Splash.class));
        }
        else
        {
        	populateMain();
        }
    }
    
    public void btnEditPlan(View view) 
    {
    	startActivity(new Intent(this, EditPlan.class));
    }
    
    public void btnResetPlan(View view) 
    {	
    	// TODO: Reset values here
    	startActivity(new Intent(this, ResetPlan.class));
    }
    
    public void populateMain() 
    {
    	Cursor cursor = model.getValues(DatabaseOpenHelper.TBL_STORAGE);
    	cursor.moveToFirst();
    	TextView phoneNum = (TextView) findViewById(R.id.phone_num);
    	phoneNum.setText(cursor.getString(1));
    	TextView totMins = (TextView) findViewById(R.id.tot_mins);
    	totMins.setText(cursor.getString(13));
    	TextView usedMins = (TextView) findViewById(R.id.used_mins);
    	usedMins.setText(cursor.getString(14));
    	TextView remMins = (TextView) findViewById(R.id.rem_mins);
    	remMins.setText("" + (cursor.getInt(13) - cursor.getInt(14)));
    	TextView totSMS = (TextView) findViewById(R.id.tot_sms);
    	totSMS.setText(cursor.getString(17));
    	TextView usedSMS = (TextView) findViewById(R.id.used_sms);
    	usedSMS.setText(cursor.getString(18));
    	TextView remSMS = (TextView) findViewById(R.id.rem_sms);
    	remSMS.setText("" + (cursor.getInt(17) - cursor.getInt(18)));
    	TextView weekend = (TextView) findViewById(R.id.weekends_free_main);
    	if (cursor.getString(10).equals("1")) 
    	{
    		weekend.setText("Yes");
    	}
    	else 
    	{
    		weekend.setText("No");
    	}
    	TextView FavNum = (TextView) findViewById(R.id.Fav_numbers);
    	if (cursor.getString(20).equals("1")){
    		FavNum.setText("Fab5 Plan");
    	}
    	else if (cursor.getString(21).equals("1")){
    		FavNum.setText("Fab10 Plan");
    	}
    	else 
    	{
    		FavNum.setText("No");
    	}
    	cursor.close();
    }
}