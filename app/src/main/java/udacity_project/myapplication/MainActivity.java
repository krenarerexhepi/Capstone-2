package udacity_project.myapplication;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import udacity_project.myapplication.Data.BitmapUtility;
import udacity_project.myapplication.Data.GridViewAdapter;
import udacity_project.myapplication.Data.ImageItem;
import udacity_project.myapplication.Data.UserDbHelper;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    String username;
    String EXTRA_MESSAGE="EXTRA_MESSAGE";
    ImageButton imgButton;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1913927795422634~2973015302");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().addTestDevice("6815682B4D58DC5A23BE2F264CA95B54").build();
        mAdView.loadAd(request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = getIntent().getStringExtra("EXTRA_MESSAGE");

        img= (ImageView)findViewById(R.id.imgMain);
        imgButton=(ImageButton) findViewById(R.id.imageButton);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddDrinkActivity.class);
                intent.putExtra(EXTRA_MESSAGE, username);
                startActivity(intent);
            }
        });





        // PublisherAdView mPublisherAdView = (PublisherAdView) findViewById(R.id.adView);
       // PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
       // mPublisherAdView.loadAd(adRequest);


     /*   imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    v.setBackgroundResource(R.drawable.star_fill);

            }
        });
*/
     //  LoadGridData();

    }

    private void LoadGridData() {
        LoadImages();
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
    }

    Bitmap valu;
    String name;
    public  void LoadImage()
    {
        UserDbHelper mDbHelper;

        mDbHelper = new UserDbHelper(getBaseContext());

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                 name = String.valueOf("p");

               Cursor data = db.rawQuery("select * from tblDrink where drinkname=?",
                        new String[] { name });

                data.moveToFirst();
                while (data.getCount() > 0) {
                    String id = data.getString(data.getColumnIndex("drinkname"));
                    String user = data.getString(data.getColumnIndex("username"));

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

        UserDbHelper mDbHelper;

        mDbHelper = new UserDbHelper(getBaseContext());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

      try{

          Cursor data = db.rawQuery("select * from tblDrink where username=?",
                  new String[] {username });
          data.moveToFirst();
          while (data.getCount() > 0) {
              String drinkName = data.getString(data.getColumnIndex("drinkname"));
              String isdetox = data.getString(data.getColumnIndex("isdetox"));
              byte[] image = data.getBlob(data.getColumnIndex("drinkimg"));
              names.add(drinkName);

              BitmapUtility b = new BitmapUtility();
              valu =  b.getImageByteToBitmap(image);
              Drawable d = new BitmapDrawable(valu);
              img.setImageDrawable(d);
              images.add(valu);
              // break;
              if (data.isLast()) {
                  break;
              } else {
                  data.moveToNext();
              }
          }

      }catch (Exception e)
      {
          Context context = getApplicationContext();
          CharSequence text = "No image to display!";
          int duration = Toast.LENGTH_SHORT;

          Toast toast = Toast.makeText(context, text, duration);
          toast.show();
      }



    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
     //   LoadImage();
        for(int i =0;i<images.size();i++)
        {
            imageItems.add(new ImageItem(images.get(i),names.get(i),false));

        }
        return imageItems;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        LoadGridData();
           // Get the Camera instance as the activity achieves full user focus

    }
    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
         super.onDestroy();
    }
}
