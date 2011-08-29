package il.co.gadiworks.tutorial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SQLiteExample extends Activity implements OnClickListener {
	Button sqlUpdate, sqlView;
	EditText sqlName, sqlHotness;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);

		sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
		sqlView = (Button) findViewById(R.id.bSQLOpenView);
		sqlName = (EditText) findViewById(R.id.etSQLName);
		sqlHotness = (EditText) findViewById(R.id.etSQLHotness);

		sqlUpdate.setOnClickListener(this);
		sqlView.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bSQLUpdate:
			boolean didItWork = true;
			try {
				String name = sqlName.getText().toString();
				String hotness = sqlHotness.getText().toString();
				
				HotOrNot entry = new HotOrNot(SQLiteExample.this);
				entry.open();
				entry.createEntry(name, hotness);
				entry.close();
			}
			catch (Exception e) {
				didItWork = false;
			}
			finally {
				if (didItWork) {
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yeah!");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.bSQLOpenView:
			Intent i = new Intent("il.co.gadiworks.tutorial.SQLVIEW");
			startActivity(i);
			break;
		}
	}
}