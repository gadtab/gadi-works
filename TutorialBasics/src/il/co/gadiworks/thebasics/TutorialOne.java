package il.co.gadiworks.thebasics;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TutorialOne extends Activity implements OnCheckedChangeListener {
	TextView tvGetInput;
	EditText etInput;
	RadioGroup rgGravity, rgStyle;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial1);
        
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        rgStyle = (RadioGroup) findViewById(R.id.rgStyle);
        
        rgGravity.setOnCheckedChangeListener(this);
        rgStyle.setOnCheckedChangeListener(this);
        
        tvGetInput = (TextView) findViewById(R.id.tvGetInput);
        etInput = (EditText) findViewById(R.id.etInput);
        
        Button btnOK = (Button) findViewById(R.id.btnOK);
        
        btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvGetInput.setText(etInput.getText());
			}
		});
    }

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.rbLeft:
			tvGetInput.setGravity(Gravity.LEFT);
			break;
		case R.id.rbCenter:
			tvGetInput.setGravity(Gravity.CENTER);
			break;
		case R.id.rbRight:
			tvGetInput.setGravity(Gravity.RIGHT);
			break;
		case R.id.rbNormal:
			tvGetInput.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), Typeface.NORMAL);
			break;
		case R.id.rbBold:
			tvGetInput.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.BOLD);
			break;
		case R.id.rbItalic:
			tvGetInput.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC), Typeface.ITALIC);
			break;
		}
	}
}
