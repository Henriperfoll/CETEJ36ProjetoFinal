package henriperfoll.cetej36projetofinal.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME    = "moviereview.db";
    private static final int    DB_VERSION = 2;

    private static AppDatabase instance;

    private Context context;
    public  MovieReviewDAO movieReviewDAO;

    public static AppDatabase getInstance(Context context){

        if (instance == null){
            instance = new AppDatabase(context);
        }

        return instance;
    }

    private AppDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        context = context;

        movieReviewDAO = new MovieReviewDAO(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        movieReviewDAO.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        movieReviewDAO.deleteTable(db);

        onCreate(db);
    }
}
