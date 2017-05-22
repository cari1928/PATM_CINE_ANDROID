package com.example.radog.patm_cine_mapas.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.radog.patm_cine_mapas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public static final String TAG = "NOTICIAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        txtTitle.setText(getIntent().getExtras().getString("title"));
        txtMessage.setText(getIntent().getExtras().getString("message"));



    }
}
