package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import henriperfoll.cetej36projetofinal.model.MovieReview;

public class AddMovieReviewActivity extends AppCompatActivity {

    public static final String MODE         = "MODE";
    public static final String NAME         = "NAME";
    public static final String REVIEW       = "REVIEW";
    public static final String SCORE        = "SCORE";
    public static final int    NEW  = 1;
    public static final int    EDIT = 2;

    private EditText movieNameEditText;
    private EditText movieReviewEditText;
    private int mode;
    private MovieReview original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_review);
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
                setTitle("New Movie Review");
            }else{
                this.original = new MovieReview(bundle.getString(NAME),bundle.getString(REVIEW));
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
                this.add();
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
        intent.putExtra(REVIEW,review.getReview());
        intent.putExtra(NAME,review.getMovieName());

        activity.startActivityForResult(intent, EDIT);
    }

    public void cancel(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void add(){

        String name = this.movieNameEditText.getText().toString();

        if(name == null || name.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.messageError,
                    Toast.LENGTH_SHORT).show();

            this.movieNameEditText.requestFocus();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(NAME, name);
        intent.putExtra(REVIEW, this.movieReviewEditText.getText().toString());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
