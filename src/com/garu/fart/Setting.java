package com.garu.fart;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Setting extends PreferenceActivity{

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();
	}

	private void setupSimplePreferencesScreen() {
		addPreferencesFromResource(R.xml.preference);
	}
}
