package com.example.radog.patm_cine_mapas;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.radog.patm_cine_mapas.BD.DBHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Function extends AppCompatActivity {

    @BindView(R.id.lvFunctions)
    ListView lvFunctions;

    private ArrayList<itemFunctionEvent> arrayItem;
    private ListViewFunctionAdapter adapter = null;
    private CharSequence[] items;
    private String selection, number;
    private DBHelper objDBH;
    private SQLiteDatabase BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);

    }

}
