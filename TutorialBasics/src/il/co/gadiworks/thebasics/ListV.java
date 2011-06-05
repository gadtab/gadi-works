package il.co.gadiworks.thebasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListV extends ListActivity {
	String[] classNames = new String[] {
		"MyMain", "MyMenu", "Sweet", "TutorialOne",
		"TutorialThree", "TutorialFour", "TutorialFive"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classNames));
		
		ListView list = getListView();
		list.setTextFilterEnabled(true);
	}

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		
		String openClass = this.classNames[position];
		
		try {
			Class selected = Class.forName("il.co.gadiworks.thebasics." + openClass);
			Intent selectedIntent = new Intent(this, selected);
			startActivity(selectedIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
