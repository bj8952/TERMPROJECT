package com.example.byoungjune.term;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by ByoungJune on 2016-12-06.
 */

public class BitmapCh {

    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    } // 비트맵 파일을 바이트코드로 변환


    public Bitmap byteArrayToBitmap(byte[] byteArray, int num){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        byteArray = null;
        return bitmap;
    }// 비트맵파일로 디코드
}
