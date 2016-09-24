package udacity_project.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import udacity_project.myapplication.Data.DrinksContract;
import udacity_project.myapplication.Data.UserDbHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    UserDbHelper mDbHelper = new UserDbHelper(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.register_in_button);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData(v);
            }
        });
    }

    public void SaveData(View view) {
        if (username.getText().toString().equals(null) || username.getText().toString().equals("") || username.getText().toString().equals(" ")) {
            Snackbar.make(view, R.string.WriteUsername, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action, null).show();
            return;
        }

        mDbHelper = new UserDbHelper(getBaseContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DrinksContract.DrinksEntry.COLUMN_NAME_USERNAME, username.getText().toString());
        values.put(DrinksContract.DrinksEntry.COLUMN_NAME_PASWORD, password.getText().toString());
        values.put(DrinksContract.DrinksEntry.COLUMN_NAME_EMAIL, email.getText().toString());

        if (CheckUsername(username.getText().toString())) {
            Snackbar.make(view, R.string.TakenUsername, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show();
        } else {
            db.insert(
                    DrinksContract.DrinksEntry.TABLE_NAME,
                    DrinksContract.DrinksEntry._ID,
                    values);

            db.close();
            Snackbar.make(view, getString(R.string.data_saved), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show();
            Intent intent = new Intent(getBaseContext(), AddDrinkActivity.class);
            intent.putExtra(getString(R.string.EXTRA_MESSAGE), username.getText().toString());
            intent.putExtra(getString(R.string.EXTRA_ID), "");
            startActivity(intent);
        }
    }

    public Boolean CheckUsername(String username) {
        mDbHelper = new UserDbHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor data = null;
        try {

            data = db.
                    rawQuery("select * from tblUser where username=?",
                            new String[]{username});
            data.moveToFirst();
            while (data.getCount() > 0) {
                String id = data.getString(data.getColumnIndex("username"));

                if (id.equals(username)) {
                    return true;

                } else {
                    data.moveToNext();
                }
            }
        } catch (Exception e) {
            mDbHelper.onCreate(db);

        }
        if (!data.equals(null)) {
            data = db.
                    rawQuery("select * from tblUser where username=?",
                            new String[]{username});
            data.moveToFirst();
            while (data.getCount() > 0) {
                String id = data.getString(data.getColumnIndex("username"));
                if (id.equals(username)) {
                    return true;

                }
            }
        }
        return false;
    }


}
