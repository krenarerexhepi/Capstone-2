package udacity_project.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import udacity_project.myapplication.Data.BitmapUtility;
import udacity_project.myapplication.Data.DrinksContract;
import udacity_project.myapplication.Data.UserDbHelper;

public class AddDrinkActivity extends AppCompatActivity {

    EditText drinkName;
    EditText drinkRecipe;
    CheckBox detox;
    ImageView img;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.uploadImage);
        Button btnSave = (Button) findViewById(R.id.btnSaveDrinks);
        user = getIntent().getStringExtra("EXTRA_MESSAGE");
        drinkName =(EditText)findViewById(R.id.drinksName);
        drinkRecipe = (EditText)findViewById(R.id.recipe);
        detox = (CheckBox)findViewById(R.id.isdetox);
        img = (ImageView)findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveDrinkData(v);
            }
        });
    }

    UserDbHelper mDbHelper = new UserDbHelper(getBaseContext());

    public void saveDrinkData(View view)
    {
        mDbHelper = new UserDbHelper(getBaseContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String detoxValue="";
        if(detox.isChecked())
        {
            detoxValue="true";
        }
        else
        {
            detoxValue="false";
        }
        ContentValues values = new ContentValues();
        values.put(DrinksContract.DrinksEntry.COLUMN_DRINK_NAME, drinkName.getText().toString());
        values.put(DrinksContract.DrinksEntry.COLUMN_DRINK_RECIPE, drinkRecipe.getText().toString());
        values.put(DrinksContract.DrinksEntry.COLUMN_IS_DETOX, detoxValue);
        values.put(DrinksContract.DrinksEntry.COLUMN_IMAGE, bitmapdata);
        values.put(DrinksContract.DrinksEntry.COLUMN_NAME_USERNAME_DRINK, user);
        try {
            db.insert(
                    DrinksContract.DrinksEntry.TABLE_NAME_DRINK,
                    DrinksContract.DrinksEntry._ID_DRINK,
                    values);

            db.close();
            Snackbar.make(view, "Data saved ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        catch (Exception ex)
        {
            Snackbar.make(view, "Error data", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

    }
    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddDrinkActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery"))
                {
                   Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }

    byte[] bitmapdata;
    public void onActivityResult(int requestcode,int resultcode,Intent intent)
    {
        super.onActivityResult(requestcode, resultcode, intent);
        if(resultcode==RESULT_OK)
        {
           if(requestcode==2)
            {
                Uri selectedImage = intent.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                assert c != null;
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Drawable drawable=new BitmapDrawable(thumbnail);
                img.setImageDrawable(drawable);

                Drawable d; // the drawable (Captain Obvious, to the rescue!!!)
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                bitmapdata= BitmapUtility.getBytes(bitmap);

            }
        }
    }
     public void onBackPressed() {
        super.onBackPressed();
    }
}
