package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;

public class ReaderUtil {

	private static ReaderUtil instance = null;

	public static synchronized ReaderUtil getInstance() {
		if (instance == null)
			instance = new ReaderUtil();
		return instance;
	}

	private ReaderUtil() {
	}
	
	public Level streamReader(FileInputStream file) throws Exception {

		try(ObjectInput input = new ObjectInputStream(new BufferedInputStream(file))) {
			return (Level) input.readObject();
		} 
	}
}
