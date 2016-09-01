package udacity_project.myapplication.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krenare Rexhepi on 7/10/2016.
 */
public class UserDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Drinks.db";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DrinksContract.DrinksEntry.TABLE_NAME + " (" +
                   DrinksContract.DrinksEntry._ID + " INTEGER PRIMARY KEY  autoincrement," +
                   DrinksContract.DrinksEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                   DrinksContract.DrinksEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                   DrinksContract.DrinksEntry.COLUMN_NAME_PASWORD + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DrinksContract.DrinksEntry.TABLE_NAME;


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}