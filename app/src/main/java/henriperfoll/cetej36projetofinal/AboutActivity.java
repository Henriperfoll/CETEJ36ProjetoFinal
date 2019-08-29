package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity {

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about_movies_review);
        this.layout = findViewById(R.id.aboutLayout);
        SharedPreferences shared = getSharedPreferences(PreferencesActivity.FILE, Context.MODE_PRIVATE);
        layout.setBackgroundColor(shared.getInt(PreferencesActivity.COLOR,PreferencesActivity.DAY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void showAbout(AppCompatActivity activity){
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    public void exit(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
