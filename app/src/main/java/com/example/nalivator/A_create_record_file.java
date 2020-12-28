package com.example.nalivator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nalivator.MyBD.MyListAngle;
import com.example.nalivator.MyBD.MyListConnect;
import com.example.nalivator.MyBD.MyListNalivators;
import com.example.nalivator.MyBD.NalivatorDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class A_create_record_file extends AppCompatActivity {
    //Кнопка отменить-------------------------------------------------------------------------------
    Button btn_back_cmf_A_create_record_file;
    //Кнопка настроить углы-------------------------------------------------------------------------
    Button but_setting_angle;
    //Кнопка настроить объём------------------------------------------------------------------------
    Button but_setting_volume;
    //Кнопка сохранить------------------------------------------------------------------------------
    Button b_save_A_create_record_file;

    EditText my_name_record_text_viev_chenge;
    TextView name_crf;

    private NalivatorDao nalivatorDB;

    int kolvoR = -1;
    double timevolume = 0;

    ArrayList<Integer> angelR = null;

    int itsopenid;
    int itsIDrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_create_record_file);

        my_name_record_text_viev_chenge = (EditText) findViewById(R.id.my_name_record_text_viev_chenge);
        name_crf = (TextView) findViewById(R.id.name_crf);

        Intent intenttet = getIntent();
        itsopenid = Integer.parseInt(intenttet.getStringExtra("opID"));
        itsIDrecords = itsopenid;
        if (itsopenid>-1){
            nalivatorDB = ((AppDelegate) getApplicationContext()).getNalivatorDataBase().getNalivatorDao();

            MyListNalivators itsMyListNalivatorsID = nalivatorDB.getMyListNalivatorsNEW(itsopenid).get(0);
            timevolume = itsMyListNalivatorsID.getMyTimeNaliv();
            kolvoR = itsMyListNalivatorsID.getMyKolvoR();
            my_name_record_text_viev_chenge.setText(itsMyListNalivatorsID.getMyNameNalivator());
            name_crf.setText(itsMyListNalivatorsID.getMyNameNalivator());

            if (kolvoR>2)
            {
                angelR = new ArrayList<Integer>();

                List <MyListAngle> checkListAngle = nalivatorDB.getidnal(itsopenid);

                for (int ir=0; ir<kolvoR;ir++)
                {
                    angelR.add(checkListAngle.get(ir).getMyAngleR());

                }
            }
            else {
                angelR = new ArrayList<Integer>();
                angelR.add(0);
                angelR.add(180);
            }

        }








        //Привязываю кнопки-------------------------------------------------------------------------
        btn_back_cmf_A_create_record_file = (Button) findViewById(R.id.btn_back_cmf_A_create_record_file);
        but_setting_angle = (Button) findViewById(R.id.but_setting_angle);
        but_setting_volume = (Button) findViewById(R.id.but_setting_volume);
        b_save_A_create_record_file = (Button) findViewById(R.id.b_save_A_create_record_file);

        //Слушаем нажатия на кнопки
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            public void onClick(View view) {
                switch (view.getId()) {
                    //Событие кнопки Отменить-------------------------------------------------------
                    case R.id.btn_back_cmf_A_create_record_file:
                        finish();
                        break;
                    //Событие кнопки Настроить углы-------------------------------------------------
                    case R.id.but_setting_angle:
                        Intent i_setting_angle = new Intent(A_create_record_file.this, A_setting_angle.class);
                        i_setting_angle.putExtra("kolvoR", Integer.toString(kolvoR));
                        i_setting_angle.putExtra("angelR", angelR);

                        /*
                        Toast toaster = Toast.makeText(getApplicationContext(),
                                "Отправили " + kolvoR + " рюмок и массив из " + " записей", Toast.LENGTH_SHORT);
                        toaster.show();



                         */


                        startActivityForResult(i_setting_angle, 1);
                        break;
                    //Событие кнопки Настроить объём------------------------------------------------
                    case R.id.but_setting_volume:
                        Intent i_setting_volume = new Intent(A_create_record_file.this, A_setting_volume.class);
                        i_setting_volume.putExtra("timevolume", Double.toString(timevolume));
                        startActivityForResult(i_setting_volume,2);
                        break;
                    //Событие кнопки Сохранить------------------------------------------------------
                    case R.id.b_save_A_create_record_file:
                        if (itsIDrecords >-1) //update records
                        {
                            nalivatorDB.deleteByUserId(itsIDrecords);
                            nalivatorDB.deleteByUserAndleId(itsIDrecords);
                        }
                        nalivatorDB = ((AppDelegate) getApplicationContext()).getNalivatorDataBase().getNalivatorDao();
                        nalivatorDB.insertNalivators(createNalivator());
                        nalivatorDB.insertListAngle(createListAngle());
                        //nalivatorDB.insertListConnect(myrecylt);
                        finish();
                        break;
                }
            }
        };
        //Привязываю слушателя к кнопкам------------------------------------------------------------
        btn_back_cmf_A_create_record_file.setOnClickListener(onClickListener);
        but_setting_angle.setOnClickListener(onClickListener);
        but_setting_volume.setOnClickListener(onClickListener);
        b_save_A_create_record_file.setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if ((requestCode==1)&&(resultCode==-1)) {
            kolvoR = Integer.parseInt(data.getStringExtra("kolvoR"));
            angelR = data.getIntegerArrayListExtra("angelR");
        }
        if ((requestCode==2)&&(resultCode==-1)) {
            timevolume = Double.parseDouble(data.getStringExtra("timevolume"));
        }
    }

    private List<MyListNalivators> createNalivator() {
        List<MyListNalivators> albums = new ArrayList<>();
        int size = nalivatorDB.getMyListNalivators().size();
        int lastRecordsId =  0;
        if (size > 0 )
            lastRecordsId = nalivatorDB.getMyListNalivators().get(size-1).getMyId() + 1;
        albums.add(new MyListNalivators(lastRecordsId, (my_name_record_text_viev_chenge.getText().toString()), kolvoR, timevolume));
        return albums;
    }


    private List<MyListAngle> createListAngle() {
        List<MyListAngle> albums = new ArrayList<>();

        int sizeNal = nalivatorDB.getMyListNalivators().size();
        int lastRecordsIdNal =  0;
        if (sizeNal > 0 )
            lastRecordsIdNal = nalivatorDB.getMyListNalivators().get(sizeNal-1).getMyId();

        int sizeAng = nalivatorDB.getMyListAngle().size();
        int lastRecordsIdAng =  0;
        if (sizeAng > 0 )
            lastRecordsIdAng = nalivatorDB.getMyListAngle().get(sizeAng-1).getMyId() + 1;

        int sizeFrom = nalivatorDB.getMyAngleFromNalivator(lastRecordsIdNal).size();
        int lastRecordsIdFrom =  0;
        if (sizeFrom > 0 )
            lastRecordsIdFrom = nalivatorDB.getMyAngleFromNalivator(lastRecordsIdNal).get(sizeFrom-1).getMyId() + 1;

        if (angelR != null) {
            List<MyListConnect> myrecylt = new ArrayList<>();
            for (int i = 0; i < angelR.size(); i++) {
                albums.add(new MyListAngle(lastRecordsIdAng, lastRecordsIdNal, i, angelR.get(i)));

                //createListConnect(lastRecordsIdNal, lastRecordsIdFrom, lastRecordsIdAng);

/*


                int size = nalivatorDB.getMyAngleFromNalivator(lastRecordsIdNal).size();
                int lastRecordsId =  0;
                if (size > 0 )
                    lastRecordsId = nalivatorDB.getMyAngleFromNalivator(lastRecordsIdNal).get(size-1).getMyId() + 1;

                myrecylt.add(new MyListConnect(lastRecordsIdFrom, lastRecordsIdNal, lastRecordsIdAng));
*/
                lastRecordsIdFrom = lastRecordsIdFrom + 1;
                lastRecordsIdAng = lastRecordsIdAng + 1;

            }
        }
        else
        {
            //albums.add(new MyListAngle());
        }
        return albums;
    }
/*
    private List<MyListConnect> createListConnect(int lastRecordsIdNal, int lastRecordsIdFrom, int lastRecordsIdAng) {
        List<MyListNalivators> albums = new ArrayList<>();
        int size = 0;//nalivatorDB.getMyAngleFromNalivatorAll().size();
        int lastRecordsId =  0;
        if (size > 0 )
            lastRecordsId = 0; // nalivatorDB.getMyAngleFromNalivatorAll().get(size-1).getMyId() + 1;
        myrecylt.add(new MyListConnect(lastRecordsIdFrom, lastRecordsIdNal, lastRecordsIdAng));
        return myrecylt;



        /*
        List<MyListNalivators> albums = new ArrayList<>();
        int size = nalivatorDB.getMyListNalivators().size();
        int lastRecordsId =  0;
        if (size > 0 )
            lastRecordsId = nalivatorDB.getMyListNalivators().get(size-1).getMyId() + 1;
        my_name_record_text_viev_chenge = (EditText) findViewById(R.id.my_name_record_text_viev_chenge);
        albums.add(new MyListNalivators(lastRecordsId, (my_name_record_text_viev_chenge.getText().toString()), kolvoR, lastRecordsId * 5));
        return albums;


         */





}
