package com.rubberdroid.sudoiku;

import com.rubberdroid.sudoiku.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Sudoku extends Activity implements OnClickListener {
	private static final String TAG = "Sudoiku";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		addThisAsClickListenerTo(R.id.resume_button);
//		addThisAsClickListenerTo(R.id.add_button);
		addThisAsClickListenerTo(R.id.new_button);
		addThisAsClickListenerTo(R.id.about_button);
		addThisAsClickListenerTo(R.id.settings_button);
	}

	private void addThisAsClickListenerTo(int viewId) {
		View button = findViewById(viewId);
		button.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_button:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.new_button:
			openNewGameDialog();
			break;
		case R.id.resume_button:
			resumeGame();
			break;
		case R.id.settings_button:
			startSettingsView();
			break;
		}
	}

	private void resumeGame() {
		Intent intent = new Intent(Sudoku.this, Sudoiku.class);
		startActivity(intent);
	}

	private void openNewGameDialog() {
		new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
				.setItems(R.array.difficulty,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								startGame(i);
							}
						}).show();
	}

	private void startGame(int i) {
		Log.d(TAG, "clicked on " + i);
		Intent intent = new Intent(Sudoku.this, Sudoiku.class);
		intent.putExtra(Sudoiku.KEY_DIFFICULTY, i);
		startActivity(intent);
	}

	private void startSettingsView() {
		startActivity(new Intent(this, Settings.class));
	}

}