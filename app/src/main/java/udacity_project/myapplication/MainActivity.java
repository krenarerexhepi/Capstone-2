package udacity_project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import udacity_project.myapplication.Data.BitmapUtility;
import udacity_project.myapplication.Data.GridViewAdapter;
import udacity_project.myapplication.Data.ImageItem;
import udacity_project.myapplication.Data.UserDbHelper;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    String username;
    String EXTRA_MESSAGE="EXTRA_MESSAGE";
    ImageButton imgButton;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


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
                intent.putExtra("EXTRA_ID","");
                startActivity(intent);
            }
        });

        Bundle params = new Bundle();
        params.putString("full_text", username);
        mFirebaseAnalytics.logEvent("loged_user", params);

       }
String value="";
    private void LoadGridData() {
        LoadImages(value);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
    }
    Bitmap valu;
    ArrayList<String> names;
    ArrayList<Bitmap> images;
    ArrayList<Boolean> favStatus;
    ArrayList<String> ids;

    public  void LoadImages(String value)
    {
        names= new ArrayList<>();
        images = new ArrayList<>();
        favStatus = new ArrayList<>();
        ids =new ArrayList<>();
        UserDbHelper mDbHelper;
        mDbHelper = new UserDbHelper(getBaseContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

      try{
          Cursor  data= null;
if(value.equals("favorite")){
    data =  db.rawQuery("select * from tblDrink where isFavorite=? and username=?",
            new String[] {"true",username });
}
          else if(value.equals("detox")){
    data =  db.rawQuery("select * from tblDrink where isdetox=? and username=?",
            new String[]{"true",username});
}
          else
{
    data = db.rawQuery(getString(R.string.select_for_username),
            new String[] {username });


}
          data.moveToFirst();
          while (data.getCount() > 0) {
              String drinkName = data.getString(data.getColumnIndex(getString(R.string.drinkname)));
              String isFav = data.getString(data.getColumnIndex("isFavorite"));
              String id = data.getString(data.getColumnIndex("idDrink"));
              Boolean boolean1 = Boolean.valueOf(isFav);

              byte[] image = data.getBlob(data.getColumnIndex(getString(R.string.drinkimg)));
              names.add(drinkName);
              favStatus.add(boolean1);

              BitmapUtility b = new BitmapUtility();
              valu =  b.getImageByteToBitmap(image);
              Drawable d = new BitmapDrawable(valu);
              img.setImageDrawable(d);
              images.add(valu);
              ids.add(id);
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
          CharSequence text = getString(R.string.no_image);
          int duration = Toast.LENGTH_SHORT;

          Toast toast = Toast.makeText(context, text, duration);
          toast.show();
      }

    }
    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        imageItems.clear();
        for(int i =0;i<images.size();i++)
        {   imageItems.add(new ImageItem(images.get(i),names.get(i),favStatus.get(i),ids.get(i)));

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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPref = getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                     editor.remove("LoggedUser");
                    editor.remove("LoggedUsername");
                     editor.clear();
                      editor.commit();
            editor.apply();

            Intent loginscreen=new Intent(this,LogInActivity.class);
            loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginscreen);

        }
        if (id == R.id.action_main)
        {
            value="";
              LoadGridData();

        }
        if (id == R.id.action_detox) {
            value="detox";
            LoadGridData();

        }
        if (id == R.id.action_favorite)
        {
            value="favorite";
            LoadGridData();

        }
        return super.onOptionsItemSelected(item);
    }

    private void loadIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + images);

        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
    }
}
