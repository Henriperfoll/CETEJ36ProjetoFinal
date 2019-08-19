package henriperfoll.cetej36projetofinal;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.layout = findViewById(R.id.principal_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemExcluir:

                return true;

            case R.id.menuItemSalvar:

                return true;

            case R.id.menuItemTestColor:
                this.layout.setBackgroundColor(Color.DKGRAY);
                item.setChecked(true);
                return true;

            case R.id.menuItemTestColor2:
                this.layout.setBackgroundColor(Color.CYAN);
                item.setChecked(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void exemploAcaoMenuItem(MenuItem item){
        item.setChecked(!item.isChecked());
    }
}
