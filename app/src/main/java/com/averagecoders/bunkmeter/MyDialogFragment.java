package com.averagecoders.bunkmeter;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.averagecoders.bunkmeter.SqlContract.FeedEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyDialogFragment extends DialogFragment {

    private EditText subjectName, tBunk, tClass;
    private Button yes, no;
    static ArrayList<DataModel> itemIds = new ArrayList<>();

    // Empty constructor required for DialogFragment
    public MyDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.opendialog, container);
        subjectName = (EditText) view.findViewById(R.id.username);
        tBunk = (EditText) view.findViewById(R.id.bunked);
        tClass = (EditText) view.findViewById(R.id.total);
        // set this instance as callback for editor action
        /*SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_ONE,
                FeedEntry.COLUMN_TWO,
                FeedEntry.COLUMN_THREE
        };

                // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_ONE + " ASC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        //Add the result


        *//* getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Please enter username");
        getDialog().getWindow().setLayout(600,600);
       *//*
        */yes = (Button) view.findViewById(R.id.OK);
        no = (Button) view.findViewById(R.id.Discard);
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Create a new map of values, where column names are the keys
                int b = Integer.parseInt(tBunk.getText().toString());
                int c = Integer.parseInt(tClass.getText().toString());
                if(b<c){
                ContentValues values = new ContentValues();
                values.put(FeedEntry.COLUMN_ONE, subjectName.getText().toString());
                values.put(FeedEntry.COLUMN_TWO, tBunk.getText().toString());
                values.put(FeedEntry.COLUMN_THREE, tClass.getText().toString());
            // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
                db.close();
                subjectName.setText("");
                tBunk.setText("");
                tClass.setText("");
                MainActivity.adapter.clear();
                mainRefresh();
                getDialog().dismiss();
              }else{
                  subjectName.setText("");
                  tBunk.setText("");
                  tClass.setText("");
                  MainActivity.adapter.clear();
                  mainRefresh();

              }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDialog().dismiss();
            }
        });
        /*while (cursor.moveToNext()) {
            itemIds.add(new DataModel(cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            Log.v("Subject Name", cursor.getString(1));
        }
        Log.v("Array List", itemIds.toString());
        cursor.close();
*/
        return view;
    }

    public  void mainRefresh(){
        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_ONE,
                FeedEntry.COLUMN_TWO,
                FeedEntry.COLUMN_THREE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry._ID + " ASC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        while (cursor.moveToNext()) {
            itemIds.add(new DataModel(cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            Log.v("Subject Name", cursor.getString(1));
        }
        Log.v("Main Activity", itemIds.toString());
        cursor.close();

    }

    public static void updateRecord(int position){


    }
    public static ArrayList<DataModel> getSubjects() {
        return itemIds;
    }
}
