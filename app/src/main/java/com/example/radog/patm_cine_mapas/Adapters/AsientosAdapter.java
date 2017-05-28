package com.example.radog.patm_cine_mapas.Adapters;

import android.content.Context;
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

import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
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
                    //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(context, name + " : " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(context, "DO SOMETHING HERE...", Toast.LENGTH_SHORT).show();
//                if (type.equals("Login")) {
//                    Intent iLogin = new Intent(context.getApplicationContext(), Login.class);
//                    context.startActivity(iLogin);
//                } else if (type.equals("Sucursal")) {
//                    Intent iAsientos = new Intent(context.getApplicationContext(), AsientosActivity.class);
//                    context.startActivity(iAsientos);
//                }
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
            txtFila = (TextView) itemView.findViewById(R.id.txtFila);
            txtColumna = (TextView) itemView.findViewById(R.id.txtColumna);

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
