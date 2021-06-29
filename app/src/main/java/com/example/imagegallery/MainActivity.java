package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;

public class MainActivity extends AppCompatActivity {

    static ArrayList<ImageModel> imageArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (checkPermission()) {
            getImageFiles();
//            for (int i=0; i<imageArrayList.size() ; i++) {
//                Log.i("image" + i, imageArrayList.get(i).getImageUrl().toString());
//            }
        }

    }

    public void getImageFiles() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                ImageModel audioModel = new ImageModel();
                audioModel.setTitle(title);
                audioModel.setImageUrl(url);
                imageArrayList.add(audioModel);

            } while (cursor.moveToNext());
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, imageArrayList);
        recyclerView.setAdapter(adapter);
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        getImageFiles();
//                        for (int i=0; i<imageArrayList.size() ; i++) {
//                            Log.i("image" + i, imageArrayList.get(i).getTitle());
//                        }
                    }
                }
            }
        }
    }
}