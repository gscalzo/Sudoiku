package com.jordan.sudoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Serializer {

	public static void serialize(Serializable objToSerialize, OutputStream oStream)
			throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(oStream);
		out.writeObject(objToSerialize);
		out.close();
	}

	public static Object deserialize(InputStream iStream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(iStream);

		Object result = in.readObject();
		in.close();
		return result;
	}
}
