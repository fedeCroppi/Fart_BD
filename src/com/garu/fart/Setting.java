package com.garu.fart;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Setting extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	@Override
	protected void onCreate(Bundle state){
		super.onCreate(state);
		addPreferencesFromResource(R.xml.preference);
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

	}
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_sound")) {
        	boolean so = sharedPreferences.getBoolean(key, false);
    		SharedPreferences.Editor editor = sharedPreferences.edit();
    		editor.putBoolean("pref_sound", so);
    		editor.commit();
        }
    }
}
