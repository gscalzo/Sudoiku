package com.rubberdroid.sudoiku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jordan.sudoku.Serializer;
import com.rubberdroid.sudoiku.model.SudokuModel;
import com.rubberdroid.sudoiku.view.SudokuView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Sudoiku extends Activity {
	public static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;

	public enum Difficulty {
		easy, medium, hard
	};

	private SudokuView sudokuView;
	private SudokuModel sudokuModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, -1);

		if (diff == -1)
			try {
				deserializeModel();
			} catch (IOException e) {
				// TODO
				// popup che mai lanciato e fargliscegliere la difficoltà
			}
		else
			createModel(diff);

		sudokuView = new SudokuView(this, sudokuModel);

		setContentView(sudokuView);
		sudokuView.requestFocus();
	}

	private void deserializeModel() throws IOException {
		InputStream iStream = getBaseContext().openFileInput(
				"sudoiku_last_game.res");
		try {
			sudokuModel = (SudokuModel) Serializer.deserialize(iStream);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, e.getMessage());
			throw new IOException(e.getMessage());
		}
	}

	private void createModel(int diff) {
		sudokuModel = new SudokuModel(diff);
	}

	@Override
	protected void onPause() {
		serializeModel();
		super.onPause();
	}

	private void serializeModel() {
		try {
			OutputStream oStream = getBaseContext().openFileOutput(
					"sudoiku_last_game.res", MODE_PRIVATE);
			Serializer.serialize(sudokuModel, oStream);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
