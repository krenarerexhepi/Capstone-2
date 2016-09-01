package udacity_project.myapplication.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krenare Rexhepi on 8/20/2016.
 */
public class DrinksDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Drinks.db";

    public DrinksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_Drink =
            "CREATE TABLE " + DrinksContract.DrinksEntry.TABLE_NAME_DRINK + " (" +
                    DrinksContract.DrinksEntry._ID_DRINK + " INTEGER PRIMARY KEY  autoincrement," +
                    DrinksContract.DrinksEntry.COLUMN_DRINK_NAME + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_DRINK_RECIPE + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_IS_DETOX + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_IMAGE + " BLOB" +
                    " )";

    private static final String SQL_DELETE_ENTRIES_DRINK =
            "DROP TABLE IF EXISTS " + DrinksContract.DrinksEntry.TABLE_NAME_DRINK;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Drink);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_DRINK);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}