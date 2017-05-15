package com.example.radog.patm_cine_mapas.rol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.radog.patm_cine_mapas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RolActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        RolListFragment cicloListFragment = new RolListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_rol, cicloListFragment);
        transaction.commit();

    }

    @OnClick(R.id.fab)
    public void fab() {
        Intent iInsert = new Intent(this, RolInsertActivity.class);
        startActivity(iInsert);
    }
}
