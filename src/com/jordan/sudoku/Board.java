package com.jordan.sudoku;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

public class Board {

	private float tileWidth;
	private int height;
	private float tileHeight;
	private int width;
	private Paint dark;
	private Paint hilite;
	private Paint light;
	private Paint background;
	private Paint foreground;
	private Puzzle puzzle;
	int hintColors[];

	public Board(int width, int height, float tileWidth, float tileHeight,
			Puzzle puzzle, Resources resources) {
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
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

		createForegroundPaint(resources);
	}

	private void createForegroundPaint(Resources resources) {
		foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(resources.getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(tileHeight * 0.75f);
		foreground.setTextScaleX(tileWidth / tileHeight);
		foreground.setTextAlign(Paint.Align.CENTER);
	}

	public void draw(Canvas canvas) {
		drawBackground(canvas);
		drawLines(canvas);
		drawNumbers(canvas);
		drawHints(canvas);
	}

	private void drawHints(Canvas canvas) {
		Paint hint = new Paint();
		Rect r = new Rect();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int movesleft = 9 - puzzle.getUsedTiles(i, j).length;
				if (movesleft < hintColors.length) {
					getRect(i, j, r);
					hint.setColor(hintColors[movesleft]);
					canvas.drawRect(r, hint);
				}
			}
		}
	}

	private void getRect(int x, int y, Rect rect) {
		   rect.set((int) (x * width), (int) (y * height), (int) (x
		         * width + width), (int) (y * height + height));
		}

	private void drawBackground(Canvas canvas) {
		canvas.drawRect(0, 0, width, height, background);
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
		canvas.drawLine(i * tileWidth, 0, i * tileWidth, height, color);
		canvas
				.drawLine(i * tileWidth + 1, 0, i * tileWidth + 1, height,
						hilite);
	}

	private void drawHorizontalLine(Canvas canvas, Paint color, int i) {
		canvas.drawLine(0, i * tileHeight, width, i * tileHeight, color);
		canvas.drawLine(0, i * tileHeight + 1, width, i * tileHeight + 1,
				hilite);
	}

	private Paint createPaint(int color, Resources resources) {
		Paint paint = new Paint();
		paint.setColor(resources.getColor(color));
		return paint;
	}

	private void drawNumbers(Canvas canvas) {
		float xTileCenter = tileWidth / 2;
		float yTileCenter = tileHeight / 2 - (heightCenterOfText()) / 2;
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				canvas.drawText(puzzle.getTileString(col, row), col * tileWidth
						+ xTileCenter, row * tileHeight + yTileCenter,
						foreground);
			}
		}

	}

	private float heightCenterOfText() {
		FontMetrics fm = foreground.getFontMetrics();
		return fm.ascent + fm.descent;
	}

}
