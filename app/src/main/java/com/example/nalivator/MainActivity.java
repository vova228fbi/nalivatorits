package com.example.nalivator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //Кнопка выбора блютуз-подключения--------------------------------------------------------------
    Spinner btn_vibrat_podkl;
    //Кнопка создания записи------------------------------------------------------------------------
    Button btn_create_record_file;
    //Кнопка редактирования записи------------------------------------------------------------------
    Button btn_chenge_record_file;
    //Кнопка открытия записи------------------------------------------------------------------------
    Button btn_open_record_file;
    //Кнопка промывки-------------------------------------------------------------------------------
    Button btn_clear_weather;

    private BluetoothAdapter mBluetoothAdapter;
    public static BluetoothSocket clientSocket;
    private ConnectedThread threadCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Привязываю кнопки-------------------------------------------------------------------------
        btn_vibrat_podkl = (Spinner) findViewById(R.id.vibrat_podkl);
        btn_create_record_file = (Button) findViewById(R.id.create_record_file);
        btn_chenge_record_file = (Button) findViewById(R.id.chenge_record_file);
        btn_open_record_file = (Button) findViewById(R.id.open_record_file);
        btn_clear_weather = (Button) findViewById(R.id.clear_weather);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "На вашем устройстве не поддерживается Bluetooth", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        //если разрешения получены (функция ниже)
        if(permissionGranted()) {
            //адаптер для управления блютузом
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothEnabled()) { //если блютуз включен (функция ниже)
               // findArduino(); //начать поиск устройства (функция ниже)
            }
        }

        final List<String> s = new ArrayList<String>();
        s.add("Нажмите для выбора");
        for(BluetoothDevice bt : pairedDevices)
            s.add(bt.getName() + ":" + bt.getAddress());
        final ArrayAdapter<?> adapternev = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, s);

        btn_vibrat_podkl.setAdapter(adapternev);

        btn_vibrat_podkl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                if (s.get(selectedItemPosition) == "Нажмите для выбора")
                {
                    //disconnect
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ваш выбор:" + s.get(selectedItemPosition).toString().split(":", 2)[0], Toast.LENGTH_SHORT);
                    toast.show();
                    String search_add_bluetooth = s.get(selectedItemPosition).toString().split(":", 2)[1];

                    if (search_add_bluetooth != "") {
                        connect_to_Arduino(search_add_bluetooth.toString());
                    }
                    else
                    {
                        Toast toasts = Toast.makeText(getApplicationContext(), "Ошибка! Перезапустите приложение.", Toast.LENGTH_SHORT);
                        toasts.show();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    //Событие кнопки создать запись-------------------------------------------------
                    case R.id.create_record_file:
                        Intent i_create_record_file = new Intent(MainActivity.this, A_create_record_file.class);
                        i_create_record_file.putExtra("opID", "-1");
                        startActivity(i_create_record_file);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run();
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        break;
                    //Событие кнопки редактировать запись-------------------------------------------
                    case R.id.chenge_record_file:
                        Intent i_chenge_record_file = new Intent(MainActivity.this, A_OpenDB.class);
                        i_chenge_record_file.putExtra("inOpening", Integer.toString(2));
                        startActivity(i_chenge_record_file);
                        break;
                    //Событие кнопки Открыть запись-------------------------------------------------
                    case R.id.open_record_file:
                        Intent i_open_record_file = new Intent(MainActivity.this, A_OpenDB.class);
                        i_open_record_file.putExtra("inOpening", Integer.toString(21));
                        startActivity(i_open_record_file);
                        break;
                    //Событие кнопки промывка-------------------------------------------------------
                    case R.id.clear_weather:
                        Intent i_clear_weather = new Intent(MainActivity.this, A_washing.class);
                        startActivity(i_clear_weather);
                        break;
                }
            }
        };
        //Привязываю слушателя к кнопкам------------------------------------------------------------
        //btn_vibrat_podkl.setOnClickListener(onClickListener);
        btn_create_record_file.setOnClickListener(onClickListener);
        btn_chenge_record_file.setOnClickListener(onClickListener);
        btn_open_record_file.setOnClickListener(onClickListener);
        btn_clear_weather.setOnClickListener(onClickListener);
    }

    private boolean permissionGranted() {
        //если оба разрешения получены, вернуть true
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.BLUETOOTH) == PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),                   Manifest.permission.BLUETOOTH_ADMIN) == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN}, 0);
            return false;
        }
    }

    private boolean bluetoothEnabled() {
    //если блютуз включен, вернуть true, если нет, вежливо попросить пользователя его включить
        if(mBluetoothAdapter.isEnabled()) {
            return true;
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
            return false;
        }
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

    static class ConnectedThread extends Thread {
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

        public void run() {
            String rjggjr = "Hello Arduino.";
            try {
                outputStream.write(rjggjr.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




