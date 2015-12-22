package com.example.raintree.signature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by rainTree on 2015-12-02.
 */
public class PaintBoardActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_NAMEDIALOG = 1;

    PaintBoard board;
    Button colorBtn;
    Button penBtn;
    Button undoBtn;
    Button saveBtn;
    Button clearBtn;

    int mColor = 0xff000000;
    int mSize = 2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paintboard_main);

        LinearLayout boardLayout = (LinearLayout) findViewById(R.id.boardLayout);
        colorBtn = (Button) findViewById(R.id.colorBtn);
        penBtn = (Button) findViewById(R.id.penBtn);
        undoBtn = (Button) findViewById(R.id.undoBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        board = new PaintBoard(this);
        board.setLayoutParams(params);
        board.setPadding(2, 2, 2, 2);

        boardLayout.addView(board);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ColorPaletteDialog.listener = new OnColorSelectedListener() {
                    public void onColorSelected(int color) {
                        mColor = color;
                        board.updatePaintProperty(mColor, mSize);
                    }
                };
                // show color palette dialog
                Intent intent = new Intent(getApplicationContext(), ColorPaletteDialog.class);
                startActivity(intent);

            }
        });

        penBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                PenPaletteDialog.listener = new OnPenSelectedListener() {
                    public void onPenSelected(int size) {
                        mSize = size;
                        board.updatePaintProperty(mColor, mSize);
                    }
                };


                // show pen palette dialog
                Intent intent = new Intent(getApplicationContext(), PenPaletteDialog.class);
                startActivity(intent);

            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                board.undo();

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NameDialog.class);
                startActivityForResult(intent, REQUEST_CODE_NAMEDIALOG);

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //clear
                board.clear();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        if(requestCode == REQUEST_CODE_NAMEDIALOG){
            if(resultCode == 1){
                String lastName = Data.getExtras().getString("lastname");
                String firstName = Data.getExtras().getString("firstname");
                String dateNTime = DateFormat.getDateTimeInstance().format(new Date());
                Log.v("PaintBoardActivity", "lastname: " + lastName + " firstname: " + firstName +  "date: " + dateNTime);
                //save
                String fileName = board.save();

                String SQL = "insert into " + SignDatabase.TABLE_SIGN +
                        "(URI, USERLASTNAME, USERFIRSTNAME, DATE) values("
                        + "'" + fileName + "',"
                        + "'" + lastName + "',"
                        + "'" + firstName + "',"
                        + "'" + dateNTime + "')";
                if (MainActivity.mDatabase != null) {
                    MainActivity.mDatabase.execSQL(SQL);

                }
                finish();
            }
        }
    }

}
