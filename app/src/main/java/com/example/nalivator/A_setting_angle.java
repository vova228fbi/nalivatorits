package com.example.nalivator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class A_setting_angle extends AppCompatActivity {
    //Кнопка выбора количества рюмок----------------------------------------------------------------
    Spinner btn_vibrat_kolvo;
    //Кнопка отменить-------------------------------------------------------------------------------
    Button btn_back_lay_setting_angle;
    //Кнопка отменить-------------------------------------------------------------------------------
    Button b_save_lay_setting_angle;
    //Текст номера рюмки----------------------------------------------------------------------------
    private static String TITLE = "catname";
    //Текст угла рюмки----------------------------------------------------------------------------
    private static String STR_angle = "Проверить (180)";
    //Спискок рюмок---------------------------------------------------------------------------------
    ListView list_of_glasses;
    ArrayList<HashMap<String, Object>> cList;

    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter My_BluetoothAdapter;
    private ArrayList<String> spinnerArrayAdapter = new ArrayList<String>();
    private static final int REQUEST_ENABLE_BT = 1;
    public static BluetoothSocket clientSocket;
    private A_setting_angle.ConnectedThread threadCommand;

    int kolvoR = -1;
    ArrayList<Integer> angelR;
    ArrayList<Integer> angelRTHIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_setting_angle);
        list_of_glasses = (ListView) findViewById(R.id.list_of_glasses);




        Intent intenttet = getIntent();
        kolvoR = Integer.parseInt(intenttet.getStringExtra("kolvoR"));


/*
        if (kolvoR != -1)
        {

            int selectedItemPositionvfvf = kolvoR-2;
            String[] choose = getResources().getStringArray(R.array.list_chenge_number_of_glassesanimals);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ваш выбор: " + choose[selectedItemPositionvfvf], Toast.LENGTH_SHORT);
            toast.show();






            cList = new ArrayList<>();
            HashMap<String, Object> hMap;
            for (int ii = 0; ii<=selectedItemPositionvfvf + 1; ii++)
            {
                int fdsf = ((((180 * ii )/ (selectedItemPositionvfvf + 1))/5)*5);
                hMap = new HashMap<>();
                hMap.put(TITLE, "Рюмка " + (ii+1));
                hMap.put(STR_angle, "Проверить (" + fdsf + ")" );
                cList.add(hMap);
                angelR.add(fdsf);
            }
            SimpleAdapter my_adapter = new SimpleAdapter(A_setting_angle.this, cList,
                    R.layout.layo_list_glasses, new String[]{TITLE, STR_angle},
                    new int[]{R.id.textview_title, R.id.button_chek});
            list_of_glasses.setAdapter(my_adapter);
        }
*/














        //Привязываю кнопки-------------------------------------------------------------------------
        btn_vibrat_kolvo = (Spinner) findViewById(R.id.view_chenge_number_of_glasses);
        btn_back_lay_setting_angle = (Button) findViewById(R.id.btn_back_setting_volume);
        b_save_lay_setting_angle = (Button) findViewById(R.id.b_save_setting_volume);
        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    //Событие кнопки Отменить-------------------------------------------------------
                    case R.id.btn_back_setting_volume:
                        finish();
                        break;
                    //Событие кнопки Сохранить------------------------------------------------------
                    case R.id.b_save_setting_volume:
                        Intent intent = new Intent();
                        intent.putExtra("kolvoR", Integer.toString(kolvoR+2));
                        intent.putExtra("angelR", angelRTHIS);







                        setResult(RESULT_OK, intent);
                        finish();
                        break;

                }
            }
        };
        //Привязываю слушателя к кнопкам------------------------------------------------------------
        btn_back_lay_setting_angle.setOnClickListener(onClickListener);
        b_save_lay_setting_angle.setOnClickListener(onClickListener);












        // Настраиваем адаптер
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.list_chenge_number_of_glassesanimals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Вызываем адаптер
        btn_vibrat_kolvo.setAdapter(adapter);
        if (kolvoR != -1)

            btn_vibrat_kolvo.setSelection(kolvoR - 2);

        else
            {
            btn_vibrat_kolvo.setSelection(0);



        }
/*




        cList = new ArrayList<>();
        HashMap<String, Object> hMap;
        hMap = new HashMap<>();
        hMap.put(TITLE, "Рюмка 1");
        hMap.put(STR_angle, "Проверить (0)");
        cList.add(hMap);
        hMap = new HashMap<>();
        hMap.put(TITLE, "Рюмка 2");
        hMap.put(STR_angle, "Проверить (180)");
        cList.add(hMap);
        SimpleAdapter my_adapter = new SimpleAdapter(this, cList,
                R.layout.layo_list_glasses, new String[]{TITLE, STR_angle},
                new int[]{R.id.textview_title, R.id.button_chek});
        list_of_glasses.setAdapter(my_adapter);

*/

        btn_vibrat_kolvo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_chenge_number_of_glassesanimals);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
                kolvoR = selectedItemPosition;


                boolean tsfd=false;



                if (angelR == null)
                {
                    angelRTHIS = new ArrayList<Integer>( 2);







                }
                else {

                    if (angelR.size() == selectedItemPosition) {
                        tsfd = true;
                        angelRTHIS = angelR;
                    } else
                        angelRTHIS = new ArrayList<Integer>(kolvoR + 2);
                }




                cList = new ArrayList<>();
                HashMap<String, Object> hMap;
                for (int ii = 0; ii<=selectedItemPosition + 1; ii++)
                {
                    int itsMyAngle;
                    if (tsfd)
                        itsMyAngle = angelRTHIS.get(ii);
                    else
                        itsMyAngle = ((((180 * ii )/ (selectedItemPosition + 1))/5)*5);
                    hMap = new HashMap<>();
                    hMap.put(TITLE, "Рюмка " + (ii+1));
                    hMap.put(STR_angle, "Проверить (" + itsMyAngle + ")" );
                    cList.add(hMap);
                    angelRTHIS.add(itsMyAngle);
                }
                SimpleAdapter my_adapter = new SimpleAdapter(A_setting_angle.this, cList,
                        R.layout.layo_list_glasses, new String[]{TITLE, STR_angle},
                        new int[]{R.id.textview_title, R.id.button_chek});
                list_of_glasses.setAdapter(my_adapter);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    public void btn_Click_plus(View v)
    {
        int position = list_of_glasses.getPositionForView(v);

        String stringrjgser = cList.toArray()[position].toString();
        int end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(end_position + 1);
        int start_position = stringrjgser.indexOf("(");
        end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(start_position + 1, end_position);
        int my_int_here = Integer.parseInt(stringrjgser);
        my_int_here = my_int_here + 5;
        if (my_int_here == 185)
            my_int_here = 0;
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put(TITLE, "Рюмка " + Integer.toString(position));
        hMap.put(STR_angle, "Проверить (" +  Integer.toString(my_int_here) + ")");
        cList.set(position, hMap);
        angelRTHIS.set(position, my_int_here);
        SimpleAdapter my_adapter = new SimpleAdapter(A_setting_angle.this, cList,
                R.layout.layo_list_glasses, new String[]{TITLE, STR_angle},
                new int[]{R.id.textview_title, R.id.button_chek});
        list_of_glasses.setAdapter(my_adapter);



        String go_to_arduino = Integer.toString(my_int_here) + ".";
        threadCommand = new ConnectedThread(MainActivity.clientSocket);
        threadCommand.run(go_to_arduino);



    }
    public void btn_Click_chek(View v)
    {
        int position = list_of_glasses.getPositionForView(v);

        String stringrjgser = cList.toArray()[position].toString();
        int end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(end_position + 1);
        int start_position = stringrjgser.indexOf("(");
        end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(start_position + 1, end_position);




        String go_to_arduino = stringrjgser + ".";
        threadCommand = new ConnectedThread(MainActivity.clientSocket);
        threadCommand.run(go_to_arduino);




    }
    public void btn_Click_min(View v)
    {
        int position = list_of_glasses.getPositionForView(v);

        String stringrjgser = cList.toArray()[position].toString();
        int end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(end_position + 1);
        int start_position = stringrjgser.indexOf("(");
        end_position = stringrjgser.indexOf(")");
        stringrjgser = stringrjgser.substring(start_position + 1, end_position);
        int my_int_here = Integer.parseInt(stringrjgser);
        my_int_here = my_int_here - 5;
        if (my_int_here == -5)
            my_int_here = 180;
        HashMap<String, Object> hMap = new HashMap<>();
        hMap.put(TITLE, "Рюмка " + Integer.toString(position));
        hMap.put(STR_angle, "Проверить (" +  Integer.toString(my_int_here) + ")");
        cList.set(position, hMap);
        angelRTHIS.set(position, my_int_here);
        SimpleAdapter my_adapter = new SimpleAdapter(A_setting_angle.this, cList,
                R.layout.layo_list_glasses, new String[]{TITLE, STR_angle},
                new int[]{R.id.textview_title, R.id.button_chek});
        list_of_glasses.setAdapter(my_adapter);





        String go_to_arduino = Integer.toString(my_int_here) + ".";
        threadCommand = new ConnectedThread(MainActivity.clientSocket);
        threadCommand.run(go_to_arduino);
    }









    private void connect_to_Arduino(String my_add_bluetooth) {


        //через костыль получаем адрес
        String itemMAC =  my_add_bluetooth;
        //получаем класс с информацией об устройстве
        BluetoothDevice connectDevice = mBluetoothAdapter.getRemoteDevice(itemMAC);
        try {
            //генерируем socket - поток, через который будут посылаться данные
            Method m = connectDevice.getClass().getMethod(
                    "createRfcommSocket", new Class[]{int.class});

            clientSocket = (BluetoothSocket) m.invoke(connectDevice, 1);
            clientSocket.connect();
            if(clientSocket.isConnected()) {
                //если соединение установлено, завершаем поиск
                mBluetoothAdapter.cancelDiscovery();
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
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