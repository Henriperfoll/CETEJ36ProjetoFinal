package henriperfoll.cetej36projetofinal;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private ListView listItemsStrings;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> strings;
    private EditText editText;
    private ImageButton imageButton1, imageButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.layout = findViewById(R.id.principal_layout);
        this.editText = findViewById(R.id.editTextString);
        this.imageButton1 = findViewById(R.id.imageButton1);
        this.imageButton2 = findViewById(R.id.imageButton2);
        this.listItemsStrings = findViewById(R.id.listItemStrings);

        this.populateList();

        registerForContextMenu(this.listItemsStrings);
    }

    private void populateList(){
        this.strings = new ArrayList<>();

        this.adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,this.strings);

        this.listItemsStrings.setAdapter(this.adapter);
    }

    public void add(View view){
        String str = this.editText.getText().toString();
        if(str.isEmpty())
            return;
        this.editText.setText("");
        this.strings.add(str);
        this.adapter.notifyDataSetChanged();
    }

    public void delete(int position){
        this.strings.remove(position);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_menu_context,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItemExcluir:
                Toast.makeText(this,"TODO: esse item",Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menuContextItemEdit:
                Toast.makeText(this,"TODO: editar",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuContextItemDelete:
                this.delete(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void exemploAcaoMenuItem(MenuItem item){
        item.setChecked(!item.isChecked());
    }
}
