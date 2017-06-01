package com.example.radog.patm_cine_mapas.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.LongClickListener;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;

import java.util.List;

/**
 * Created by radog on 28/05/2017.
 */

public class LogAdapter extends
        RecyclerView.Adapter<LogAdapter.LogViewHolder>
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private List<TDAPelicula> lPeli;
    private Context context;

    public LogAdapter(List<TDAPelicula> listComp, Context context) {
        this.lPeli = listComp;
        this.context = context;
    }

    @Override
    public LogAdapter.LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_log, parent, false);
        return new LogAdapter.LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LogAdapter.LogViewHolder holder, int position) {
        try {
            holder.txtPelicula.setText("Película: " + lPeli.get(position).getTitulo());
            holder.txtSala.setText("Sala: " + lPeli.get(position).getNombre());
            holder.txtHora.setText("Hora: " + lPeli.get(position).getHora() + " - " + lPeli.get(position).getHora_fin());
            holder.txtFecha.setText("Fecha de compra: " + lPeli.get(position).getFecha());
            holder.txtSucursal.setText("Sucursal: "
                    + lPeli.get(position).getPais() + " "
                    + lPeli.get(position).getCiudad() + " "
                    + lPeli.get(position).getDireccion());
            holder.txtEntradas.setText("Entrada: 1");
            holder.txtTotal.setText("Total: 79");

            holder.setLongClickListener(new LongClickListener() {
                @Override
                public void onItemLongClick(int pos) {
                    //Something
                }
            });

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return lPeli.size();
    }

    public void getItemSelected(MenuItem item, String type) {
        //Toast.makeText(context, name + " : " + item.getItemId(), Toast.LENGTH_SHORT).show();
        /*switch (item.getItemId()) {
            case 0:
                if (type.equals("Login")) {
                    Intent iLogin = new Intent(context.getApplicationContext(), Login.class);
                    context.startActivity(iLogin);
                } else if (type.equals("Sucursal")) {
                    Intent iAsientos = new Intent(context.getApplicationContext(), AsientosActivity.class);
                    context.startActivity(iAsientos);
                }
                break;
        }*/
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    public static class LogViewHolder extends RecyclerView.ViewHolder implements
            View.OnLongClickListener,
            View.OnCreateContextMenuListener {

        public TextView txtPelicula;
        public TextView txtSala;
        public TextView txtSucursal;
        public TextView txtHora;
        public TextView txtFecha;
        public TextView txtEntradas;
        public TextView txtTotal;

        LongClickListener longClickListener;

        public LogViewHolder(View itemView) {
            super(itemView);
            txtPelicula = (TextView) itemView.findViewById(R.id.txtPelicula);
            txtSala = (TextView) itemView.findViewById(R.id.txtSala);
            txtSucursal = (TextView) itemView.findViewById(R.id.txtSucursal);
            txtHora = (TextView) itemView.findViewById(R.id.txtHora);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtEntradas = (TextView) itemView.findViewById(R.id.txtEntradas);
            txtTotal = (TextView) itemView.findViewById(R.id.txtTotal);

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
            /*menu.setHeaderTitle("Select Action:");
            menu.add(0, 0, 0, "Buy Tickets");
            menu.add(0, 1, 1, "Extra Info");*/
        }
    }

}
