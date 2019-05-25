package com.example.usecontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dislplayAllContact();
        displayAllFileMedia();
    }

    public void dislplayAllContact(){
        Uri uri = Uri.parse("content://contacts/people");
        List<String> list = new ArrayList<>();
        CursorLoader loader = new CursorLoader(this,uri,null,null,null,null);
        Cursor cursor = loader.loadInBackground();
        cursor.moveToFirst();
        while (cursor.isAfterLast()== false){
            String chuoi = "";
            String cotName  = ContactsContract.Contacts.DISPLAY_NAME;
            int hangName  = cursor.getColumnIndex(cotName);
            String name = cursor.getString(hangName);

            String cotID = ContactsContract.Contacts._ID;
            int hangID = cursor.getColumnIndex(cotID);
            String id = cursor.getString(hangID);


            chuoi = id + " - " + name;
            list.add(chuoi);
            cursor.moveToNext();
        }
        cursor.close();
        ArrayAdapter  arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        lvContact = (ListView) findViewById(R.id.lvContact);
        lvContact.setAdapter(arrayAdapter);

    }
    public void displayAllFileMedia(){
        String projection[] = {
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader loader = new CursorLoader(MainActivity.this,MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            String s = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

            cursor.moveToNext();
        }
        cursor.close();
    }
}
