package cs422.callstracker.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
        
        public static final String DB_NAME = "CallsTrackerDB";
        public static final int DB_VERSION = 1;
        Cursor cur1,cur2;
        public SQLiteDatabase db;
        
        public static final String TBL_STORAGE = "STORAGE_VALUE";
        public static final String TBL_FAVORITE = "FAVORITE_NUMBER";

        public String getPath(){
                return db.getPath();
        }
        
        
        public DatabaseOpenHelper(Context context) {
                super(context, DB_NAME, null, DB_VERSION);
        }
        
        public void createTables(SQLiteDatabase db) {
                db.execSQL(
                        "CREATE TABLE " + TBL_STORAGE + " (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "PHONE_NUM TEXT NOT NULL, " +
                                "SERVICE_PROVIDER TEXT, " +
                                "BILL_CYCLE_YEAR TEXT NOT NULL, " +
                                "BILL_CYCLE_MONTH TEXT NOT NULL, " +
                                "BILL_CYCLE_DAY TEXT NOT NULL, " +
                                "EVENING_START_HOUR TEXT, " +
                                "EVENING_START_MINUTE TEXT, " +
                                "EVENING_END_HOUR TEXT, " +
                                "EVENING_END_MINUTE TEXT, " +
                                "WEEKENDS_FREE TEXT, " +
                                "CALLER_ID TEXT NOT NULL, " +
                                "FREE_INCOMMING TEXT NOT NULL, " +
                                "PLAN_MINS TEXT NOT NULL, " +
                                "USED_MINS TEXT NOT NULL, " +
                                "SMS_PLAN TEXT NOT NULL, " +
                                "SMS_INCOMMING TEXT NOT NULL, " +
                                "TOTAL_SMS TEXT NOT NULL, " +
                                "USED_SMS TEXT NOT NULL," +
                                "CITY TEXT NOT NULL, " +
                                "FAV_FIVE TEXT NOT NULL, " +
                                "FAV_TEN TEXT NOT NULL" +
                        ")"
                );
                
                db.execSQL(
                        "CREATE TABLE " + TBL_FAVORITE + " (" +
                                "FAV_CHECK TEXT NOT NULL, " +
                                "FAV_NO TEXT NOT NULL, " +
                                "FAV_NUMBS_1 TEXT, " +
                                "FAV_NUMBS_2 TEXT, " +
                                "FAV_NUMBS_3 TEXT, " +
                                "FAV_NUMBS_4 TEXT, " +
                                "FAV_NUMBS_5 TEXT, " +
                                "FAV_NUMBS_6 TEXT, " +
                                "FAV_NUMBS_7 TEXT, " +
                                "FAV_NUMBS_8 TEXT, " +
                                "FAV_NUMBS_9 TEXT, " +
                                "FAV_NUMBS_10 TEXT" +
                        ")"
                );
        }
        
        @SuppressWarnings({ "static-access", "unused" })
        public boolean checkDBExists(){
                SQLiteDatabase tempDB_tbl_storage=null;
                SQLiteDatabase tempDB_tbl_favorite=null;
                boolean flag=true;
                try{
                        tempDB_tbl_storage=db.openDatabase(TBL_STORAGE, null, SQLiteDatabase.OPEN_READONLY);
                        tempDB_tbl_favorite=db.openDatabase(TBL_FAVORITE, null, SQLiteDatabase.OPEN_READONLY);
                        try{
                                cur1= tempDB_tbl_storage.rawQuery("SELECT * FROM "+TBL_STORAGE+";", null);
                                cur2= tempDB_tbl_favorite.rawQuery("SELECT * FROM "+TBL_FAVORITE+";", null);
                                if(cur1==null)
                                        flag = false;
                                else
                                        flag = true;
                                        
                                
                        }
                        catch(SQLiteException e){
                                
                        }
                        
                }
                catch(SQLiteException e){
                }
                cur1.close();
                cur2.close();
                return flag; 
        }
        
        
        
        
        public void dropTables(SQLiteDatabase db) {
                db.execSQL("DROP TABLE " + TBL_STORAGE);
                db.execSQL("DROP TABLE " + TBL_FAVORITE);
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
                createTables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                dropTables(db);
                createTables(db);
        }
}