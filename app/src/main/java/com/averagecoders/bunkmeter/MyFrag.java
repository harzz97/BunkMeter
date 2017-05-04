package com.averagecoders.bunkmeter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyFrag extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Subjects")
                .setMessage("This Message")
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Ok Done!",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.Discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"Discard!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    AlertDialog dialog = builder.create();
        return  dialog;

    }
    public  void onClickBtn(View view){
      EditText  editText = (EditText) view.findViewById(R.id.username);
        editText.setText("HArish");

    }

}
