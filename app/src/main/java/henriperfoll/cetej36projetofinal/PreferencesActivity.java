package henriperfoll.cetej36projetofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

public class PreferencesActivity extends AppCompatActivity {

    public static final int DAY = Color.WHITE;
    public static final int NIGHT = Color.LTGRAY;
    public static final int PREFERENCE = 3;
    public static final String COLOR = "COLOR";
    public static final String FILE = "henriperfoll.cetej36projetofinal.PREFERENCES";

    private int colorMode = DAY;
    private RadioGroup radioGroupModes;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.preferences);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        this.radioGroupModes = findViewById(R.id.radioGroupColorMode);
        this.layout = findViewById(R.id.preferencesLayout);
        this.readPreferences(this.layout);

        this.radioGroupModes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeColor();
                changeColorPreferences();
            }
        });

        this.checkRadioButtons();
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

    public static void setPreferences(AppCompatActivity activity){
        Intent intent = new Intent(activity, PreferencesActivity.class);

        activity.startActivityForResult(intent, PREFERENCE);
    }

    public void readPreferences(ConstraintLayout layout){
        SharedPreferences shared = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        this.colorMode = shared.getInt(COLOR,DAY);
        this.changePreferences(layout);
    }

    public void changePreferences(ConstraintLayout layout){
        layout.setBackgroundColor(this.colorMode);
    }

    public void changeColorPreferences(){
        this.layout.setBackgroundColor(this.colorMode);
    }

    public void savePreferences(View view){

        SharedPreferences shared = getSharedPreferences(FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(COLOR, colorMode);

        editor.commit();

        setResult(Activity.RESULT_OK);
        finish();
    }

    public void checkRadioButtons(){
        switch (this.colorMode){
            case NIGHT:
                this.radioGroupModes.check(R.id.radioButtonNightMode);
                break;
            default:
                this.radioGroupModes.check(R.id.radioButtonDayMode);
        }
    }

    public void changeColor(){

        switch (this.radioGroupModes.getCheckedRadioButtonId()){
            case R.id.radioButtonDayMode:
                this.colorMode = DAY;
                break;
            case R.id.radioButtonNightMode:
                this.colorMode = NIGHT;
                break;
        }
    }

    public void exit(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
