package com.example.radog.patm_cine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPass)
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void btnLogin() {
        String user = etUser.getText().toString();
        String pass = etUser.getText().toString();

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "Input the required information", Toast.LENGTH_SHORT).show();
        } else {
            Bundle data = new Bundle();
            pass = md5(pass);

            data.putString("USER", user);
            data.putString("PASS", pass);

            //validar usuario y contraseña
            //abrir mapa para seleccionar sucursales
        }
    }

    @OnClick(R.id.btnRegister)
    public void btnRegister() {

    }

    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

}
