package com.rubberdroid.sudoiku.model;

import java.io.Serializable;
import java.util.Arrays;

public class Tile implements Serializable {
	private static final long serialVersionUID = 2352351809652897583L;

	public static final int NOTES_BUTTON = 11;
	public static final int ERASE_BUTTON = 0;
	public static final int SOLVE_BUTTON = 12;

	private int x;
	private int y;
	private int value;
	private boolean given = true;
	private boolean[] notes = new boolean[9];

	@Override
	public boolean equals(Object o) {
		if (null != o && o instanceof Tile) {
			Tile otherTile = (Tile) o;
			return otherTile.x == x && otherTile.y == y
					&& otherTile.value == value && otherTile.given == given
					&& Arrays.equals(notes, otherTile.notes);

		}

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

	public boolean noteActiveAt(int noteIndex) {
		return notes[noteIndex - 1];
	}

	public void toggleNoteAt(int noteIndex) {
		notes[noteIndex - 1] ^= true;
	}

	public void resetNotes() {
		for (int i = 0; i < 9; ++i)
			notes[i] = false;
	}
}
