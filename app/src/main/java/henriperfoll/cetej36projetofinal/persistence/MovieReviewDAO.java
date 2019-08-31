package henriperfoll.cetej36projetofinal.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import henriperfoll.cetej36projetofinal.model.MovieReview;

public class MovieReviewDAO {

    public static final String TABLE    = "MOVIE_REVIEW";
    public static final String NAME     = "NAME";
    public static final String ID       = "ID";
    public static final String REVIEW   = "REVIEW";

    private AppDatabase database;
    public List<MovieReview> list;

    public MovieReviewDAO(AppDatabase database){
        this.database = database;
        this.list = new ArrayList<>();
    }

    public void createTable(SQLiteDatabase database){
        String sql = "CREATE TABLE " + TABLE + "(" +
                ID    + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                NAME  + " TEXT NOT NULL, " +
                REVIEW + " TEXT)";
        database.execSQL(sql);
    }

    public void deleteTable(SQLiteDatabase database){
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        database.execSQL(sql);
    }

    public boolean insert(MovieReview movieReview){
        ContentValues values = new ContentValues();

        values.put(NAME, movieReview.getMovieName());
        values.put(REVIEW, movieReview.getReview());

        long id = database.getWritableDatabase().insert(TABLE,
                null,
                values);

        movieReview.setId(id);

        list.add(movieReview);

        this.sortList();

        return true;
    }

    public boolean update(MovieReview movieReview){
        ContentValues values = new ContentValues();

        values.put(NAME,  movieReview.getMovieName());
        values.put(REVIEW, movieReview.getReview());

        String[] args = {String.valueOf(movieReview.getId())};

        database.getWritableDatabase().update(TABLE,
                values,
                ID + " = ?",
                args);

        this.sortList();

        return true;
    }

    public boolean delete(MovieReview movieReview){

        String[] args = {String.valueOf(movieReview.getId())};

        database.getWritableDatabase().delete(TABLE,
                ID + " = ?",
                args);
        list.remove(movieReview);

        return true;
    }

    public void loadAll(){
        list.clear();

        String sql = "SELECT * FROM " + TABLE + " ORDER BY " + NAME;

        Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);

        int columnName  = cursor.getColumnIndex(NAME);
        int columnId    = cursor.getColumnIndex(ID);
        int columnReview = cursor.getColumnIndex(REVIEW);

        while(cursor.moveToNext()){

            MovieReview movieReview = new MovieReview();

            movieReview.setId(cursor.getLong(columnId));
            movieReview.setMovieName(cursor.getString(columnName));
            movieReview.setReview(cursor.getString(columnReview));

            list.add(movieReview);
        }

        cursor.close();
    }

    public MovieReview movieReviewById(long id){

        for (MovieReview m: list){

            if (m.getId() == id){
                return m;
            }
        }

        return null;
    }

    public void sortList(){
        Collections.sort(list, MovieReview.comparador);
    }
}
