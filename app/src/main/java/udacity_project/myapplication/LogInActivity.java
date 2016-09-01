package udacity_project.myapplication;

import android.content.Intent;
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
    String EXTRA_MESSAGE="EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                Cursor data = db.rawQuery("select * from tblUser where username=? and password=?",
                        new String[] { name,pass});

                data.moveToFirst();
                boolean isUsername=false;
                while (data.getCount() > 0) {
                    isUsername=true;
                    String id = data.getString(data.getColumnIndex("username"));
                    if(id.equals(name)) {
                        startActivity(intent);
                         intent.putExtra(EXTRA_MESSAGE, name);
                        break;
                    }
                    else{data.moveToNext();

                    }
                }
                if(isUsername==false)
                {
                    Snackbar.make(v,"This username do not exist!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
