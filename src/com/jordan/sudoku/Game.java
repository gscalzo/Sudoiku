package com.jordan.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity {
	private static final String TAG = "Sudoku";

	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;

	public enum Difficulty {
		easy, medium, hard
	};

	private PuzzleView puzzleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);

		Puzzle puzzle =  new Puzzle(diff);


		puzzleView = new PuzzleView(this, puzzle);

		setContentView(puzzleView);
		puzzleView.requestFocus();
	}


	protected void showKeypadOrError(int x, int y) {
		// int tiles[] = getUsedTiles(x, y);
		// if (tiles.length == 9) {
		// Toast toast = Toast.makeText(this,
		// R.string.no_moves_label, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		// toast.show();
		// } else {
		// Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles));
		// Dialog v = new Keypad(this, tiles, puzzleView);
		// v.show();
		// }
		}


}
