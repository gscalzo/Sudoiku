package com.jordan.sudoku;

public class Tile {
	public static final int NOTES_BUTTON = 11;
	public static final int ERASE_BUTTON = 0;
	
	private int x;
	private int y;
	private int value;
	private boolean given = true;

	@Override
	public boolean equals(Object o) {
		if (null != o && o instanceof Tile)
			return ((Tile) o).x == x && ((Tile) o).y == y;

		return false;
	}

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tile(int x, int y, int value) {
		this(x, y);
		this.value = value;
		if (value == 0) {
			given = false;
		}
	}

	@Override
	public String toString() {

		return "Tile: x [" + x + "] y [" + y + "] value [" + value + "]";
	}

	public int value() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String valueAsString() {
		if (value > 0)
			return String.valueOf(value);
		return "";
	}

	public int y() {
		return y;
	}

	public int x() {
		return x;
	}

	public boolean isGiven() {
		return given;
	}
}
