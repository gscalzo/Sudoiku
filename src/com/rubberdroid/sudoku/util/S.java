package com.rubberdroid.sudoku.util;

import java.util.HashMap;

public class S {
	static char[] A;

	static void R() {
		int i, j;
		for (i = 0; i < 81; i++) {
			if (A[i] != '0')
				continue;
			HashMap h = new HashMap();
			for (j = 0; j < 81; j++) {
				h.put(j / 9 == i / 9 || j % 9 == i % 9 || (j / 27 == i / 27)
						&& ((j % 9 / 3) == (i % 9 / 3)) ? "" + A[j] : "0", "1");
			}
			for (j = 1; j <= 9; j++) {
				if (h.get("" + j) == null) {
					A[i] = (char) ('0' + j);
					R();
				}
			}
			A[i] = '0';
			return;
		}
		for (i = 0; i < 81; i++) {
			System.out.print(A[i]);
		}
		System.out.println();
		System.exit(0);
	}

	public static void main(String[] a) {
		String easyPuzzle = "360000000004230800000004200"
				+ "070460003820000014500013020" + "001900000007048300000000045";

		A = easyPuzzle.toCharArray();
		R();
	}
}
