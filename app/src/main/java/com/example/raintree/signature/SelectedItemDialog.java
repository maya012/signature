package com.example.raintree.signature;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by rainTree on 2015-12-06.
 */
public class SelectedItemDialog extends Activity {
    Button emailBtn, deleteBtn;
    ImageView imgView;
    String firstname, lastname, date, uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecteditem_dialog);

        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int new_window_width = screen_width * 90 / 100;

        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        getWindow().setAttributes(layout);

        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        emailBtn = (Button) findViewById(R.id.sendEmailBtn);
        imgView = (ImageView) findViewById(R.id.signImageView);
        Intent intent = getIntent();
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        date = intent.getStringExtra("date");
        uri = intent.getStringExtra("uri");

        this.setTitle(firstname + " " + lastname);
        this.setFinishOnTouchOutside(false);

        if (uri == null || uri.equals("-1") || uri.equals("")) {
            imgView.setImageBitmap(null);
        } else {
            imgView.setImageURI(Uri.parse(uri));
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("uri", uri);
                setResult(2, resultIntent);
                finish();

            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("*/*");
                intent.setData(Uri.parse("mailto:"));

                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + uri));

                String subject = "Signature - " + firstname + " " + lastname + ", " + date;
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    setResult(1);
                    finish();
                }
            }
        });

    }
}
