package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TutorialThree extends Activity implements OnClickListener {
	ImageView ivDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial3);
		
		ivDisplay = (ImageView) findViewById(R.id.ivDisplay);
		
		ImageView ivImage1 = (ImageView) findViewById(R.id.ivImage1);
		ImageView ivImage2 = (ImageView) findViewById(R.id.ivImage2);
		ImageView ivImage3 = (ImageView) findViewById(R.id.ivImage3);
		ImageView ivImage4 = (ImageView) findViewById(R.id.ivImage4);
		ImageView ivImage5 = (ImageView) findViewById(R.id.ivImage5);
		ImageView ivImage6 = (ImageView) findViewById(R.id.ivImage6);
		ImageView ivImage7 = (ImageView) findViewById(R.id.ivImage7);
		ImageView ivImage8 = (ImageView) findViewById(R.id.ivImage8);
		ImageView ivImage9 = (ImageView) findViewById(R.id.ivImage9);
		ImageView ivImage10 = (ImageView) findViewById(R.id.ivImage10);
		ImageView ivImage11 = (ImageView) findViewById(R.id.ivImage11);
		ImageView ivImage12 = (ImageView) findViewById(R.id.ivImage12);
		
		ivImage1.setOnClickListener(this);
		ivImage2.setOnClickListener(this);
		ivImage3.setOnClickListener(this);
		ivImage4.setOnClickListener(this);
		ivImage5.setOnClickListener(this);
		ivImage6.setOnClickListener(this);
		ivImage7.setOnClickListener(this);
		ivImage8.setOnClickListener(this);
		ivImage9.setOnClickListener(this);
		ivImage10.setOnClickListener(this);
		ivImage11.setOnClickListener(this);
		ivImage12.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.ivImage1:
			ivDisplay.setImageResource(R.drawable.clouds_picture);
			break;
		case R.id.ivImage2:
			ivDisplay.setImageResource(R.drawable.dog_animal);
			break;
		case R.id.ivImage3:
			ivDisplay.setImageResource(R.drawable.engine_photo);
			break;
		case R.id.ivImage4:
			ivDisplay.setImageResource(R.drawable.flowers_image);
			break;
		case R.id.ivImage5:
			ivDisplay.setImageResource(R.drawable.forest_wood);
			break;
		case R.id.ivImage6:
			ivDisplay.setImageResource(R.drawable.fruits_picture);
			break;
		case R.id.ivImage7:
			ivDisplay.setImageResource(R.drawable.lake_tree);
			break;
		case R.id.ivImage8:
			ivDisplay.setImageResource(R.drawable.logs_picture);
			break;
		case R.id.ivImage9:
			ivDisplay.setImageResource(R.drawable.sculpture_photo);
			break;
		case R.id.ivImage10:
			ivDisplay.setImageResource(R.drawable.stones_background);
			break;
		case R.id.ivImage11:
			ivDisplay.setImageResource(R.drawable.sunshades);
			break;
		case R.id.ivImage12:
			ivDisplay.setImageResource(R.drawable.tree_winter);
			break;
		}
	}
}
