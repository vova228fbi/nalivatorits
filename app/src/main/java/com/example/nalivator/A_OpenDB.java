package com.example.nalivator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nalivator.MyBD.MyListAngle;
import com.example.nalivator.MyBD.MyListNalivators;
import com.example.nalivator.MyBD.NalivatorDao;
//import com.example.nalivator.S_My_Nal.SNalivator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class A_OpenDB extends AppCompatActivity {
    //Кнопка выбора количества рюмок----------------------------------------------------------------
    Button but_delete_nalivators;
    //Кнопка отменить-------------------------------------------------------------------------------
    Button but_back_open_bd;
    //Кнопка отменить-------------------------------------------------------------------------------
    ListView its_my_recycler;

    private NalivatorDao nalivatorDB;

    ArrayList<HashMap<String, Object>> cList;
    //Текст номера рюмки----------------------------------------------------------------------------
    private static String ID = "id";
    //Текст угла рюмки----------------------------------------------------------------------------
    private static String NAME = "Name";
    //Текст угла рюмки----------------------------------------------------------------------------
    private static int ICON = R.drawable.ic_launcher_background;
    //Текст угла рюмки----------------------------------------------------------------------------
    private static String INFO = "Info";

    ArrayList<Integer> arrayid = null;

    private A_OpenDB.ConnectedThread threadCommand;

    int inOpening=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_open_db);
        Intent jrgjrgjrgnj = getIntent();
        inOpening = Integer.parseInt(jrgjrgjrgnj.getStringExtra("inOpening"));

        but_delete_nalivators = findViewById(R.id.but_delete_nalivators);
        but_back_open_bd = findViewById(R.id.but_back_open_bd);

        nalivatorDB = ((AppDelegate) getApplicationContext()).getNalivatorDataBase().getNalivatorDao();


        but_delete_nalivators.setText(" Удалить выбором ");

        // начальная инициализация списка
        its_my_recycler = (ListView) findViewById(R.id.its_my_recycler);
        // создаем адаптер
        cList = new ArrayList<>();
        HashMap<String, Object> hMap;
        arrayid = new ArrayList<Integer>();
        for (int it = 0; it < nalivatorDB.getMyListNalivators().size();it++){
            String myid=Integer.toString(nalivatorDB.getMyListNalivators().get(it).getMyId());
            String stringname = nalivatorDB.getMyListNalivators().get(it).getMyNameNalivator();
            String snritginfo = "Кол-во рюмок = " + nalivatorDB.getMyListNalivators().get(it).getMyKolvoR() + "; Время разлива = " + nalivatorDB.getMyListNalivators().get(it).getMyTimeNaliv() + ".";

            hMap = new HashMap<>();
            hMap.put(ID, myid);
            hMap.put(NAME, stringname);
            hMap.put(INFO, snritginfo);
            //hMap.put(ICON, R.drawable.ic_launcher_background);
            cList.add(hMap);
            arrayid.add(Integer.parseInt(myid));
        }
        SimpleAdapter my_adapter = new SimpleAdapter(this, cList,
                R.layout.s_naliv, new String[]{ID, NAME, INFO},
                new int[]{R.id.thisid, R.id.SNaliv_text_Name, R.id.SNaliv_text_Info});

        // устанавливаем для списка адаптер
        its_my_recycler.setAdapter(my_adapter);
        its_my_recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (but_delete_nalivators.getText() == " Удалить выбором ")
                {

                    //Open Activity Change
                    if (inOpening == 2) //chenge
                    {

                        //Toast.makeText(A_OpenDB.this, Integer.toString(arg2), Toast.LENGTH_SHORT).show();

                        Intent i_chenge_record_file = new Intent(A_OpenDB.this, A_create_record_file.class);
                        Toast.makeText(A_OpenDB.this, Integer.toString(nalivatorDB.getMyListNalivators().get(arrayid.get(arg2)).getMyId()), Toast.LENGTH_SHORT).show();
                        i_chenge_record_file.putExtra("opID", Integer.toString(nalivatorDB.getMyListNalivators().get(arrayid.get(arg2)).getMyId()));
                        startActivity(i_chenge_record_file);


                    }
                    else    //21 - go to arduino
                    {
                        //Toast.makeText(A_OpenDB.this, "I dont understend you", Toast.LENGTH_SHORT).show();
                        //Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino Go To Arduino

                        String go_to_arduino = "";
                        int iditsnalivators = nalivatorDB.getMyListNalivators().get(arrayid.get(arg2)).getMyId();
                        int kolvoRitsnalivators = nalivatorDB.getMyListNalivators().get(iditsnalivators).getMyKolvoR();

                        go_to_arduino ="Принимай настройки: " +  "Количество рюмок: " + Integer.toString(kolvoRitsnalivators) + "; ";
                        go_to_arduino = go_to_arduino + "Время наливания (мсек): " + Integer.toString((int)(nalivatorDB.getMyListNalivators().get(iditsnalivators).getMyTimeNaliv()*1000)) + "; Углы:";


                        ArrayList<Integer> angelR = null;
                        if (kolvoRitsnalivators>2)
                        {
                            angelR = new ArrayList<Integer>();

                            List <MyListAngle> checkListAngle = nalivatorDB.getidnal(iditsnalivators);

                            for (int ir=0; ir<kolvoRitsnalivators;ir++)
                            {
                                angelR.add(checkListAngle.get(ir).getMyAngleR());

                            }
                        }
                        else {
                            angelR = new ArrayList<Integer>();
                            angelR.add(0);
                            angelR.add(180);
                        }

                        for (int ie=0;ie<angelR.size(); ie++)
                        {
                            go_to_arduino = go_to_arduino + " " + Integer.toString(angelR.get(ie)) + " ";
                        }
                        go_to_arduino = go_to_arduino + ".";
                        Toast.makeText(A_OpenDB.this, go_to_arduino, Toast.LENGTH_SHORT).show();





                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete
                        threadCommand = new A_OpenDB.ConnectedThread(MainActivity.clientSocket);
                        threadCommand.run(go_to_arduino);
                        //dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete dont delete

                    }
                }
                else
                {
                    nalivatorDB.deleteByUserId(arrayid.get(arg2));
                    nalivatorDB.deleteByUserAndleId(arrayid.get(arg2));
                    createMyListView();
                }
            }
        });

        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    //Событие кнопки Отменить-------------------------------------------------------
                    case R.id.but_delete_nalivators:
                        if (but_delete_nalivators.getText() == " Удалить выбором ")
                            but_delete_nalivators.setText(" Выберите ");
                        else
                            but_delete_nalivators.setText(" Удалить выбором ");
                        break;
                     //Событие кнопки Отменить-------------------------------------------------------
                    case R.id.but_back_open_bd:
                        finish();
                }
            }
        };
        //Привязываю слушателя к кнопкам------------------------------------------------------------
        but_delete_nalivators.setOnClickListener(onClickListener);
        but_back_open_bd.setOnClickListener(onClickListener);
    }



    private void createMyListView(){
        cList = new ArrayList<>();
        HashMap<String, Object> hMap;
        arrayid = new ArrayList<Integer>();
        for (int it = 0; it < nalivatorDB.getMyListNalivators().size();it++){
            String myid=Integer.toString(nalivatorDB.getMyListNalivators().get(it).getMyId());
            String stringname = nalivatorDB.getMyListNalivators().get(it).getMyNameNalivator();
            String snritginfo = "Кол-во рюмок = " + nalivatorDB.getMyListNalivators().get(it).getMyKolvoR() + "; Время разлива = " + nalivatorDB.getMyListNalivators().get(it).getMyTimeNaliv() + ".";

            hMap = new HashMap<>();
            hMap.put(ID, myid);
            hMap.put(NAME, stringname);
            hMap.put(INFO, snritginfo);
            //hMap.put(ICON, R.drawable.ic_launcher_background);
            cList.add(hMap);
            arrayid.add(Integer.parseInt(myid));
        }
        SimpleAdapter my_adapter = new SimpleAdapter(this, cList,
                R.layout.s_naliv, new String[]{ID, NAME, INFO},
                new int[]{R.id.thisid, R.id.SNaliv_text_Name, R.id.SNaliv_text_Info});
        // устанавливаем для списка адаптер
        its_my_recycler.setAdapter(my_adapter);
    }


    /*
    private List<MyListNalivators> createAlbum() {
        List<MyListNalivators> albums = new ArrayList<>();
        int size = nalivatorDB.getMyListNalivators().size();
        int lastRecordsPoz =  0;
        int lastRecordsId =  0;
        if (size > 0 )
        {
            lastRecordsPoz = size + 1;
            lastRecordsId = nalivatorDB.getMyListNalivators().get(size-1).getMyId();
        }
        for (int i = lastRecordsPoz; i< (lastRecordsPoz + 10); i++)
        {
            albums.add(new MyListNalivators(lastRecordsId, "Nalivator" + i, i * 5, i * 5));
            lastRecordsId = lastRecordsId + 1;
        }
        return albums;
    }

*/



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