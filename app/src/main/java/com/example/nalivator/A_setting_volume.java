package com.example.nalivator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class A_setting_volume extends AppCompatActivity {
    //Кнопка сохранения записи----------------------------------------------------------------------
    Button b_save_setting_volume;
    //Кнопка отмены записи--------------------------------------------------------------------------
    Button btn_back_setting_volume;
    //Кнопка изменения записи-----------------------------------------------------------------------
    Button b_start_setting_volume;
    //Кнопка изменения записи-----------------------------------------------------------------------
    TextView textView;
    //Кнопка выбора количества рюмок----------------------------------------------------------------
    Spinner view_number_of_glasses;

    double timevolume = 0;

    String mytime = "0";

    private A_setting_volume.ConnectedThread threadCommand;

    int numberR = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_setting_volume);

        Intent intenttet = getIntent();
        if (intenttet.getStringExtra("timevolume")!=null) {
            timevolume = Double.parseDouble(intenttet.getStringExtra("timevolume"));
            mytime = Double.toString(timevolume);
        }



        //Привязываю кнопки-------------------------------------------------------------------------
        b_save_setting_volume = (Button) findViewById(R.id.b_save_setting_volume);
        btn_back_setting_volume = (Button) findViewById(R.id.btn_back_setting_volume);
        b_start_setting_volume = (Button) findViewById(R.id.b_start_setting_volume);
        textView = (TextView) findViewById(R.id.textView3);
        view_number_of_glasses = (Spinner) findViewById(R.id.view_number_of_glasses);


        textView.setText("Записано значение " + mytime + " секунд.");

        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.b_save_setting_volume:
                        Intent intent = new Intent();
                        intent.putExtra("timevolume", mytime);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    //Событие кнопки отменить запись------------------------------------------------
                    case R.id.btn_back_setting_volume:
                        finish();
                        break;
                }
            }
        };
        //Считаем время удерживания кнопки
        View.OnTouchListener onOnTouchListener = new View.OnTouchListener() {
            long startTime = 0;
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие



                        String go_to_arduino = "Начать промывку в рюмку " + numberR + ".";
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new A_setting_volume.ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run(go_to_arduino);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete






                        startTime = System.currentTimeMillis();
                        b_start_setting_volume.setText("Идёт запись");
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание



                        go_to_arduino = "Закончить промывку.";
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new A_setting_volume.ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run(go_to_arduino);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete







                        b_start_setting_volume.setText("Запись");
                            long totalTime = System.currentTimeMillis() - startTime;
                            int totalSecunds = (int)(totalTime / 1000);
                            NumberFormat nf = new DecimalFormat("000");
                            mytime = totalSecunds + "." + nf.format(totalTime - totalSecunds * 1000);
                            //String[] choose = getResources().getStringArray(R.array.list_chenge_number_of_glassesanimals);
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Запись шла: " + mytime + " сек.", Toast.LENGTH_SHORT);
                            toast.show();
                            textView.setText("Записанн значение " + mytime + " секунд.");
                        break;
                }
                return true;
            }
        };
        //Привязываю слушателя к кнопкам------------------------------------------------------------
        b_save_setting_volume.setOnClickListener(onClickListener);
        btn_back_setting_volume.setOnClickListener(onClickListener);
        b_start_setting_volume.setOnTouchListener((View.OnTouchListener) onOnTouchListener);


        // Настраиваем адаптер
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.list_number_of_glassesanimals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Вызываем адаптер
        view_number_of_glasses.setAdapter(adapter);
        view_number_of_glasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                numberR = selectedItemPosition;
                String go_to_arduino = "0.";
                //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                threadCommand = new A_setting_volume.ConnectedThread(MainActivity.clientSocket);
                threadCommand.run(go_to_arduino);
                //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

