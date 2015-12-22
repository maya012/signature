package com.example.raintree.signature;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by rainTree on 2015-12-03.
 */

public class SignListItemView extends LinearLayout {

    private ImageView itemSignImg;

    private TextView itemFirstName;

    private TextView itemLastName;

    private TextView itemDate;

    public SignListItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.signature_list, this, true);

        itemSignImg = (ImageView) findViewById(R.id.signImg);

        itemLastName = (TextView) findViewById(R.id.listview_lastname);

        itemFirstName = (TextView) findViewById(R.id.listview_firstname);

        itemDate = (TextView) findViewById(R.id.date);

    }

    public void setContents(int index, String data) {
        if (index == 0) {
            if (data == null || data.equals("-1") || data.equals("")) {
                itemSignImg.setImageBitmap(null);
            } else {
                itemSignImg.setImageURI(Uri.parse(data));
            }
        } else if (index == 1) {
            itemLastName.setText(data);
        } else if (index == 2) {
            itemFirstName.setText(data);
        } else if (index == 3) {
            itemDate.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
