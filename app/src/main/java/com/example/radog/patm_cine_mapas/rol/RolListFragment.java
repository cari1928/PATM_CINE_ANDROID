package com.example.radog.patm_cine_mapas.rol;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.constantes.G;
import com.example.radog.patm_cine_mapas.proveedor.Contrato;
import com.example.radog.patm_cine_mapas.proveedor.RolProveedor;

/**
 * Created by radog on 14/05/2017.
 */

public class RolListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    CicloCursorAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    //dice el modo en que está el action bar
    ActionMode actionMode;
    private View selectedView;

    public static RolListFragment newInstance() {
        RolListFragment f = new RolListFragment();
        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se indica que se usarán menús porque ésta no es una actividad y necesita que se indique
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "Insertar");
        menuItem.setIcon(R.drawable.ic_nuevo_registro);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); //para que siempre aparezca

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case G.INSERTAR:
                Intent iDetalle = new Intent(getActivity(), RolInsertActivity.class);
                startActivity(iDetalle);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.i(LOGTAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_rol_list, container, false);
        mAdapter = new CicloCursorAdapter(getActivity());
        setListAdapter(mAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(LOGTAG, "onActivityCreated");
        mCallbacks = this;
        getLoaderManager().initLoader(0, null, mCallbacks);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //no siempre se quiere que se muestre
                if (actionMode != null) {
                    return false;
                }

                actionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);
                selectedView = view;
                return true;
            }
        });
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //creacion del menu contextual
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int rolId;
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    rolId = (int) selectedView.getTag();
                    RolProveedor.deleteRecord(getActivity().getContentResolver(), rolId);
                    break;
                case R.id.menu_edit:
                    Intent iUpdate = new Intent(getActivity(), RolUpdateActivity.class);
                    rolId = (int) selectedView.getTag();
                    iUpdate.putExtra(Contrato.Rol._ID, rolId);
                    startActivity(iUpdate);
            }

            actionMode.finish(); //para quitar el menú una vez que se borró
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        String columns[] = new String[]{Contrato.Rol._ID,
                Contrato.Rol.ROL
        };

        Uri baseUri = Contrato.Rol.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String selection = null;
        return new CursorLoader(getActivity(), baseUri, columns, selection, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)

        Uri laUriBase = Uri.parse("content://" + Contrato.AUTHORITY + "/Rol");
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    public class CicloCursorAdapter extends CursorAdapter {
        public CicloCursorAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Rol._ID));
            String rol = cursor.getString(cursor.getColumnIndex(Contrato.Rol.ROL));

            TextView textviewRol = (TextView) view.findViewById(R.id.textview_rol_list_item_rol);
            textviewRol.setText(rol);

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(rol); //Genera un color según el nombre
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(rol.substring(0, 1), color);

            ImageView image = (ImageView) view.findViewById(R.id.image_view);
            image.setImageDrawable(drawable);

            view.setTag(ID);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.rol_list_item, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }
}
