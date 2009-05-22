package com.jordan.sudoku;

public class Tile {
	private int x;
	private int y;
	private int value;

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
	}

	@Override
	public String toString() {

		return "Tile: x [" + x + "] y [" + y + "]";
	}

	public int value() {
		return value;
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
}
