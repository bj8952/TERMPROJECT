package com.example.byoungjune.term;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity implements OnMapReadyCallback, LocationListener {


    public static final int CAMERA_REQUEST = 10;
    private ImageView imgSpecimenPhoto;
    Bitmap cameraImage = null;
    BitmapCh btb = new BitmapCh();/////비트맵 db에 저장하기 위해 바이트로 변환
    byte[] ImgTB;


    Spinner todoSpinner;
    String spin_text;

    LocationManager m_lmLocation;
    List<String> m_lstProviders;

    double lat = 37.527089;
    double lon = 127.028480;


    LatLng SEOUL = new LatLng(lat, lon);

    private GoogleMap googleMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    ////사진 불러오기 기능
    public void btnTakePhotoClicked(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //유저가 오케이를 눌렀으면 실행
        if(resultCode== RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // we are hearing back from camera

                cameraImage = (Bitmap)data.getExtras().get("data");

                imgSpecimenPhoto.setImageBitmap(cameraImage);

                ImgTB= btb.bitmapToByteArray(cameraImage); // 비트맵을 바이트로 저장
            }
        }
    }
////////////////////////

    @Override
    public void onMapReady(final GoogleMap map) {

        googleMap = map;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 17.0f));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


           startActivity(new Intent(this, SplashActivity.class));



        //카메라
        imgSpecimenPhoto = (ImageView) findViewById(R.id.imgSpecimenPhoto);
        Button btn_Photo = (Button)findViewById(R.id.btnTakePhoto);


        //화면넘기기
        Button btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener(){

                                        @Override
                                        public void onClick(View v){
                                            Intent intent = new Intent(
                                                    getApplicationContext(), // 현재 화면의 제어권자이다.
                                                    Main2Activity.class); // 넘어갈 클래스를 지정한다.
                                            startActivity(intent); // 다음 화면으로 넘어간다.

                                        }
                                    }
        );
        //화면이동 버튼

        Button btn_3 = (Button)findViewById(R.id.btn_4);
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


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "LoggerDB.db", null, 1);
        final EditText eventT = (EditText) findViewById(R.id.editText2);

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();
                Date d = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",java.util.Locale.getDefault());

                spin_text = todoSpinner.getSelectedItem().toString(); // 선택된 스피너

                String date = dateFormat.format(d);
                String item = spin_text;
                double Lat = lat;
                double Lon = lon;
                String event = eventT.getText().toString();


                dbHelper.insert(date, item, Lat, Lon, event,ImgTB);

                Toast.makeText(MainActivity.this, "제출하였습니다.", Toast.LENGTH_SHORT).show();


            }
       } );

        //SQLite


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        m_lmLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        m_lstProviders = m_lmLocation.getProviders(true);

        //스피너구현
        todoSpinner = (Spinner) findViewById(R.id.todo_spinner);
        ArrayAdapter todoAdapter = ArrayAdapter.createFromResource(this,
                R.array.todo_list, android.R.layout.simple_spinner_item);
        todoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoSpinner.setAdapter(todoAdapter);


        //스피너 구현

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 모든 위치 제공자에 위치 갱신 리스너를 등록한다.
        for (String strName : m_lstProviders) {
            // QQQ: 시간, 거리를 0 으로 설정하면 가급적 자주 위치 정보가 갱신되지만 베터리 소모가 많을 수 있다.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            m_lmLocation.requestLocationUpdates(strName, 0, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 위치 제공자에 위치 갱신 리스터를 삭제한다.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        m_lmLocation.removeUpdates(this);
    }

    // ================================================================
    // LocationListener

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();

        LatLng S = new LatLng(lat, lon);

        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(S).title("현재위치"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(S, 17.0f));

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}



