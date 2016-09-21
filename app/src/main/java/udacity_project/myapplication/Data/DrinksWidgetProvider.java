package udacity_project.myapplication.Data;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

import java.util.Random;

import udacity_project.myapplication.MainActivity;
import udacity_project.myapplication.R;

import static udacity_project.myapplication.R.id.drinksName;
import static udacity_project.myapplication.R.id.username;

/**
 * Created by betim on 9/19/2016.
 */

public class DrinksWidgetProvider extends AppWidgetProvider {

        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            final int count = appWidgetIds.length;

            for (int i = 0; i < count; i++) {
                int widgetId = appWidgetIds[i];

                UserDbHelper mDbHelper;
                mDbHelper = new UserDbHelper(context);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
               Cursor data = db.rawQuery("select * from tblDrink where username=?",
                        new String[] {"k" });

                String drinkName="";
                data.moveToFirst();
                while (data.getCount() > 0) {
                    drinkName = data.getString(data.getColumnIndex("drinkname"));
                    if (data.isLast()) {
                        break;
                    } else {
                        data.moveToNext();
                    }
                }

               Intent intent = new Intent(context, DrinksWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);


                Intent p_intent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, p_intent, PendingIntent.FLAG_UPDATE_CURRENT);


         /*       PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.simple_widget);
                remoteViews.setOnClickPendingIntent(R.id.imageButton, pendingIntent);
                remoteViews.setTextViewText(R.id.txtDrinkName, drinkName);


                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }