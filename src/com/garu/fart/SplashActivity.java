package com.garu.fart;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	private Timer timer;
	private ImageView img;
	// durada de la pantalla de benvinguda
	private static final long SPLASH_SCREEN_DELAY = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Oculta la barra de títol
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.splash_activity);
	
		img = (ImageView)findViewById(R.id.splash);
		
		if(onLine()) new imageDownload().execute("http://www.santamarinadozo.es/gestion/catalogo/tumb/DIOS%20Y%20EL%20GENOMA.jpg");
		else img.setImageResource(R.drawable.fart);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				// Comença la següent activitat
				startActivity(new Intent(SplashActivity.this, Main_Activity.class));
				// Tancament de l'activitat per tal que l'usuari no pugui tornar
				// a aquesta activitat pitjant el botó Tornar
				finish();
			}
		};

		// Simulació d'un procés de càrrega en iniciar l'aplicació.
		timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);
	}

	class imageDownload extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bmp = null;
			try {
				bmp = BitmapFactory.decodeStream(
						(InputStream)new URL(params[0]).getContent());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bmp;
		}
		
		@Override
		protected void onPostExecute(Bitmap result){
			img.setImageBitmap(result);
		}
	}
	
	public boolean onLine() {
		ConnectivityManager connectMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectMgr != null) {
			NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
			if (netInfo != null) {
				for (NetworkInfo net : netInfo) {
					if (net.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void entrar_Click(View v) {
		// Cancelo el timer perquè no s'obri una altra Main_activity
		this.timer.cancel();
		// Comença la següent activitat
		startActivity(new Intent(SplashActivity.this, Main_Activity.class));

		// Tancament de l'activitat per tal que l'usuari no pugui tornar a
		// aquesta activitat pitjant el botó Tornar
		finish();
	}

	//metode per no perdre les dades introduides
	@Override
	public void onConfigurationChanged(Configuration novaconfig) {
		super.onConfigurationChanged(novaconfig);
	}
}
