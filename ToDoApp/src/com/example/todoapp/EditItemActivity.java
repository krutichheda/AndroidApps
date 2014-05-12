package com.example.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class EditItemActivity extends ActionBarActivity {
	private EditText et_edit;
    public int pos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		et_edit = (EditText) findViewById(R.id.et_edit);
	    String value = getIntent().getStringExtra("ItemValue");  
	    pos = getIntent().getIntExtra("ItemPos", 0);
	    et_edit.setText(value);
	    et_edit.setSelection(et_edit.getText().length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_item, menu);
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
	
	public void onSave(View v){
    	  Intent data = new Intent();
    	  data.putExtra("editedValue", et_edit.getText().toString());
    	  data.putExtra("position", pos);
    	  setResult(RESULT_OK,data); // set result code and bundle data for response
    	  finish(); // closes the activity, pass data to parent
    }
}
