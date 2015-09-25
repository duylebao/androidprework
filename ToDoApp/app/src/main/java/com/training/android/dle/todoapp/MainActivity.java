package com.training.android.dle.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdapters;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById( R.id.lvItems );
        lvItems.setAdapter(todoAdapters);
        etEditText = (EditText)findViewById( R.id.etEditText );
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                todoAdapters.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        todoAdapters = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>();
            List<String> items = FileUtils.readLines(file);
            if (items != null){
                todoItems.addAll(items);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddItem(View view) {
        todoAdapters.add( etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
