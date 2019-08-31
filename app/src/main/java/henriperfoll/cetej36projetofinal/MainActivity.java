package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import henriperfoll.cetej36projetofinal.model.MovieReview;
import henriperfoll.cetej36projetofinal.persistence.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private ListView listViewMovies;
    private ArrayAdapter<MovieReview> listAdapter;
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
                    deleteAlert();
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
        readPreferences();

        AppDatabase database = AppDatabase.getInstance(this);
        database.movieReviewDAO.loadAll();

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
            case R.id.menuItemAbout:
                AboutActivity.showAbout(this);
                return true;
            case R.id.menuItemPreferences:
                PreferencesActivity.setPreferences(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == PreferencesActivity.PREFERENCE){
                this.readPreferences();
                return;
            }
            this.listAdapter.notifyDataSetChanged();
        }
    }

    private void populateList(){

        AppDatabase database = AppDatabase.getInstance(this);

        this.listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,database.movieReviewDAO.list);

        this.listViewMovies.setAdapter(this.listAdapter);
    }

    public void deleteAlert(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setMessage(R.string.alert_message).setCancelable(true).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.setTitle(getString(R.string.alert_title));
        alert.show();
    }

    public void delete(){
        AppDatabase database = AppDatabase.getInstance(this);
        database.movieReviewDAO.delete(database.movieReviewDAO.list.get(positionSelected));
        listAdapter.notifyDataSetChanged();
    }

    public void edit(){
        AppDatabase database = AppDatabase.getInstance(this);
        AddMovieReviewActivity.editReview(this,database.movieReviewDAO.list.get(positionSelected));
    }

    public void readPreferences(){
        layout = findViewById(R.id.principal_layout);
        SharedPreferences shared = getSharedPreferences(PreferencesActivity.FILE, Context.MODE_PRIVATE);
        layout.setBackgroundColor(shared.getInt(PreferencesActivity.COLOR,PreferencesActivity.DAY));
    }


}
