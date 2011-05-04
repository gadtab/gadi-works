package il.co.gadiworks.thebasics;

import android.app.ListActivity;
import android.os.Bundle;

public class ListV extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	static final String[] food = new String[] {
		"Ice Cream", "Bacon", "Cheese", "Sandwich",
		"Ice Cream", "Bacon", "Cheese", "Sandwich",
		"Ice Cream", "Bacon", "Cheese", "Sandwich"
	};
}
