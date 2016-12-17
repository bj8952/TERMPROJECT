package com.example.byoungjune.term;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DBAadapter extends CursorAdapter {



    public DBAadapter(Context context, Cursor c) {
        super(context, c,true); // true하면 리쿼리된다고
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        byte[] a= cursor.getBlob(cursor.getColumnIndex("photo"));

        BitmapCh btn = new BitmapCh();
        Bitmap b = btn.byteArrayToBitmap(a,2); // 이미지변환


        ImageView image = (ImageView) view.findViewById(R.id.imageView1);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        TextView text2 = (TextView) view.findViewById(R.id.text2);
        TextView text3 = (TextView) view.findViewById(R.id.text3);

         //image.setImageResource(R.drawable.ic_launcher);
        image.setImageBitmap(b);
        text1.setText(""+cursor.getString(cursor.getColumnIndex("create_at")));
        text2.setText(""+cursor.getString(cursor.getColumnIndex("item")));
        text3.setText(""+cursor.getString(cursor.getColumnIndex("event")));

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.itemlayout, parent, false);
        return v;
    }


}
