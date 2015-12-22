package com.example.raintree.signature;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_SORTDIALOG = 2;
    public static final int REQUEST_CODE_FILTERDIALOG = 3;
    public static final int REQUEST_CODE_SELECTEDITEMDIALOG = 4;

     // Signature List View
    ListView mSignListView;

     // Signature List Adapter
    SignListAdapter mSignListAdapter;

    public static SignDatabase mDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignListView = (ListView)findViewById(R.id.signList);
        mSignListAdapter = new SignListAdapter(this);
        mSignListView.setAdapter(mSignListAdapter);
        mSignListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewSign(position);
            }
        });

        Button newSignBtn = (Button) findViewById(R.id.newBtn);
        newSignBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaintBoardActivity.class);
                startActivity(intent);
            }
        });

        Button closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Button sortBtn = (Button) findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SortDialog.class);
                startActivityForResult(intent, REQUEST_CODE_SORTDIALOG);

            }
        });

        Button filterBtn = (Button) findViewById(R.id.filterBtn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilterDialog.class);
                startActivityForResult(intent, REQUEST_CODE_FILTERDIALOG);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        if(requestCode == REQUEST_CODE_SORTDIALOG){
            if(resultCode == 1){
                String order = Data.getExtras().getString("order");
                String sortBy = Data.getExtras().getString("sortBy");

                String SQL = "select _id, URI, USERLASTNAME, USERFIRSTNAME, DATE from SIGN order by "  + sortBy + " " + order;
                loadSignListData(SQL);
            }
        } else if(requestCode == REQUEST_CODE_FILTERDIALOG){
            if(resultCode == 1){
                String SQL = "select _id, URI, USERLASTNAME, USERFIRSTNAME, DATE from SIGN ";
                loadSignListData(SQL);
            }
            else if(resultCode == 2){
                String keyword = Data.getExtras().getString("keyword");
                String filterBy = Data.getExtras().getString("filterBy");

                String SQL = "select _id, URI, USERLASTNAME, USERFIRSTNAME, DATE from SIGN where " + filterBy + " like '%" + keyword + "%'";
                loadSignListData(SQL);
            }
        }else if (requestCode == REQUEST_CODE_SELECTEDITEMDIALOG){
            if(resultCode == 2){
                String uri = Data.getExtras().getString("uri");
                if(uri != null) {
                    String SQL = "delete from SIGN where URI = '" + uri + "'";
                    mDatabase.execSQL(SQL);

                    File file = new File(uri);
                    file.delete();

                    SQL = "select * from SIGN";
                    loadSignListData(SQL);
                }

            }
        }
    }
    protected void onStart() {
        // Open Database
        openDatabase();

        // Load Signature List
        String SQL = "select _id, URI, USERLASTNAME, USERFIRSTNAME, DATE from SIGN ";
        loadSignListData(SQL);

        super.onStart();
    }

    /**
     * Open or create Database
     */
    public void openDatabase() {
        // open database
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = SignDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Database is open.");
        } else {
            Log.d(TAG, "Database is not open.");
        }
    }

    /**
     * Load Signature List Data
     */
    public int loadSignListData(String SQL) {
        int recordCount = -1;
        if (mDatabase != null) {
            Cursor outCursor = mDatabase.rawQuery(SQL);

            if(outCursor == null)
            {
                Log.e(TAG, "Database RawQuery Error \n");
                return -1;
            }
            else {
                recordCount = outCursor.getCount();
                Log.d(TAG, "cursor count : " + recordCount + "\n");
            }
            mSignListAdapter.clear();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                String signId = outCursor.getString(0);
                String signUriStr = outCursor.getString(1);
                String lastNameStr = outCursor.getString(2);
                String firstNameStr = outCursor.getString(3);
                String dateStr = outCursor.getString(4);

                mSignListAdapter.addItem(new SignListItem(signId, signUriStr, lastNameStr, firstNameStr, dateStr));
            }
            outCursor.close();

            mSignListAdapter.notifyDataSetChanged();
        }
        return recordCount;
    }

    private void viewSign(int position) {
        SignListItem item = (SignListItem)mSignListAdapter.getItem(position);

        Intent intent = new Intent(getApplicationContext(), SelectedItemDialog.class);
        intent.putExtra("uri", item.getData(0).toString());
        intent.putExtra("lastname", item.getData(1).toString());
        intent.putExtra("firstname", item.getData(2).toString());
        intent.putExtra("date", item.getData(3).toString());
        startActivityForResult(intent, REQUEST_CODE_SELECTEDITEMDIALOG);
    }
}
