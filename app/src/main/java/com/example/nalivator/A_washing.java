package com.example.nalivator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class A_washing extends AppCompatActivity {
    //Кнопка закончить промывку---------------------------------------------------------------------
    Button b_close_washing;
    //Кнопка промывка-------------------------------------------------------------------------------
    Button b_start_washing;

    private A_washing.ConnectedThread threadCommand;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_washing);
        //Привязываю кнопки-------------------------------------------------------------------------
        b_close_washing = (Button) findViewById(R.id.b_save_setting_volume);
        b_start_washing = (Button) findViewById(R.id.b_start_washing);
        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    //Событие кнопки закончить промывку---------------------------------------------
                    case R.id.b_save_setting_volume:
                        finish();
                        break;
                }
            }
        };

        String go_to_arduino = "0.";
        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
        threadCommand = new A_washing.ConnectedThread(MainActivity.clientSocket);
        threadCommand.run(go_to_arduino);
        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete






        //Считаем время удерживания кнопки
        View.OnTouchListener onOnTouchListener = new View.OnTouchListener() {
            long startTime = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие



                        String go_to_arduino = "Начать промывку.";
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new A_washing.ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run(go_to_arduino);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete






                        startTime = System.currentTimeMillis();
                        b_start_washing.setText("Идёт промывка");
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание



                        go_to_arduino = "Закончить промывку.";
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new A_washing.ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run(go_to_arduino);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete







                        b_start_washing.setText("Промывка");
                        long totalTime = System.currentTimeMillis() - startTime;
                        int totalSecunds = (int)(totalTime / 1000);

                        NumberFormat nf = new DecimalFormat("000");
                        String mytime = totalSecunds + "," + nf.format(totalTime - totalSecunds * 1000);

                        //String[] choose = getResources().getStringArray(R.array.list_chenge_number_of_glassesanimals);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Промывка длилась " + mytime + " сек.", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                }
                return true;
            }
        };

        //Привязываю слушателя к кнопкам------------------------------------------------------------
        b_close_washing.setOnClickListener(onClickListener);
        b_start_washing.setOnTouchListener((View.OnTouchListener) onOnTouchListener);
    }



    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket btSocket) {
            //получаем сокет
            this.socket = btSocket;
            //создаем стрим - нить для отправки данных на ардуино
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
            } catch(Exception e) {}
            outputStream = os;
        }

        public void run(String rjggjr) {
            try {
                outputStream.write(rjggjr.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}