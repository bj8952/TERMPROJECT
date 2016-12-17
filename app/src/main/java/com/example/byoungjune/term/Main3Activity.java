package com.example.byoungjune.term;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    TextView myOutput;
    TextView myRec;
    Button myBtnStart;
    Button myBtnRec;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        myOutput = (TextView) findViewById(R.id.time_out);
        myRec = (TextView) findViewById(R.id.record);
        myBtnStart = (Button) findViewById(R.id.btn_start);
        myBtnRec = (Button) findViewById(R.id.btn_rec);

        final TextView P = (TextView)findViewById(R.id.purpose);
        final EditText eP = (EditText)findViewById(R.id.editText3);
        final EditText eT = (EditText)findViewById(R.id.editText4);

        Button btn_7 = (Button)findViewById(R.id.button7);
        btn_7.setOnClickListener(new View.OnClickListener(){

                                        @Override
                                        public void onClick(View v){
                                            Intent intent = new Intent(
                                                    getApplicationContext(), // 현재 화면의 제어권자이다.
                                                    MainActivity.class); // 넘어갈 클래스를 지정한다.
                                            startActivity(intent); // 다음 화면으로 넘어간다.

                                        }
                                    }
        );// 화면이동

        Button btn_4 = (Button)findViewById(R.id.button4);
        btn_4.setOnClickListener(new View.OnClickListener(){

                                     @Override
                                     public void onClick(View v){
                                         Intent intent = new Intent(
                                                 getApplicationContext(), // 현재 화면의 제어권자이다.
                                                 Main2Activity.class); // 넘어갈 클래스를 지정한다.
                                         startActivity(intent); // 다음 화면으로 넘어간다.

                                     }
                                 }
        );// 화면이동

        Button btn_10 = (Button)findViewById(R.id.button10); // 목표입력버튼
        btn_10.setOnClickListener(new View.OnClickListener(){

                                     @Override
                                     public void onClick(View v){


                                         P.setText("오늘의 목표 : "+eP.getText()+" | 시간 : "+eT.getText());

                                     }
                                 }
        );
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void myOnClick(View v){
        switch(v.getId()){
            case R.id.btn_start:
                switch(cur_Status){
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText("멈춤");
                        myBtnRec.setEnabled(true);
                        cur_Status = Run;
                        break;
                    case Run:
                        myTimer.removeMessages(0);
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        myBtnRec.setText("리셋");
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        myBtnStart.setText("멈춤");
                        myBtnRec.setText("기록");
                        cur_Status = Run;
                        break;


                }
                break;
            case R.id.btn_rec:
                switch(cur_Status){
                    case Run:

                        String str = myRec.getText().toString();
                        str +=  String.format("%d. %s\n",myCount,getTimeOut());
                        myRec.setText(str);
                        myCount++; //카운트 증가

                        break;
                    case Pause:
                        //핸들러를 멈춤
                        myTimer.removeMessages(0);

                        myBtnStart.setText("시작");
                        myBtnRec.setText("기록");
                        myOutput.setText("00:00:00");

                        cur_Status = Init;
                        myCount = 1;
                        myRec.setText("");
                        myBtnRec.setEnabled(false);
                        break;


                }
                break;

        }
    }

    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            myOutput.setText(getTimeOut());
            myTimer.sendEmptyMessage(0);
        }
    };
    String getTimeOut(){
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);
        return easy_outTime;

    }


}
