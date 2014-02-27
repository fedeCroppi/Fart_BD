package com.garu.fart;

import java.util.Iterator;
import java.util.TreeSet;

import DAO_Acudit.Conversor;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.garu.BD.SQLiteHelper;

public class Main_Activity extends Activity implements DialegListener {

	private Cursor dades;
	private static AcuditAdapter adapter;
	private ListView lista;
	private int numAcu;
	private SearchView searchView;
	private ActionMode actionMode;
	
	private SQLiteHelper sql;
	private Conversor acuConv;
	
	private boolean so;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Recuperar preferencies
		SharedPreferences config = getPreferences(0);
		so = config.getBoolean("pref_sound", true);

		sql = new SQLiteHelper(this, "acuditsBD.dbs", null, 1);
		SQLiteDatabase database = sql.getWritableDatabase();
		
		acuConv = new Conversor(sql);
		lista = (ListView) findViewById(R.id.llistaTitols);
		
		if(database != null){
			dades = acuConv.getAll();
			adapter = new AcuditAdapter(this, dades);
			adapter.notifyDataSetChanged();
			lista.setAdapter(adapter);
			database.close();
		}
		
		dades.moveToLast();
		numAcu = dades.getInt(0);

		listenersLista();
	}
	
	private void refreshData() {
		dades = acuConv.getAll();
		adapter.setDades(dades);
		adapter.notifyDataSetChanged();
	}

	private void listenersLista() {
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				if (actionMode != null) {
					return false;
				}
				Main_Activity.this.adapter.setChecked(position, true);
				startActionMode(new ActionModeCallback());
				actionMode.invalidate();
				return true;
			}
		});
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (actionMode != null) {
					Main_Activity.this.adapter.toggleChecked(position);
					actionMode.invalidate();
				} else {
					Intent i = new Intent(Main_Activity.this, Acudit_Activity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("acudit", Main_Activity.adapter.getItem(position));
					i.putExtras(bundle);
					startActivityForResult(i, 0);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.search).getActionView();

		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				if (!newText.isEmpty()) {
					mostrarResultat(newText);
				} else {
					listenersLista();
					lista.setAdapter(adapter);
				}
				return false;
			}
		});
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				listenersLista();
				return false;
			}
		});
		searchView.setIconifiedByDefault(false);
		return true;
	}

	private void mostrarResultat(String query) {
		Cursor filtre = acuConv.getAll(query);

		final AcuditAdapter cercaAdapter = new AcuditAdapter(this, filtre);
		lista.setAdapter(cercaAdapter);

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				return false;
			}
		});
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				if (actionMode != null) {
					Main_Activity.this.adapter.toggleChecked(position);
					actionMode.invalidate();
				} else {
					Intent i = new Intent(Main_Activity.this, Acudit_Activity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("acudit", cercaAdapter.getItem(position));
					i.putExtras(bundle);
					startActivityForResult(i, 1);
				}
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.nou:
			Dialeg dr = new Dialeg();
			Bundle b = new Bundle();
			b.putSerializable("acudit", adapter.getFirstCheckedItem());
			dr.setArguments(b);
			dr.show(getFragmentManager(), "NOU");
			return true;
		case R.id.search:
			return true;
		case android.R.id.home:
			return true;
		case R.id.action_settings:
			Intent i = new Intent(Main_Activity.this, Setting.class);
			startActivity(i);
			return true;
		default:
			Toast.makeText(this, "Acudits extrets de la plana web feti-chistes.es", Toast.LENGTH_SHORT).show();
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				Acudit acu = (Acudit) data.getExtras().getSerializable("result");
				acuConv.update(acu);
				refreshData();
			}
			break;
		case 1:
			searchView.setQuery("", true);
			if (resultCode == RESULT_OK) {
				Acudit acu = (Acudit) data.getExtras().getSerializable("result");
				acuConv.update(acu);
				refreshData();
			}
			lista.setAdapter(adapter);
			break;
		default:
			break;
		}
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog.getTag().equals("NOU")) {
			EditText et = (EditText) dialog.getDialog().findViewById(R.id.nouTitolText);
			if (et.getText().length() > 0) {
				Acudit nou = new Acudit();
				nou.setNum(++numAcu);
				nou.setTitol(et.getText().toString());
				et = (EditText) dialog.getDialog().findViewById(R.id.nouCosText);
				nou.setCos(et.getText().toString());
				RatingBar rb = (RatingBar) dialog.getDialog().findViewById(R.id.nouRating);
				nou.setRate(rb.getRating());
				acuConv.insert(nou);
				refreshData();
			}
		}
		if (dialog.getTag().equals("EDIT")) {
			Toast.makeText(this, "EDit", Toast.LENGTH_SHORT).show();
			Acudit acu = adapter.getFirstCheckedItem();
			EditText et = (EditText) dialog.getDialog().findViewById(R.id.nouTitolText);
			acu.setTitol(et.getText().toString());
			et = (EditText) dialog.getDialog().findViewById(R.id.nouCosText);
			acu.setCos(et.getText().toString());
			RatingBar rb = (RatingBar) dialog.getDialog().findViewById(R.id.nouRating);
			acu.setRate(rb.getRating());
			acuConv.update(acu);
			refreshData();
		}
		if (actionMode != null)
			actionMode.finish();
		Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(this, "Cancel·lat", Toast.LENGTH_SHORT).show();
		if (actionMode != null)
			actionMode.finish();
	}

	// update ListView
	protected void updateData() {
		if (adapter == null) {
			return;
		}

		TreeSet<Integer> checked = (TreeSet<Integer>) adapter.getCheckedItems().descendingSet();
		Iterator<Integer> it = checked.iterator();
		while (it.hasNext()) {
			Integer index = (Integer) it.next();
			acuConv.remove(adapter.getItem(index));
			this.numAcu--;
		}

		if (checked != null) {
			adapter.updateData();
			refreshData();
			if (actionMode != null) {
				actionMode.invalidate();
			}
		}
		// if empty - finish action mode.
		if (actionMode != null && dades.getColumnCount() < 0) {
			actionMode.finish();
		}
	}

	// metode per no perdre les dades introduides
	@Override
	public void onConfigurationChanged(Configuration novaconfig) {
		super.onConfigurationChanged(novaconfig);
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		SharedPreferences config = getPreferences(0);
		SharedPreferences.Editor editor = config.edit();
		editor.putBoolean("pref_sound", so);
		editor.commit();
	}

	public final class ActionModeCallback implements ActionMode.Callback {

		private String select = getString(R.string.select);
		private String selected = getString(R.string.selected);

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.compartir:
				Acudit acu = adapter.getFirstCheckedItem();
				Intent msg = new Intent(Intent.ACTION_SEND);
				msg.putExtra(Intent.EXTRA_SUBJECT, acu.getTitol());
				msg.putExtra(Intent.EXTRA_TEXT, acu.getCos());
				msg.setType("text/plain");
				startActivity(Intent.createChooser(msg, "Enviar per..."));
				return true;
			case R.id.edit:
				Dialeg dr = new Dialeg();
				Acudit acudit = adapter.getFirstCheckedItem();
				Bundle b = new Bundle();
				b.putSerializable("acudit", acudit);
				dr.setArguments(b);
				dr.show(getFragmentManager(), "EDIT");
				return true;
			case R.id.esborrar:
				updateData();
				return true;
			case R.id.espai:
				return true;
			default:
				return false;
			}
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			adapter.enterMultiMode();
			actionMode = mode;
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			adapter.exitMultiMode();
			actionMode = null;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// remove previous items
			menu.clear();
			final int checked = adapter.getCheckedItemCount();
			// update title with number of checked items
			if (checked < 2)
				mode.setTitle(checked + " " + select);
			else
				mode.setTitle(checked + " " + selected);

			switch (checked) {
			case 0:
				// if nothing checked - exit action mode
				mode.finish();
				return true;
			case 1:
				mode.getMenuInflater().inflate(R.menu.contextual_complert, menu);
				return true;
			default:
				mode.getMenuInflater().inflate(R.menu.contextual_esborrar, menu);
				return true;
			}
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
			lista.setSelected(checked);
		}
	}
}
