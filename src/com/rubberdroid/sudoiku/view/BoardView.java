package com.rubberdroid.sudoiku.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

import com.rubberdroid.sudoiku.R;
import com.rubberdroid.sudoiku.SoundManager;
import com.rubberdroid.sudoiku.SudoikuPreferences;
import com.rubberdroid.sudoiku.model.SudokuModel;
import com.rubberdroid.sudoiku.model.Tile;

public class BoardView {
	private Paint foreground;
	private Paint notes;
	private Paint givens_color;
	private SudokuModel sudokumodel;
	int hintColors[];
	private BoardLayout boardLayout;
	private Bitmap notes_enabled;
	private Bitmap notes_disabled;
	private Bitmap eraser;

	// -------------------------------------------------------
	private Bitmap background_bitmap;
	private Bitmap tile_dark;
	private Bitmap tile_lite;
	private Bitmap tile_orange;
	private Paint disabled_numbers;
	private Bitmap alienWinBitmap;
	private SudoikuPreferences preferences;
	private SoundManager soundManager;

	// -------------------------------------------------------

	public BoardView(BoardLayout boardLayout, SudokuModel puzzle,
			Resources resources, SudoikuPreferences preferences,
			SoundManager soundManager) {
		this.boardLayout = boardLayout;
		this.sudokumodel = puzzle;
		this.preferences = preferences;
		this.soundManager = soundManager;

		notes_enabled = BitmapFactory.decodeResource(resources,
				R.drawable.notes_enabled);
		notes_disabled = BitmapFactory.decodeResource(resources,
				R.drawable.notes_disabled);
		eraser = BitmapFactory.decodeResource(resources, R.drawable.eraser);
		background_bitmap = BitmapFactory.decodeResource(resources,
				R.drawable.nebula);
		tile_dark = BitmapFactory.decodeResource(resources,
				R.drawable.tile_dark);
		tile_lite = BitmapFactory.decodeResource(resources,
				R.drawable.tile_lite);
		tile_orange = BitmapFactory.decodeResource(resources,
				R.drawable.tile_orange);

		alienWinBitmap = BitmapFactory.decodeResource(resources,
				R.drawable.golden_alien_win);

		createPaints(resources);
		createHintColors(resources);

	}

	private void createHintColors(Resources resources) {
		hintColors = new int[] { resources.getColor(R.color.puzzle_hint_0),
				resources.getColor(R.color.puzzle_hint_1),
				resources.getColor(R.color.puzzle_hint_2), };
	}

	private void createPaints(Resources resources) {
		disabled_numbers = createPaint(R.color.puzzle_disabled, resources);

		foreground = createNumbersPaint(resources, R.color.puzzle_foreground,
				boardLayout.tileSide);
		givens_color = createNumbersPaint(resources, R.color.puzzle_given,
				boardLayout.tileSide);
		notes = createNumbersPaint(resources, R.color.puzzle_notes,
				boardLayout.tileSide / 3f);
	}

	private Paint createNumbersPaint(Resources resources, int color, float size) {
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(resources.getColor(color));
		foreground.setStyle(Style.FILL);
		foreground.setFakeBoldText(true);
		foreground.setTextSize(size * 0.75f);
		foreground.setTextAlign(Paint.Align.CENTER);
		return foreground;
	}

	public void draw(Canvas canvas) {
		drawBackground(canvas);
		drawTiles(canvas);
		drawNumbers(canvas);
		drawNotes(canvas);
		drawNumberBoard(canvas);
		drawButtons(canvas);
		drawAlien(canvas);

	}

	private void drawAlien(Canvas canvas) {
		if (!sudokumodel.isFinshed())
			return;
		soundManager.playWinning();

		Rect src = new Rect(0, 0, alienWinBitmap.getWidth(), alienWinBitmap
				.getHeight());
		int topX = (boardLayout.boardSide - src.width()) / 2;
		int topY = (boardLayout.screenHeight - src.height()) / 2;
		Rect dst = new Rect(topX, topY, topX + alienWinBitmap.getWidth(), topY
				+ alienWinBitmap.getHeight());
		canvas.drawBitmap(alienWinBitmap, src, dst, null);
	}

	private void drawTiles(Canvas canvas) {
		Rect darkTile = new Rect(0, 0, tile_dark.getWidth(), tile_dark
				.getHeight());
		Rect liteTile = new Rect(0, 0, tile_lite.getWidth(), tile_lite
				.getHeight());

		Rect selectedTile = new Rect(0, 0, tile_lite.getWidth(), tile_lite
				.getHeight());

		for (int i = 0; i < 81; i++) {
			Bitmap tile = tile_dark;
			Rect tileRect = darkTile;

			if (sudokumodel.isSelected(i)) {
				tile = tile_orange;
				tileRect = selectedTile;
			} else if (numOfBlock(i) % 2 == 0) {
				tile = tile_lite;
				tileRect = liteTile;
			}

			Rect dst = rectOfTileNo(i);
			canvas.drawBitmap(tile, tileRect, dst, null);
		}
	}

	private int numOfBlock(int i) {
		int y = i / 9;
		int x = i - y * 9;
		return x / 3 + 3 * (y / 3);
	}

	private Rect rectOfTileNo(int i) {
		int y = i / 9;
		int x = i - y * 9;

		return getTileRect(x, y);
	}

	private void drawButtons(Canvas canvas) {
		int upper = boardLayout.boardSide + 2 * (int) boardLayout.tileSide + 3;
		drawNotesButton(canvas, upper);
		drawEraserButton(canvas, upper);
	}

	private void drawNotesButton(Canvas canvas, int upper) {
		Bitmap notes_button = notes_disabled;
		if (sudokumodel.isNotesMode())
			notes_button = notes_enabled;

		Rect src = new Rect(0, 0, notes_button.getWidth(), notes_button
				.getHeight());
		Rect dst = new Rect(boardLayout.boardSide / 2, upper,
				boardLayout.boardSide / 2 + (int) boardLayout.tileSide, upper
						+ (int) boardLayout.tileSide);
		canvas.drawBitmap(notes_button, src, dst, null);
	}

	private void drawEraserButton(Canvas canvas, int upper) {
		Rect src = new Rect(0, 0, eraser.getWidth(), eraser.getHeight());
		Rect dst = new Rect(boardLayout.boardSide / 2
				- (int) boardLayout.tileSide, upper, boardLayout.boardSide / 2,
				upper + (int) boardLayout.tileSide);
		canvas.drawBitmap(eraser, src, dst, null);
	}

	private void drawNumberBoard(Canvas canvas) {
		Rect tileRect = new Rect(0, 0, tile_dark.getWidth(), tile_dark
				.getHeight());

		int upper = boardLayout.boardSide + (int) boardLayout.tileSide;
		for (int i = 0; i < 9; ++i) {
			Rect dst = new Rect((int) (i * boardLayout.tileSide), upper,
					(int) (i * boardLayout.tileSide + boardLayout.tileSide),
					upper + (int) boardLayout.tileSide);
			canvas.drawBitmap(tile_dark, tileRect, dst, null);
		}

		float xTileCenter = boardLayout.tileSide / 2;
		float yTileCenter = boardLayout.tileSide / 2 - (heightCenterOfText())
				/ 2;
		for (int currentNumber = 1; currentNumber <= 9; currentNumber++) {
			drawInNumbersMode(canvas, upper, xTileCenter, yTileCenter,
					currentNumber);
			drawInNotesMode(canvas, upper, xTileCenter, yTileCenter,
					currentNumber);
		}
	}

	private void drawInNumbersMode(Canvas canvas, int upper, float xTileCenter,
			float yTileCenter, int currentNumber) {
		if (sudokumodel.isNotesMode())
			return;

		Paint color = foreground;
		Tile selectedTile = sudokumodel.selectedTile();
		if (preferences.hintsNumbers()
				&& (selectedTile.isGiven() || sudokumodel.isUsed(selectedTile
						.x(), selectedTile.y(), currentNumber)))
			color = disabled_numbers;

		canvas.drawText(String.valueOf(currentNumber), (currentNumber - 1)
				* boardLayout.tileSide + xTileCenter, upper + yTileCenter,
				color);
	}

	private void drawInNotesMode(Canvas canvas, int upper, float xTileCenter,
			float yTileCenter, int currentNumber) {
		if (sudokumodel.isNumbersMode())
			return;

		Paint color = foreground;
		Tile selectedTile = sudokumodel.selectedTile();
		if (selectedTile.noteActiveAt(currentNumber))
			color = disabled_numbers;

		canvas.drawText(String.valueOf(currentNumber), (currentNumber - 1)
				* boardLayout.tileSide + xTileCenter, upper + yTileCenter,
				color);
	}

	private Rect getTileRect(int x, int y) {
		Rect rect = new Rect((int) (x * boardLayout.tileSide),
				(int) (y * boardLayout.tileSide), (int) (x
						* boardLayout.tileSide + boardLayout.tileSide),
				(int) (y * boardLayout.tileSide + boardLayout.tileSide));
		return rect;
	}

	private void drawBackground(Canvas canvas) {
		Rect src = new Rect(0, 0, background_bitmap.getWidth(),
				background_bitmap.getHeight());
		Rect dst = new Rect(0, 0, boardLayout.boardSide,
				boardLayout.screenHeight);
		canvas.drawBitmap(background_bitmap, src, dst, null);
	}

	private Paint createPaint(int color, Resources resources) {
		Paint paint = new Paint();
		paint.setColor(resources.getColor(color));
		return paint;
	}

	private void drawNumbers(Canvas canvas) {
		float xTileCenter = boardLayout.tileSide / 2;
		float yTileCenter = boardLayout.tileSide / 2 - (heightCenterOfText())
				/ 2;
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				Tile currentTile = sudokumodel.getTile(col, row);
				Paint color = foreground;
				if (currentTile.isGiven())
					color = givens_color;

				canvas.drawText(currentTile.valueAsString(), col
						* boardLayout.tileSide + xTileCenter, row
						* boardLayout.tileSide + yTileCenter, color);
			}
		}
	}

	private void drawNotes(Canvas canvas) {
		if (preferences.hintsNotes())
			availableNumbersAsNotes();

		float xNoteCenter = boardLayout.tileSide / 6f;
		float yNoteCenter = (boardLayout.tileSide - heightCenterOfText()) / 6f;
		int noteSide = (int) (boardLayout.tileSide / 3f);

		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				Tile currentTile = sudokumodel.getTile(col, row);
				if (currentTile.value() != 0)
					continue;

				int topTile = (int) (row * boardLayout.tileSide);
				int leftTile = (int) (col * boardLayout.tileSide);

				Paint color = notes;
				for (int yNote = 0; yNote < 3; ++yNote) {
					for (int xNote = 0; xNote < 3; ++xNote) {
						int value = xNote + 1 + yNote * 3;
						if (currentTile.noteActiveAt(value))
							canvas.drawText(String.valueOf(value), leftTile
									+ xNote * noteSide + xNoteCenter, topTile
									+ yNote * noteSide + yNoteCenter, color);
					}
				}
			}
		}
	}

	private void availableNumbersAsNotes() {
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				Tile currentTile = sudokumodel.getTile(col, row);
				if (currentTile.value() != 0)
					continue;

				currentTile.resetNotes();
				for (int i = 1; i < 10; ++i)
					currentTile.toggleNoteAt(i);

				int[] usedNumbers = sudokumodel.getUsedTiles(col, row);
				for (int number : usedNumbers)
					currentTile.toggleNoteAt(number);

			}
		}
	}

	private float heightCenterOfText() {
		FontMetrics fm = foreground.getFontMetrics();
		return fm.ascent + fm.descent;
	}

}
