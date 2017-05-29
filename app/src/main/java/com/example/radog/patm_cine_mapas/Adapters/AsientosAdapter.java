package com.example.radog.patm_cine_mapas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Activities.ReportActivity;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.LongClickListener;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAAsiento;

import java.util.List;

/**
 * Created by radog on 28/05/2017.
 */

public class AsientosAdapter extends
        RecyclerView.Adapter<AsientosAdapter.AsiViewHolder>
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private List<TDAAsiento> lAsientos;
    Context context;
    private String name;
    private int asiento_id;
    private int columna;
    private String fila;

    public AsientosAdapter(List<TDAAsiento> listComp, Context context) {
        this.lAsientos = listComp;
        this.context = context;
    }

    @Override
    public AsiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_asientos, parent, false);
        return new AsiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AsiViewHolder holder, int position) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.asiento);
            holder.imvAsiento.setImageBitmap(bitmap);
            holder.txtFila.setText(lAsientos.get(position).getFila());
            holder.txtColumna.setText(String.valueOf(lAsientos.get(position).getColumna()));

            holder.setLongClickListener(new LongClickListener() {
                @Override
                public void onItemLongClick(int pos) {
                    name = lAsientos.get(pos).getFila() + " - " + lAsientos.get(pos).getColumna();
                    asiento_id = lAsientos.get(pos).getAsiento_id();
                    columna = lAsientos.get(pos).getColumna();
                    fila = lAsientos.get(pos).getFila();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return lAsientos.size();
    }

    public void getItemSelected(MenuItem item, String type) {
        switch (item.getItemId()) {
            case 0:
                ((MyApplication) context.getApplicationContext()).setAsiento_id(asiento_id);
                ((MyApplication) context.getApplicationContext()).setColumna(columna);
                ((MyApplication) context.getApplicationContext()).setFila(fila);
                Intent iReport = new Intent(context, ReportActivity.class);
                context.startActivity(iReport);
                break;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    public static class AsiViewHolder extends RecyclerView.ViewHolder implements
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public ImageView imvAsiento;
        public TextView txtFila;
        public TextView txtColumna;
        LongClickListener longClickListener;

        public AsiViewHolder(View itemView) {
            super(itemView);

            imvAsiento = (ImageView) itemView.findViewById(R.id.imvAsiento);
            txtFila = (TextView) itemView.findViewById(R.id.txtCompraId);
            txtColumna = (TextView) itemView.findViewById(R.id.txtPelicula);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setLongClickListener(LongClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onItemLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action:");
            menu.add(0, 0, 0, "Select this seat");
        }
    }

}
