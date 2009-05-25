package com.jordan.sudoku;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

public class BoardView {

	private Paint dark;
	private Paint hilite;
	private Paint light;
	private Paint background;
	private Paint foreground;
	private Paint givens_color;
	private SudokuModel puzzle;
	private Paint puzzle_selected;
	int hintColors[];
	private BoardLayout boardLayout;
	private Bitmap wood_table;
	private Bitmap notes_enabled;
	private Bitmap notes_disabled;

	public BoardView(BoardLayout boardLayout, SudokuModel puzzle,
			Resources resources) {
		this.boardLayout = boardLayout;
		this.puzzle = puzzle;

		wood_table = BitmapFactory.decodeResource(resources, R.drawable.table);
		notes_enabled = BitmapFactory.decodeResource(resources,
				R.drawable.notes_enabled);
		notes_disabled = BitmapFactory.decodeResource(resources,
				R.drawable.notes_disabled);

		createPaints(resources);
		createHintColors(resources);

	}

	private void createHintColors(Resources resources) {
		hintColors = new int[] { resources.getColor(R.color.puzzle_hint_0),
				resources.getColor(R.color.puzzle_hint_1),
				resources.getColor(R.color.puzzle_hint_2), };
	}

	private void createPaints(Resources resources) {
		dark = createPaint(R.color.puzzle_dark, resources);
		light = createPaint(R.color.puzzle_light, resources);
		hilite = createPaint(R.color.puzzle_hilite, resources);
		background = createPaint(R.color.puzzle_background, resources);
		puzzle_selected = createPaint(R.color.puzzle_selected, resources);

		foreground = createNumbersPaint(resources, R.color.puzzle_foreground);
		givens_color = createNumbersPaint(resources, R.color.puzzle_given);
	}

	private Paint createNumbersPaint(Resources resources, int color) {
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(resources.getColor(color));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(boardLayout.tileSide * 0.75f);
		foreground.setTextAlign(Paint.Align.CENTER);
		return foreground;
	}

	public void draw(Canvas canvas) {
		drawBackground(canvas);
		drawLines(canvas);
		drawNumbers(canvas);
		drawKeyBoard(canvas);
		drawHints(canvas);
		drawSelection(canvas);
		drawButtons(canvas);
	}

	private void drawButtons(Canvas canvas) {
		int upper = boardLayout.boardSide + 2 * (int) boardLayout.tileSide + 3;

		Rect src = new Rect(0, 0, notes_enabled.getWidth(), notes_enabled
				.getHeight());
		Rect dst = new Rect(boardLayout.boardSide / 2, upper,
				boardLayout.boardSide / 2+(int)boardLayout.tileSide, upper + (int) boardLayout.tileSide);

		canvas.drawBitmap(notes_enabled, src, dst, null);

	}

	private void drawKeyBoard(Canvas canvas) {
		int upper = boardLayout.boardSide + (int) boardLayout.tileSide;

		Rect src = new Rect(0, 0, wood_table.getWidth(), wood_table.getHeight());
		Rect dst = new Rect(0, boardLayout.boardSide, boardLayout.boardSide,
				boardLayout.screenHeight);
		canvas.drawBitmap(wood_table, src, dst, null);
		canvas.drawRect(0, upper, boardLayout.boardSide, upper
				+ boardLayout.tileSide, background);

		for (int i = 0; i < 9; ++i) {
			canvas
					.drawLine(i * boardLayout.tileSide, upper, i
							* boardLayout.tileSide, upper
							+ boardLayout.tileSide, light);
			canvas.drawLine(i * boardLayout.tileSide + 1, upper, i
					* boardLayout.tileSide + 1, upper + boardLayout.tileSide,
					hilite);
		}

		canvas.drawLine(0, upper, boardLayout.boardSide, upper, dark);
		canvas.drawLine(0, upper + boardLayout.tileSide, boardLayout.boardSide,
				upper + boardLayout.tileSide, dark);

		float xTileCenter = boardLayout.tileSide / 2;
		float yTileCenter = boardLayout.tileSide / 2 - (heightCenterOfText())
				/ 2;
		for (int currentNumber = 1; currentNumber <= 9; currentNumber++) {

			Paint color = foreground;
			Tile selectedTile = puzzle.selectedTile();
			if (selectedTile.isGiven()
					|| puzzle.isUsed(selectedTile.x(), selectedTile.y(),
							currentNumber))
				color = givens_color;

			canvas.drawText(String.valueOf(currentNumber), (currentNumber - 1)
					* boardLayout.tileSide + xTileCenter, upper + yTileCenter,
					color);
		}
	}

	private void drawSelection(Canvas canvas) {
		Tile tileSelected = puzzle.selectedTile();
		Rect selectedRect = new Rect();
		getTileRect(tileSelected.x(), tileSelected.y(), selectedRect);
		canvas.drawRect(selectedRect, puzzle_selected);
	}

	private void drawHints(Canvas canvas) {
		Paint hint = new Paint();
		Rect r = new Rect();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int movesleft = 9 - puzzle.getUsedTiles(i, j).length;
				if (movesleft < hintColors.length) {
					getTileRect(i, j, r);
					hint.setColor(hintColors[movesleft]);
					canvas.drawRect(r, hint);
				}
			}
		}
	}

	private void getTileRect(int x, int y, Rect rect) {
		rect.set((int) (x * boardLayout.tileSide),
				(int) (y * boardLayout.tileSide), (int) (x
						* boardLayout.tileSide + boardLayout.tileSide),
				(int) (y * boardLayout.tileSide + boardLayout.tileSide));
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawRect(0, 0, boardLayout.boardSide, boardLayout.boardSide,
				background);
	}

	private void drawLines(Canvas canvas) {
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) {
				drawHorizontalLine(canvas, dark, i);
				drawVerticalLine(canvas, dark, i);
			} else {
				drawHorizontalLine(canvas, light, i);
				drawVerticalLine(canvas, light, i);

			}
		}
	}

	private void drawVerticalLine(Canvas canvas, Paint color, int i) {
		canvas.drawLine(i * boardLayout.tileSide, 0, i * boardLayout.tileSide,
				boardLayout.boardSide, color);
		canvas.drawLine(i * boardLayout.tileSide + 1, 0, i
				* boardLayout.tileSide + 1, boardLayout.boardSide, hilite);
	}

	private void drawHorizontalLine(Canvas canvas, Paint color, int i) {
		canvas.drawLine(0, i * boardLayout.tileSide, boardLayout.boardSide, i
				* boardLayout.tileSide, color);
		canvas.drawLine(0, i * boardLayout.tileSide + 1, boardLayout.boardSide,
				i * boardLayout.tileSide + 1, hilite);
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
				Tile currentTile = puzzle.getTile(col, row);
				Paint color = foreground;
				if (currentTile.isGiven())
					color = givens_color;

				canvas.drawText(currentTile.valueAsString(), col
						* boardLayout.tileSide + xTileCenter, row
						* boardLayout.tileSide + yTileCenter, color);
			}
		}
	}

	private float heightCenterOfText() {
		FontMetrics fm = foreground.getFontMetrics();
		return fm.ascent + fm.descent;
	}

}
