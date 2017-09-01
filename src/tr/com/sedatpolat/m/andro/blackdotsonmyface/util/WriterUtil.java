package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import java.io.BufferedOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;

/**
 * 
 * @author sedpol
 *
 */
public class WriterUtil {
	
	private static WriterUtil instance = null;
	public static synchronized WriterUtil getInstance() {
		if (instance == null)
			instance = new WriterUtil();
		return instance ;
	}
	private WriterUtil() {
	}
	public void streamWriter(Level level, OutputStream file) throws Exception {
		// use buffering
		try (ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(file));){
			output.writeObject(level);
		}
	}
}
