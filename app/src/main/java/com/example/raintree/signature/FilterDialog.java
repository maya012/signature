package com.example.raintree.signature;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Created by rainTree on 2015-12-05.
 */
public class FilterDialog extends Activity {
    RadioGroup filterRGrp;
    Button okBtn;
    EditText keyWordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_dialog);

        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int new_window_width = screen_width * 90 / 100;

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        getWindow().setAttributes(layout);

        this.setFinishOnTouchOutside(false);

        filterRGrp = (RadioGroup) findViewById(R.id.filterRGrp);
        okBtn = (Button) findViewById(R.id.filterOKBtn);
        keyWordEditText = (EditText) findViewById(R.id.editKeyword);


        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int btnId = filterRGrp.getCheckedRadioButtonId();
                String keyword = keyWordEditText.getText().toString();
                Log.v("FilterDialog", "checked radio Btn id: " + filterRGrp.getCheckedRadioButtonId());
                if(btnId == R.id.noneRBtn){
                    setResult(1);
                    finish();
                }
                if(keyword.length() == 0) {
                    keyWordEditText.setHintTextColor(Color.RED);
                    keyWordEditText.setHint("* Mandatory Field");
                }else if(btnId != -1) {
                    String filterBy =  null;
                    if(btnId == R.id.fNameFilterRBtn) {
                        filterBy = "USERFIRSTNAME";
                    } else if(btnId == R.id.lNameFilterRBtn){
                        filterBy = "USERLASTNAME";
                    }else{
                        Log.e("FilterDialog", "Radio Button Id error");
                    }
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("keyword", keyword);
                    resultIntent.putExtra("filterBy", filterBy);
                    setResult(2, resultIntent);
                    finish();
                }
            }
        });

    }
}
