package il.co.gadiworks.tutorial;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ShoppingList extends Activity {
//   List<String> myList;
   ListView lv;
   EditText text1;
   ArrayAdapter<String> ad;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.shoppinglist);
      
      text1 = (EditText) findViewById(R.id.additem);
      lv = (ListView) findViewById(R.id.list);

      final List<String> myList = new ArrayList<String>();
      myList.add("Hello");
      myList.add("World");
      myList.add("Foo");
      myList.add("Bar");
      
      myList.add("Hello");
      myList.add("World");
      myList.add("Foo");
      myList.add("Bar");
      
      myList.add("Hello");
      myList.add("World");
      myList.add("Foo");
      myList.add("Bar");
      
      myList.add("Hello");
      myList.add("World");
      myList.add("Foo");
      myList.add("Bar");
    	   
      ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
      lv.setAdapter(ad);
      
      Button ok = (Button) findViewById(R.id.buttonok);
      ok.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
        	 String newItem = text1.getText().toString();
        	 
        	 if (!newItem.equals("")) {
        		 int i = lv.getCount();
	        	 ad.insert(newItem, i);
	             text1.setText("");
        	 }
         }
      });
   }
}