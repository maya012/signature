package com.example.raintree.signature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by rainTree on 2015-12-05.
 */
public class SortDialog extends Activity {
    RadioGroup sortRGrp;
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_dialog);

        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int new_window_width = screen_width * 90 / 100;

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        getWindow().setAttributes(layout);

        this.setFinishOnTouchOutside(false);

        sortRGrp = (RadioGroup) findViewById(R.id.sortRGrp);
        okBtn = (Button) findViewById(R.id.sortOKBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int btnId = sortRGrp.getCheckedRadioButtonId();
                if(btnId != -1) {
                    String order = null;
                    String sortBy =  null;
                    if(btnId == R.id.fNameRBtnAsc) {
                        order = "asc";
                        sortBy = "USERFIRSTNAME";
                    } else if(btnId == R.id.fNameRBtnDesc){
                        order = "desc";
                        sortBy = "USERFIRSTNAME";
                    }else if (btnId == R.id.lNameRBtnAsc) {
                        order = "asc";
                        sortBy = "USERLASTNAME";
                    }else if (btnId == R.id.lNameRBtnDesc){
                        order = "desc";
                        sortBy = "USERLASTNAME";
                    }else if(btnId == R.id.dateRBtnAsc){
                        order = "asc";
                        sortBy = "DATE";
                    }else if (btnId == R.id.dateRBtnDesc){
                        order = "desc";
                        sortBy = "DATE";
                    }else{
                        Log.e("SortDialog", "Radio Button Id error");
                    }
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("order", order);
                    resultIntent.putExtra("sortBy", sortBy);
                    setResult(1, resultIntent);
                    finish();
                }
            }
        });

    }
}
