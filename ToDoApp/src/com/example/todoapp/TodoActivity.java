package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class TodoActivity extends ActionBarActivity {
	
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo); 
        lvItems = (ListView) findViewById(R.id.lvitems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        readItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        setupEditListViewListener();
    }
    
    private void setupEditListViewListener() {
		lvItems.setOnItemClickListener(new OnItemClickListener () {
			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos,long id) {
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("ItemPos", pos);
				i.putExtra("ItemValue",todoItems.get(pos).toString());
				System.out.println(todoItems.get(pos).toString());
				startActivityForResult(i,REQUEST_CODE);		
			}
		});	
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
    	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) { 	  
    	     String editedValue = data.getExtras().getString("editedValue");
    	     int position = data.getExtras().getInt("position");
    	     todoItems.remove(position);
    	     todoItems.add(position, editedValue);
    	     todoAdapter.notifyDataSetChanged();
    	     writeItems();   	     	    
    	  }
	} 

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}});
	}

	public void onAddedItem(View v){
    	String itemText = etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }
  

    private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
	}
    
    private void writeItems() {
    	File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
