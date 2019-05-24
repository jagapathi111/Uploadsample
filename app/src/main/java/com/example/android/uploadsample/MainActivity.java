package com.example.android.uploadsample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    Button btn;
    TextView textview, textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        textview = (TextView) findViewById(R.id.textview);
        textView2 = (TextView) findViewById(R.id.textView2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
                startActivityForResult(intent, 1212);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String displayName = null;
        int bytes = 0;
        double mb = 0;
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);


                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                 bytes = cursor.getInt(cursor.getColumnIndex(OpenableColumns.SIZE));
                                 mb = bytes*0.000001;
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();





                    }
                }
                break;
        }           textview.setText(displayName);
                    textView2.setText(String.format("%.2f", mb  )+ "mb");


        super.onActivityResult(requestCode, resultCode, data);
    }


}
