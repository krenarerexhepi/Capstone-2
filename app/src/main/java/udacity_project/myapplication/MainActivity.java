package udacity_project.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import udacity_project.myapplication.Data.BitmapUtility;
import udacity_project.myapplication.Data.DrinksDbHelper;
import udacity_project.myapplication.Data.GridViewAdapter;
import udacity_project.myapplication.Data.ImageItem;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img= (ImageView)findViewById(R.id.imgMain);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddDrinkActivity.class);
                startActivity(intent);
            }
        });

        LoadImages();
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

    }

    DrinksDbHelper mDbHelper;
    Bitmap valu;
    String name;
    public  void LoadImage()
    {
        mDbHelper = new DrinksDbHelper(getBaseContext());

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                 name = String.valueOf("p");

               Cursor data = db.rawQuery("select * from tblDrink where drinkname=?",
                        new String[] { name });

                data.moveToFirst();
                while (data.getCount() > 0) {
                    String id = data.getString(data.getColumnIndex("drinkname"));
                    if(id.equals(name)) {
                        byte[] image = data.getBlob(data.getColumnIndex("drinkimg"));
                        BitmapUtility b = new BitmapUtility();
                          valu =  b.getImageByteToBitmap(image);
                        Drawable d = new BitmapDrawable(valu);
                         img.setImageDrawable(d);
                       /*   Picasso.with(this)
                                .load("YOUR IMAGE URL HERE")
                                .into(imageVie);*/
                        // break;
                        if (data.isLast()) {
                            break;
                        } else {
                            data.moveToNext();
                        }
                                     }


    }
    }

    ArrayList<String> names;
    ArrayList<Bitmap> images;

    public  void LoadImages()
    {
        names= new ArrayList<>();
        images = new ArrayList<>();

        mDbHelper = new DrinksDbHelper(getBaseContext());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

       Cursor data = db.rawQuery("select * from tblDrink",
                new String[] { });
        data.moveToFirst();
        while (data.getCount() > 0) {
                String drinkName = data.getString(data.getColumnIndex("drinkname"));
                byte[] image = data.getBlob(data.getColumnIndex("drinkimg"));
            names.add(drinkName);
            BitmapUtility b = new BitmapUtility();
                valu =  b.getImageByteToBitmap(image);
                Drawable d = new BitmapDrawable(valu);
                img.setImageDrawable(d);
                images.add(valu);
                       /*   Picasso.with(this)
                                .load("YOUR IMAGE URL HERE")
                                .into(imageVie);*/
                // break;
                if (data.isLast()) {
                    break;
                } else {
                    data.moveToNext();
                }
            }

    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
     //   LoadImage();
        for(int i =0;i<images.size();i++)
        {
            imageItems.add(new ImageItem(images.get(i),names.get(i)));
        }

        return imageItems;
    }
}
