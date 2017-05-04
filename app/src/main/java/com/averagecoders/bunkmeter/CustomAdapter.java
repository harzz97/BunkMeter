package com.averagecoders.bunkmeter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.averagecoders.bunkmeter.SqlContract.FeedEntry;

import java.util.ArrayList;

import static com.averagecoders.bunkmeter.MyDialogFragment.itemIds;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;
    LayoutInflater inflater = LayoutInflater.from(getContext());
    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView Current;
        TextView Total;
        ImageView info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.rowitems, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int value =0;
        int position=(Integer) v.getTag();
        position++;
/*
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;
*/
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.Total = (TextView) v.findViewById(R.id.totalclass);
        switch (v.getId())
        {
            case R.id.Increment:
                Log.v("Position ",""+position);
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Filter results WHERE "title" = 'My Title'
                String selection = FeedEntry._ID + " =? ";
                String[] selectionArgs = { ""+position };
                String[] projection = {FeedEntry.COLUMN_TWO};
                Cursor cursor = db.query(FeedEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                while (cursor.moveToNext()) {
                    Log.v("Current Number ", cursor.getString(0));
                    value = cursor.getInt(0);
                }
                ContentValues values = new ContentValues();
                values.put(FeedEntry.COLUMN_TWO, value+1);

                // Which row to update, based on the title
                int count = db.update(
                        FeedEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                MainActivity.adapter.clear();
                mainRefresh();
                break;
        }
    }
        private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.rowitems, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.Current = (TextView) convertView.findViewById(R.id.current);
            viewHolder.Total = (TextView) convertView.findViewById(R.id.totalclass);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.Increment);
            viewHolder.info.setOnClickListener(this);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        if (dataModel != null) {
            viewHolder.txtName.setText(String.valueOf(dataModel.getName()));
            viewHolder.Current.setText(String.valueOf(dataModel.getBunked()));
            viewHolder.Total.setText(String.valueOf(dataModel.getTclass()));

        }
        //viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
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


}