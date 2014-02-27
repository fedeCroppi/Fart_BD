package com.garu.fart;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Acudit_Activity extends Activity implements DialegListener {

	private Acudit acu;
	private boolean so;
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acudit);
		
		SharedPreferences config= getPreferences(0);
		so= config.getBoolean("pref_sound", true);

		ActionBar aBar = getActionBar();
		aBar.setDisplayHomeAsUpEnabled(true);

		// agafar les dades per mostrar-les
		acu = (Acudit) getIntent().getExtras().getSerializable("acudit");
		TextView tx = (TextView) findViewById(R.id.acudits);
		tx.setText(acu.getCos());
		// posar-le títol a l'activity
		aBar.setTitle(acu.getTitol());
		
		if(so){
			mediaPlayer = MediaPlayer.create(this, R.raw.risas);
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					mediaPlayer.start();
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 2000);
		}
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mediaPlayer.stop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.segon, menu);
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.compartir:
			// compartir l'acudit seleccionat
			Intent msg = new Intent(Intent.ACTION_SEND);
			msg.putExtra(Intent.EXTRA_SUBJECT, acu.getTitol());
			msg.putExtra(Intent.EXTRA_TEXT, acu.getCos());
			msg.setType("text/plain");
			startActivity(Intent.createChooser(msg, "Enviar per..."));
			return true;
		case R.id.star:
			// donarli valoració al acudit seleccionat
			Dialeg dr = new Dialeg();
			// pasar arguments al dialeg
			Bundle b = new Bundle();
			b.putSerializable("acudit", this.acu);
			dr.setArguments(b);
			dr.show(getFragmentManager(), "RATE");
			return true;
		case R.id.info_details:
			// mostrar la valoracio del acudit
			Float rate = null;
			if ((rate = acu.getRate()) != null) {
				Toast.makeText(this, "VALORACIÓ " + rate, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "SENSE VALORACIÓ", Toast.LENGTH_SHORT).show();
			}
			return true;
		case android.R.id.home:
			// pasar el result i acabar aquesta view
			pasarResult();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		RatingBar rb = (RatingBar) dialog.getDialog().findViewById(R.id.rate);
		acu.setRate(rb.getRating());
		pasarResult();
		Toast.makeText(this, "VALORAT", Toast.LENGTH_SHORT).show();
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(this, "Cancel·lat", Toast.LENGTH_SHORT).show();
	}

	private void pasarResult() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", acu);
		setResult(RESULT_OK, returnIntent);
	}

	//metode per no perdre les dades introduides
	@Override
	public void onConfigurationChanged(Configuration novaconfig) {
		super.onConfigurationChanged(novaconfig);
	}
}