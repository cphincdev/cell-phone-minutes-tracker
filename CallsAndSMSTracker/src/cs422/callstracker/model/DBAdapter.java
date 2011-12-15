package cs422.callstracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	private Context context;
	DatabaseOpenHelper DB;
	SQLiteDatabase db;
	
	public DBAdapter(Context context) {
		this.context = context;
	}
	
	protected SQLiteDatabase openConnection() 
	{
		DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
		return helper.getWritableDatabase();
	}
	
	public boolean isEmpty(String table) 
	{
		db = openConnection();
		Cursor cursor = db.query(table, new String[] {"COUNT(*)"}, null, null, null, null, null);
		boolean result;
		
		cursor.moveToFirst();
		if(cursor.getInt(0) == 0)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		cursor.close();
		db.close();
		return result;
	}
	
	public long insert(String table, ContentValues values) 
	{
		db = openConnection();
		db.delete(table, null, null);
		long result = db.insert(table, null, values);
		db.close();
		return result;
	}
	
	public long delete(String table){
		db = openConnection();
		db.delete(table, null, null);
		db.close();
		return 0;
	}
	
	public Cursor getValues(String table) 
	{
		db = openConnection();
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		cursor.moveToFirst();
		db.close();
		return cursor;
	}
}
