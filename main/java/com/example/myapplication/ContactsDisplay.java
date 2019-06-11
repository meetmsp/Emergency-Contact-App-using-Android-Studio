package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsDisplay extends AppCompatActivity {

    ArrayList<com.example.myapplication.Contact> listContacts;
    ListView lvContacts;
    String permissionName= Manifest.permission.READ_CONTACTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ContextCompat.checkSelfPermission(this,
                    permissionName);
//        Log.e(Global.TAG, "Permission.......");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "Permission to record denied");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permissionName)) {
                    Log.e("permission", "dialog called");
                    //   details msg for use permission

                } else {
                    //makeRequest();
                    Log.e("permission", "dialog called::::::");
                    ActivityCompat.requestPermissions(this, new String[]{permissionName},
                            12);

                }

            }
            else {
                //  permission allow
                getAllContactInfo();
                Log.e("permission","  m make flag ::: " );
            }
        }
        else {
            //  lower m version
            getAllContactInfo();
            Log.e("permission"," make flag ::: " );
        }

    }

    public void getAllContactInfo(){
        listContacts = new com.example.myapplication.ContactFetcher(this).fetchAll();
        lvContacts = (ListView) findViewById(R.id.lvContacts);
        com.example.myapplication.ContactsAdapter adapterContacts = new com.example.myapplication.ContactsAdapter(this, listContacts);
        lvContacts.setAdapter(adapterContacts);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 12:
                //check if all permissions are granted
                boolean allgranted = false;
                Log.e("permission", " all granted permission :::  " + allgranted);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                    getAllContactInfo();

                }else {
                    allgranted = false;
                }
                Log.e("permission", " all granted permission :::  " + allgranted);
                break;

        }
    }
}


