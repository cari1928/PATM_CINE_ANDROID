package com.example.radog.patm_cine_mapas.rol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.constantes.G;
import com.example.radog.patm_cine_mapas.pojos.Rol;
import com.example.radog.patm_cine_mapas.proveedor.RolProveedor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RolInsertActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_rol_detalle)
    Toolbar toolbar;
    @BindView(R.id.editTextRolNombre)
    EditText editTextRolNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol_insert);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //flecha para ir a la actividad padre
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menú de guardar
        //sin grupo, tipo, orden, titulo
        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setShowAsAction(menuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_action_guardar);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case G.GUARDAR:
                attemptGuardar();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Va a validar
     */
    private void attemptGuardar() {
        //borra posibles mensajes de validaciones anteriores
        editTextRolNombre.setError(null);

        String rol = editTextRolNombre.getText().toString();

        //inicia validación
        if (TextUtils.isEmpty(rol)) {
            editTextRolNombre.setError(getString(R.string.campo_requerido));
            editTextRolNombre.requestFocus(); //toma el foco
            return; //sale de validación!!!
        }

        Rol objR = new Rol(G.SIN_VALOR_INT, rol);
        //getContentResolver sabe el proveedor de contenido porque está especificado en el Manifest
        RolProveedor.insertRecord(getContentResolver(), objR);

        finish();
    }

}
