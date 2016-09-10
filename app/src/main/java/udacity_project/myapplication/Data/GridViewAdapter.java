package udacity_project.myapplication.Data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import udacity_project.myapplication.R;

/**
 * Created by Krenare Rexhepi on 8/29/2016.
 */
public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.imageButton = (ImageButton) row.findViewById(R.id.imageButton);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Boolean isFav = (Boolean) view.getTag();

                    ImageButton btn= (ImageButton) view;
                    btn.setImageDrawable(
                            context.getDrawable(
                                    isFav ?
                                            android.R.drawable.star_big_off :
                                            android.R.drawable.star_big_on));
                    btn.setTag(!isFav);
                }
            });

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = (ImageItem) data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());

        holder.imageButton.setImageDrawable(
                context.getDrawable(
                        item.getFavorite() ?
                                android.R.drawable.star_big_on :
                                android.R.drawable.star_big_off));
        holder.imageButton.setTag(item.getFavorite());

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        ImageButton imageButton;
    }
}
