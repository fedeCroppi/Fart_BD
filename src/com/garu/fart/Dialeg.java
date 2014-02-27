package com.garu.fart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/*
 * L'activitat que crea una instància Del diàleg ha d'implementar
 * aquesta interface per tal de rebre els esdeveniments del diàleg.
 * Cada mètode té un paràmetre DialogFragment.
 */

public class Dialeg extends DialogFragment {
	private DialegListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mListener = (DialegListener) activity;
		} catch (ClassCastException e) {
			// Si l'activitat no implementa la interface...
			throw new ClassCastException(activity.toString() + " cal implementar DialegListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Obtenir l'inlador de layouts
		LayoutInflater inflater = getActivity().getLayoutInflater();

		Activity a = getActivity();
		if (a instanceof Main_Activity) {
			//agafar el view del dialog
			View v = inflater.inflate(R.layout.nou_acudit, null);
			// agafar el ratingbar per asignar valor
			RatingBar rb = (RatingBar)v.findViewById(R.id.nouRating);	
			// canviar color a les estrelles
			LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
			stars.getDrawable(0).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
			stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
			
			if (this.getTag().equals("EDIT")) {
				Acudit acu = (Acudit) getArguments().getSerializable("acudit");	
				EditText et = (EditText) v.findViewById(R.id.nouTitolText);
				et.setText(acu.getTitol());
				et = (EditText) v.findViewById(R.id.nouCosText);
				et.setText(acu.getCos());
				TextView tv = (TextView)v.findViewById(R.id.nouCos);
				tv.setText("Acudit");
				if(acu.getRate()!=null)rb.setRating(acu.getRate());
			}
			
			// "Inflar" i establir el layout nou acudit
			builder.setView(v);
		} else if (a instanceof Acudit_Activity) {
			View v = inflater.inflate(R.layout.puntuacio, null);
	
			RatingBar rb = (RatingBar)v.findViewById(R.id.rate);
			// canviar color a les estrelles
			LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
			stars.getDrawable(0).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
			stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
			
			Acudit acu = (Acudit) getArguments().getSerializable("acudit");	
			if(acu.getRate() != null)rb.setRating(acu.getRate());
			// "Inflar" i establir el layout puntuació
			builder.setView(v);
		}

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogPositiveClick(Dialeg.this);
			}
		});

		builder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogNegativeClick(Dialeg.this);
			}
		});

		AlertDialog dialeg = builder.create();

		return dialeg;
	}
}
