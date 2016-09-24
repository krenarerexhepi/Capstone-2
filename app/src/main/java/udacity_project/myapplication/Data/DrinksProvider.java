package udacity_project.myapplication.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by betim on 9/16/2016.
 */
public class DrinksProvider extends ContentProvider {

    static final String PROVIDER_NAME = "udacity_project.myapplication.Data.dbDrinks";
    static final String URL = "content://" + PROVIDER_NAME + "/drinks";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Drinks.db";


    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DrinksContract.DrinksEntry.TABLE_NAME + " (" +
                    DrinksContract.DrinksEntry._ID + " INTEGER PRIMARY KEY  autoincrement," +
                    DrinksContract.DrinksEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_NAME_PASWORD + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DrinksContract.DrinksEntry.TABLE_NAME;

    public static final String SQL_CREATE_ENTRIES_DRINK =
            "CREATE TABLE " + DrinksContract.DrinksEntry.TABLE_NAME_DRINK + " (" +
                    DrinksContract.DrinksEntry._ID_DRINK + " INTEGER PRIMARY KEY  autoincrement," +
                    DrinksContract.DrinksEntry.COLUMN_DRINK_NAME + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_DRINK_RECIPE + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_IS_DETOX + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_IMAGE + " BLOB" + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_NAME_USERNAME_DRINK + TEXT_TYPE + COMMA_SEP +
                    DrinksContract.DrinksEntry.COLUMN_IS_FAVORITE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES_DRINK =
            "DROP TABLE IF EXISTS " + DrinksContract.DrinksEntry.TABLE_NAME_DRINK;

    @Override
    public boolean onCreate() {
        return false;
    }

    UserDbHelper mDbHelper = new UserDbHelper(getContext());
    // Gets the data repository in write mode

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor retCursor;
        retCursor = db.query(
                DrinksContract.DrinksEntry.TABLE_NAME,
                projection,
                selection,
                selection == null ? null : selectionArgs,
                null,
                null,
                sortOrder
        );
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
