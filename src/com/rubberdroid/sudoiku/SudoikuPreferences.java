package com.rubberdroid.sudoiku;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SudoikuPreferences {

	private SharedPreferences prefs;

	public SudoikuPreferences(Context cxt) {
		prefs = PreferenceManager.getDefaultSharedPreferences(cxt);
	}
	
	public boolean hintsNotes(){
		return prefs.getBoolean("hints_notes", false);
	}
	
	public boolean hintsNumbers(){
		return prefs.getBoolean("hints_numbers", false);
	}
	
	public boolean soundFx(){
		return prefs.getBoolean("soundfx", true);
	}
}
