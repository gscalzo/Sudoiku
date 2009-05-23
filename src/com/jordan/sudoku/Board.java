package com.jordan.sudoku;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

public class Board {

	private Paint dark;
	private Paint hilite;
	private Paint light;
	private Paint background;
	private Paint foreground;
	private Paint givens_color;
	private SudokuModel puzzle;
	private Paint puzzle_selected;
	int hintColors[];
	private SudokuDimensions dimensions;
	private Bitmap wood_table;

	public Board(SudokuDimensions dimensions, SudokuModel puzzle,
			Resources resources) {
		this.dimensions = dimensions;
		this.puzzle = puzzle;

		wood_table = BitmapFactory.decodeResource(resources, R.drawable.table);

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
		foreground.setTextSize(dimensions.tileSide * 0.75f);
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

	}

	private void drawKeyBoard(Canvas canvas) {
		int upper = dimensions.boardSide + (int) dimensions.tileSide;

		Rect src = new Rect(0, 0, wood_table.getWidth(), wood_table.getHeight());
		Rect dst = new Rect(0, dimensions.boardSide, dimensions.boardSide,
				dimensions.screenHeight);
		canvas.drawBitmap(wood_table, src, dst, null);
		canvas.drawRect(0, upper, dimensions.boardSide, upper
				+ dimensions.tileSide, background);
		
		for (int i = 0; i < 9; ++i) {
			canvas.drawLine(i * dimensions.tileSide, upper, i
					* dimensions.tileSide, upper + dimensions.tileSide, light);
			canvas.drawLine(i * dimensions.tileSide + 1, upper, i
					* dimensions.tileSide + 1, upper + dimensions.tileSide,
					hilite);
		}

		canvas.drawLine(0, upper, dimensions.boardSide, upper, dark);
		canvas.drawLine(0, upper+dimensions.tileSide, dimensions.boardSide, upper+dimensions.tileSide, dark);

		float xTileCenter = dimensions.tileSide / 2;
		float yTileCenter = dimensions.tileSide / 2 - (heightCenterOfText())
				/ 2;
		for (int currentNumber = 1; currentNumber <= 9; currentNumber++) {

			Paint color = foreground;
			Tile selectedTile = puzzle.selectedTile();
			if (selectedTile.isGiven()
					|| puzzle.isUsed(selectedTile.x(), selectedTile.y(),
							currentNumber))
				color = givens_color;

			canvas.drawText(String.valueOf(currentNumber), (currentNumber - 1)
					* dimensions.tileSide + xTileCenter, upper + yTileCenter,
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
		rect.set((int) (x * dimensions.tileSide),
				(int) (y * dimensions.tileSide),
				(int) (x * dimensions.tileSide + dimensions.tileSide), (int) (y
						* dimensions.tileSide + dimensions.tileSide));
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawRect(0, 0, dimensions.boardSide, dimensions.boardSide,
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
		canvas.drawLine(i * dimensions.tileSide, 0, i * dimensions.tileSide,
				dimensions.boardSide, color);
		canvas.drawLine(i * dimensions.tileSide + 1, 0, i * dimensions.tileSide
				+ 1, dimensions.boardSide, hilite);
	}

	private void drawHorizontalLine(Canvas canvas, Paint color, int i) {
		canvas.drawLine(0, i * dimensions.tileSide, dimensions.boardSide, i
				* dimensions.tileSide, color);
		canvas.drawLine(0, i * dimensions.tileSide + 1, dimensions.boardSide, i
				* dimensions.tileSide + 1, hilite);
	}

	private Paint createPaint(int color, Resources resources) {
		Paint paint = new Paint();
		paint.setColor(resources.getColor(color));
		return paint;
	}

	private void drawNumbers(Canvas canvas) {
		float xTileCenter = dimensions.tileSide / 2;
		float yTileCenter = dimensions.tileSide / 2 - (heightCenterOfText())
				/ 2;
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				Tile currentTile = puzzle.getTile(col, row);
				Paint color = foreground;
				if (currentTile.isGiven())
					color = givens_color;

				canvas.drawText(currentTile.valueAsString(), col
						* dimensions.tileSide + xTileCenter, row
						* dimensions.tileSide + yTileCenter, color);
			}
		}
	}

	private float heightCenterOfText() {
		FontMetrics fm = foreground.getFontMetrics();
		return fm.ascent + fm.descent;
	}

}
