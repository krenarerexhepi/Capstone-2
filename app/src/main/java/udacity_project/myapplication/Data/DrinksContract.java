package udacity_project.myapplication.Data;

import android.provider.BaseColumns;

/**
 * Created by Krenare Rexhepi on 7/10/2016.
 */
public class DrinksContract {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public DrinksContract() {}

        /* Inner class that defines the table contents */
        public static abstract class DrinksEntry implements BaseColumns {
            public static final String TABLE_NAME = "tblUser";
            public static final String _ID = "idUser";
            public static final String COLUMN_NAME_USERNAME = "username";
            public static final String COLUMN_NAME_EMAIL = "email";
            public static final String COLUMN_NAME_PASWORD = "password";


            public static final String TABLE_NAME_DRINK = "tblDrink";
            public static final String _ID_DRINK = "idDrink";
            public static final String COLUMN_DRINK_NAME = "drinkname";
            public static final String COLUMN_DRINK_RECIPE = "recipe";
            public static final String COLUMN_IS_DETOX = "isdetox";
            public static final String COLUMN_IMAGE = "drinkimg";
            public static final String COLUMN_NAME_USERNAME_DRINK = "username";
            public static final String COLUMN_IS_FAVORITE="isFavorite";

        }
     }



