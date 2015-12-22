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

/**
 * Created by rainTree on 2015-12-04.
 */
public class NameDialog extends Activity {
    EditText lastName, firstName;
    Button okBtn, cancelBtn;
    String lastNameStr, firstNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_dialog);

        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int new_window_width = screen_width * 90 / 100;

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        getWindow().setAttributes(layout);

        this.setTitle("Please enter a name.");
        this.setFinishOnTouchOutside(false);

        lastName = (EditText) findViewById(R.id.lastname);
        firstName = (EditText) findViewById(R.id.firstname);
        okBtn = (Button) findViewById(R.id.okBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        lastName.setWidth(layout.width);
        firstName.setWidth(layout.width);

        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lastNameStr = lastName.getText().toString();
                firstNameStr = firstName.getText().toString();

                if (lastNameStr.length() == 0 || firstNameStr.length() == 0) {
                    if (lastNameStr.length() == 0) {
                        lastName.setHintTextColor(Color.RED);
                        lastName.setHint("* Mandatory Field");
                    }
                    if (firstNameStr.length() == 0) {
                        firstName.setHintTextColor(Color.RED);
                        firstName.setHint("* Mandatory Field");
                    }
                } else {
                    Log.v("NameDialog", "lastname: " + lastNameStr + " firstname: " + firstNameStr);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("lastname", lastNameStr);
                    resultIntent.putExtra("firstname", firstNameStr);
                    setResult(1, resultIntent);

                    finish();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // dispose this activity
                finish();
            }
        });

    }

}