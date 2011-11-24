package cs422.callstacker.test;

import java.io.File;
import java.net.URI;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelFileDescriptor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import cs422.callstracker.model.DatabaseOpenHelper;

public class DatabaseOpenHelperTest extends ActivityInstrumentationTestCase2 {
	
public DatabaseOpenHelper DBOpenHelper;
	//public cs422.callstracker.model.DBAdapter db;
	public SQLiteDatabase db;
	public static final String DBName = "CallsTrackerDB";
	public static String path = null;
	public Context context;
	public File DBFile = null;
	public boolean fileDeleted = false;
	
	public DatabaseOpenHelperTest(String name) {
		super("cs422.callstracker.model",DatabaseOpenHelper.class);
		setName(name);
	}

	public void setUp() throws Exception{
		super.setUp();
	}

	public void tearDown() throws Exception{
		super.tearDown();
		
		if(DBOpenHelper!=null){
			path=DBOpenHelper.db.openDatabase(DBName, null, SQLiteDatabase.OPEN_READWRITE).getPath();
			Log.d("database test","cleaning up DB"+path);
			if(path!=null){
				DBFile = new File(path);
				fileDeleted =  DBFile.delete();
				Log.d("database test", "DATABASE deleted:"+fileDeleted);
			}
		}
	}
	/*
	public URI uri = URI.create("file://DATA/data/cs422.callstracker/databases/"+DBName+"");
	public File db_file;
	
	public ParcelFileDescriptor db_location()throws Exception{
		db_file=new File(uri);
		ParcelFileDescriptor parcel = ParcelFileDescriptor.open(db_file, ParcelFileDescriptor.MODE_READ_ONLY);
		return parcel;
	}
	
	public 
	*/
	public void testOnCreateSQLiteDatabase(){
		Log.d("database creatiion test", "checking DB whether created or not");
		DBOpenHelper = new DatabaseOpenHelper(this.getActivity());
		db = DBOpenHelper.getReadableDatabase();
		//assertNull(db);
		//assertNotNull(db);
		//assertTrue(DBOpenHelper.checkDBExists());
		//assertEquals(DBOpenHelper.db,db);
	}
}