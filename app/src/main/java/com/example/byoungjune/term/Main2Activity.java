package com.example.byoungjune.term;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Main2Activity extends Activity implements OnMapReadyCallback {



    ArrayList<LatLng> Loc = new ArrayList<LatLng>();


    LatLng L1 = new LatLng(37.6, 127.1);
    LatLng L2 = new LatLng(37.601, 127.101);
    LatLng L3 = new LatLng(37.601, 127.102);
    LatLng L4 = new LatLng(37.602, 127.102);
    LatLng L5 = new LatLng(37.604, 127.103);




    private GoogleMap googleMap;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        Button btn_next = (Button)findViewById(R.id.btn_next2);
        btn_next.setOnClickListener(new View.OnClickListener(){

                                        @Override
                                        public void onClick(View v){
                                            Intent intent = new Intent(
                                                    getApplicationContext(), // 현재 화면의 제어권자이다.
                                                    MainActivity.class); // 넘어갈 클래스를 지정한다.
                                            startActivity(intent); // 다음 화면으로 넘어간다.

                                        }
                                    }
        );// 화면이동

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap2);
        mapFragment.getMapAsync(this);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "LoggerDB.db", null, 1);

        Loc = dbHelper.getPosition();

        Cursor cursor;


        SQLiteDatabase db = dbHelper.getWritableDatabase();



        cursor = db.rawQuery("SELECT * FROM LOGGER", null);

        startManagingCursor(cursor);


        SimpleCursorAdapter adapter;

        adapter = new SimpleCursorAdapter(this,
                R.layout.itemlayout , //아이템레이아웃.xml을 만들어서 사용함
                cursor,
                new String[] {"create_at", "item" ,"event"}, new int[] { R.id.text1,R.id.text2,R.id.text3 });



        ListView list = (ListView) findViewById(R.id.list_view);

        list.setAdapter(adapter);

    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        googleMap.addMarker(new MarkerOptions().position(L1).title("L1"));

        googleMap.addMarker(new MarkerOptions().position(L2).title("L2"));

        googleMap.addMarker(new MarkerOptions().position(L3).title("L3"));

        googleMap.addMarker(new MarkerOptions().position(L4).title("L4"));

        googleMap.addMarker(new MarkerOptions().position(L5).title("L5"));

        googleMap.addPolyline(new PolylineOptions().addAll(Loc).width(5).color(Color.GREEN)); // 선으로 표시

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(L4, 5.8f));

    }
}
