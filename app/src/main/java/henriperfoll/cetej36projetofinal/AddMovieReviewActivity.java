package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import henriperfoll.cetej36projetofinal.model.MovieReview;
import henriperfoll.cetej36projetofinal.persistence.AppDatabase;

public class AddMovieReviewActivity extends AppCompatActivity {

    public static final String MODE         = "MODE";
    public static final String ID           = "ID";
    public static final int    NEW  = 1;
    public static final int    EDIT = 2;

    private ConstraintLayout layout;
    private EditText movieNameEditText;
    private EditText movieReviewEditText;
    private int mode;
    private MovieReview original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_review);
        this.readPreferences();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.movieNameEditText = findViewById(R.id.editTextMovieName);
        this.movieReviewEditText = findViewById(R.id.editTextReview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            mode = bundle.getInt(MODE,NEW);
            if(mode == NEW){
                setTitle(R.string.new_movie_review);
            }else{
                setTitle(R.string.edit_movie_review);
                AppDatabase database = AppDatabase.getInstance(this);
                this.original = database.movieReviewDAO.movieReviewById(bundle.getLong(ID));
                this.movieNameEditText.setText(this.original.getMovieName());
                this.movieReviewEditText.setText(this.original.getReview());
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_movie_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemAddReview:
                this.save();
                return true;
            case android.R.id.home:
                this.cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void newReview(AppCompatActivity activity){
        Intent intent = new Intent(activity, AddMovieReviewActivity.class);

        intent.putExtra(MODE, NEW);

        activity.startActivityForResult(intent, NEW);
    }

    public static void editReview(AppCompatActivity activity, MovieReview review){
        Intent intent = new Intent(activity, AddMovieReviewActivity.class);

        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID,review.getId());

        activity.startActivityForResult(intent, EDIT);
    }

    public void cancel(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void save(){

        String name = this.movieNameEditText.getText().toString();
        String review = this.movieReviewEditText.getText().toString();

        if(name == null || name.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.messageError,
                    Toast.LENGTH_SHORT).show();

            this.movieNameEditText.requestFocus();
            return;
        }

        AppDatabase database = AppDatabase.getInstance(this);


        if(this.mode == NEW){
            MovieReview movieReview = new MovieReview(name, review);
            database.movieReviewDAO.insert(movieReview);

        }else{
            this.original.setReview(review);
            this.original.setMovieName(name);
            database.movieReviewDAO.update(this.original);
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void readPreferences(){
        layout = findViewById(R.id.addMoviesReviewLayout);
        SharedPreferences shared = getSharedPreferences(PreferencesActivity.FILE, Context.MODE_PRIVATE);
        layout.setBackgroundColor(shared.getInt(PreferencesActivity.COLOR,PreferencesActivity.DAY));
    }
}
