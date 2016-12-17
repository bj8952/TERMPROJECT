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
import android.widget.TextView;

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


    ListView list;
    DBHelper dbHelper;
    Cursor cursor;
    SQLiteDatabase db;

    ArrayList<LatLng> Loc = new ArrayList<LatLng>();

    DistanceMeasure dm;
    double dist = 0;



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

        dbHelper = new DBHelper(getApplicationContext(), "LoggerDB.db", null, 1);
        list = (ListView) findViewById(R.id.list);
         selectDB(); //DB가져오기




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

        Button btn_3 = (Button)findViewById(R.id.button5);
        btn_3.setOnClickListener(new View.OnClickListener(){

                                     @Override
                                     public void onClick(View v){
                                         Intent intent = new Intent(
                                                 getApplicationContext(), // 현재 화면의 제어권자이다.
                                                 Main3Activity.class); // 넘어갈 클래스를 지정한다.
                                         startActivity(intent); // 다음 화면으로 넘어간다.

                                     }
                                 }
        );
        //화면이동 버튼

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap2);
        mapFragment.getMapAsync(this);

        Loc = dbHelper.getPosition();


    }


    public void selectDB(){
        db = dbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM LOGGER", null);
        if(cursor.getCount() > 0) {
            startManagingCursor(cursor);
            DBAadapter dbAdapter = new DBAadapter(this, cursor);
            list.setAdapter(dbAdapter);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        for(int i =0 ; i < Loc.size() ; i++){

            googleMap.addMarker(new MarkerOptions().position(Loc.get(i)).title("TEST SPOT"));



        }

        for(int i =0 ; i < Loc.size()-1 ; i++){

            double a = Loc.get(i).latitude;
            double b = Loc.get(i).longitude;
            double c = Loc.get(i+1).latitude;
            double d = Loc.get(i+1).longitude;


            dist = dist +  dm.distance(a,b,c,d,'k');

        }

        googleMap.addPolyline(new PolylineOptions().addAll(Loc).width(5).color(Color.GREEN)); // 선으로 표시

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(L4, 5.8f));

        TextView DST = (TextView) findViewById(R.id.textView5);
        dist = Math.round(dist*100)*0.01;
        DST.setText("총 이동거리는 "+dist+"KM 입니다.");

    }
}
