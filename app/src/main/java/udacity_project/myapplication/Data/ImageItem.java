package udacity_project.myapplication.Data;


import android.graphics.Bitmap;

/**
 * Created by Krenare Rexhepi on 8/29/2016.
 */
public class ImageItem {
    private String Id;
    private Bitmap image;
    private String title;
    private Boolean isFavorite;

    public ImageItem(Bitmap image, String title,Boolean isFavorite,String ids) {
        super();
        this.image = image;
        this.title = title;
        this.setFavorite(isFavorite);
        this.setId(ids);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
