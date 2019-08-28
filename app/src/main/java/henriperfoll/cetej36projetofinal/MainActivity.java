package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import henriperfoll.cetej36projetofinal.model.MovieReview;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private ListView listViewMovies;
    private ArrayAdapter<MovieReview> listAdapter;
    private ArrayList<MovieReview> listMovies;
    private int positionSelected = -1;
    private ActionMode actionMode;
    private View viewSelected;

    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_menu_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {return false;}

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case  R.id.menuContextItemEdit:
                    edit();
                    mode.finish();
                    return true;
                case R.id.menuContextItemDelete:
                    delete(positionSelected);
                    mode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelected != null ){
                viewSelected.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelected = null;
            listViewMovies.setEnabled(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.layout = findViewById(R.id.principal_layout);
        this.listViewMovies = findViewById(R.id.listItemStrings);

        this.listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionSelected = position;
                edit();
            }
        });
        this.listViewMovies.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.listViewMovies.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {
                        if (actionMode != null){
                            return false;
                        }
                        positionSelected = position;
                        view.setBackgroundColor(Color.LTGRAY);
                        viewSelected = view;
                        listViewMovies.setEnabled(false);
                        actionMode = startSupportActionMode(mActionModeCallBack);
                        return true;
                    }
                });

        this.populateList();
        registerForContextMenu(this.listViewMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemAdd:
                AddMovieReviewActivity.newReview(this);
                return true;
            case R.id.menuItemTestColor:
                this.layout.setBackgroundColor(Color.DKGRAY);
                item.setChecked(true);
                return true;
            case R.id.menuItemTestColor2:
                this.layout.setBackgroundColor(Color.CYAN);
                item.setChecked(true);
                return true;
            case R.id.menuItemAbout:
                Toast.makeText(this,"TODO - About",Toast.LENGTH_LONG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK){

            Bundle bundle = data.getExtras();

            if (requestCode == AddMovieReviewActivity.EDIT){
                MovieReview movie = this.listMovies.get(positionSelected);
                movie.setMovieName(bundle.getString(AddMovieReviewActivity.NAME));
                movie.setReview(bundle.getString(AddMovieReviewActivity.REVIEW));
            }else{
                MovieReview movie = new MovieReview();
                movie.setMovieName(bundle.getString(AddMovieReviewActivity.NAME));
                movie.setReview(bundle.getString(AddMovieReviewActivity.REVIEW));
                this.listMovies.add(movie);
            }

            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void exampleMenuAction(MenuItem item){
        item.setChecked(!item.isChecked());
    }

    private void populateList(){
        this.listMovies = new ArrayList<>();

        this.listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,this.listMovies);

        this.listViewMovies.setAdapter(this.listAdapter);
    }

    public void delete(int position){
        this.listMovies.remove(position);
        this.listAdapter.notifyDataSetChanged();
    }

    public void edit(){
        AddMovieReviewActivity.editReview(this,listMovies.get(this.positionSelected));
    }


}
