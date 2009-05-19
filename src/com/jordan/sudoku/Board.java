package com.jordan.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

public class Board {

	private Paint dark;
	private Paint hilite;
	private Paint light;
	private Paint background;
	private Paint foreground;
	private Puzzle puzzle;
	private Paint puzzle_selected;
	int hintColors[];
	private SudokuDimensions dimensions;

	public Board(SudokuDimensions dimensions, Puzzle puzzle, Resources resources) {
		this.dimensions = dimensions;
		this.puzzle = puzzle;
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

		createForegroundPaint(resources);
	}

	private void createForegroundPaint(Resources resources) {
		foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(resources.getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(dimensions.tileSide * 0.75f);
		// foreground.setTextScaleX(tileWidth / tileHeight);
		foreground.setTextAlign(Paint.Align.CENTER);
	}

	public void draw(Canvas canvas) {
		drawBackground(canvas);
		drawLines(canvas);
		drawNumbers(canvas);
		drawHints(canvas);

		Point tileSelected = puzzle.getTileSelected();
		Rect selectedRect = new Rect();
		getTileRect(tileSelected.x, tileSelected.y, selectedRect);
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

	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * dimensions.boardSide),
				(int) (y * dimensions.boardSide), (int) (x
						* dimensions.boardSide + dimensions.boardSide),
				(int) (y * dimensions.boardSide + dimensions.boardSide));
	}

	private void getTileRect(int x, int y, Rect rect) {
		rect.set((int) (x * dimensions.tileSide),
				(int) (y * dimensions.tileSide), (int) (x
						* dimensions.tileSide + dimensions.tileSide),
				(int) (y * dimensions.tileSide + dimensions.tileSide));
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
				canvas.drawText(puzzle.getTileString(col, row), col
						* dimensions.tileSide + xTileCenter, row
						* dimensions.tileSide + yTileCenter, foreground);
			}
		}

	}

	private float heightCenterOfText() {
		FontMetrics fm = foreground.getFontMetrics();
		return fm.ascent + fm.descent;
	}

}
