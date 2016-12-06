package com.example.byoungjune.term;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ByoungJune on 2016-11-28.
 */

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* LOGGER 라는 테이블명 / 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE LOGGER (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, create_at TEXT, lat INTEGER, lon INTEGER , event TEXT , photo BLOB );");


    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String create_at, String item, double lat, double lon , String event , byte[] photo) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement p = db.compileStatement("INSERT INTO LOGGER VALUES(?,?,?,?,?,?,?);");

        // DB에 입력한 값으로 행 추가
        // db.execSQL("INSERT INTO LOGGER VALUES(null, '" + item + "', '" + create_at+ "', "+lat +","+ lon + ", '"+event+ "','"+ photo+"');");
        p.bindNull(1);
        p.bindString(2,create_at);
        p.bindString(3,item);
        p.bindDouble(4,lat);
        p.bindDouble(5,lon);
        p.bindString(6,event);
        p.bindBlob(7,photo);
        p.execute();
        p.close();
        db.close();
    }

    public void update(String item, double price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE LOGGER SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM LOGGER WHERE item='" + item + "';");
        db.close();
    }


    public ArrayList<LatLng> getPosition() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<LatLng> result = new ArrayList<LatLng>();



        // DB에서 위도와 경도를 출력하여 LatLng로 저장하고 ArrayList에 모두 저장함
        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER", null);
        while (cursor.moveToNext()) {
           double lat = cursor.getDouble(3);
           double lon = cursor.getDouble(4);
            LatLng A = new LatLng(lat,lon);

           result.add(A);


       }

        //테스트용
        LatLng L1 = new LatLng(37.6, 127.1);
        LatLng L2 = new LatLng(37.601, 127.101);
        LatLng L3 = new LatLng(37.601, 127.102);
        LatLng L4 = new LatLng(37.602, 127.102);
        LatLng L5 = new LatLng(37.604, 127.103);

        result.add(L1);
        result.add(L2);
        result.add(L3);
        result.add(L4);
        result.add(L5);



        return result;
    }
}


