package udacity_project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import udacity_project.myapplication.Data.UserDbHelper;

public class LogInActivity extends AppCompatActivity {

    UserDbHelper mDbHelper;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.PREFERENCE),Context.MODE_PRIVATE);
        Boolean loggedIn = sharedPref.getBoolean(getString(R.string.LoggedUser),false);
        String loggedUsername = sharedPref.getString(getString(R.string.LoggedUsername),"");

        //logged in
        if(loggedIn){

            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra(getString(R.string.EXTRA_MESSAGE), loggedUsername);
            startActivity(intent);
        }

        final Button btnLogIn= (Button)findViewById(R.id.sign_in_button);
        final Button btnRegister=(Button)findViewById(R.id.register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        mDbHelper = new UserDbHelper(getBaseContext());
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);


                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                String name = String.valueOf(username.getText());
                String pass = String.valueOf(password.getText());

                Cursor data = db.rawQuery(getString(R.string.selectForUsername),
                        new String[] { name,pass});

                data.moveToFirst();
                boolean isUsername=false;
                while (data.getCount() > 0) {
                    isUsername=true;
                    String id = data.getString(data.getColumnIndex(getString(R.string.username)));
                    if(id.equals(name)) {
                        // Restore preferences
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.PREFERENCE),Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(getString(R.string.LoggedUser), true);
                        editor.putString(getString(R.string.LoggedUsername), name);
                        editor.apply();

                        intent.putExtra(getString(R.string.EXTRA_MESSAGE), name);
                         startActivity(intent);
                        break;
                    }
                    else{data.moveToNext();

                    }
                }
                if(!isUsername)
                {
                    Snackbar.make(v, R.string.username_or_pass, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action, null).show();

                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                // EditText editText = (EditText) findViewById(R.id.edit_message);
                //String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }

    private void showLogin(String username) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
         intent.putExtra(getString(R.string.EXTRA_MESSAGE), username);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
    super.onStop();
    }
}
