package com.averagecoders.bunkmeter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.averagecoders.bunkmeter.SqlContract.FeedEntry;

import java.util.ArrayList;

import static android.R.id.list;
import static com.averagecoders.bunkmeter.MyDialogFragment.itemIds;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels = new ArrayList<>();
    static  ListView listView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    static CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataModels =MyDialogFragment.getSubjects();
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        Log.v("Main Activity",dataModels.toString());
        listView.setAdapter(adapter);
        updateList();
        listView.setDivider(new ColorDrawable(android.R.color.transparent));
        listView.setDividerHeight(10);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Log.v("Selected Item" ,""+position);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.longpress,null);
                TextView modify = (TextView)mView.findViewById(R.id.Modify);
                TextView delete = (TextView)mView.findViewById(R.id.Delete);
                mBuilder.setView(mView);
AlertDialog dialog = mBuilder.create();
                dialog.show();
                return false;
            }
        });

    }

    public  void updateList(){
             DbHelper dbHelper = new DbHelper(getBaseContext());
             SQLiteDatabase db = dbHelper.getWritableDatabase();
             String[] projection = {
                     FeedEntry._ID,
                     FeedEntry.COLUMN_ONE,
                     FeedEntry.COLUMN_TWO,
                     FeedEntry.COLUMN_THREE
             };

             // How you want the results sorted in the resulting Cursor
             String sortOrder =
                     FeedEntry._ID+ " ASC";

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
                 Log.v("Subject Name", cursor.getString(0));
             }
             Log.v("Main Activity", itemIds.toString());
             cursor.close();

         }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        switch(item.getItemId()){
            case R.id.Add:{
                MyDialogFragment alertDialogFragment = new MyDialogFragment();
                alertDialogFragment.show(manager, "fragment_edit_name");
                break;
            }
        }
        Toast.makeText(this, "Menu Item 1 selected"+item.getItemId(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void onFinishUserDialog(String user) {
        Toast.makeText(this, "Hello, " + user, Toast.LENGTH_SHORT).show();
    }

}
