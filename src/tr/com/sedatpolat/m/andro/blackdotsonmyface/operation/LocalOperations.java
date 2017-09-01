package tr.com.sedatpolat.m.andro.blackdotsonmyface.operation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.GAME_TYPE;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.ReaderUtil;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.WriterUtil;

/**
 * 
 * @author sedpol
 *
 */
public class LocalOperations {
	private static LocalOperations instance = null;

	public static synchronized LocalOperations getInstance() {
		if (instance == null)
			instance = new LocalOperations();
		return instance;
	}
	
	private LocalOperations() {
	}
	/**
	 * 
	 * @param dir
	 * @param level 
	 */
	public void deletelevelFromLocal(File dir, Level level) {
		File file = new File(dir.toString() + File.separator + (level.getLevelType() + "_level.json"));
		if(file.exists())
			file.delete();
	}
	
	/**
	 * 
	 * @param level
	 * @param dir
	 *            : getFilesDir()
	 * @return
	 */
	public boolean writeLevelOnLocal(File dir, Level level) {
		try {
			WriterUtil.getInstance().streamWriter(level,
					new FileOutputStream(dir.toString() + File.separator +  level.getGameType().toString() + ".st"));
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 
	 * @param dir
	 *            : getFilesDir()
	 * @param level 
	 * @return
	 */
	public Level readLevelFromLocal(File dir, GAME_TYPE game_type) {
		try {
			String fileName = dir.toString() + File.separator + game_type.toString() + ".st";
			return ReaderUtil.getInstance().streamReader(new FileInputStream(fileName));
		} catch (Throwable e) {
			return new Level();
		}
	}
	
	/**
	 * 
	 * @param dir
	 * @param star
	 */
	public boolean writeScorOnLocal(File dir, Long star) {
		try (ObjectOutput output  = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(dir.toString() + File.separator + "scor.dat")));) {

			output.writeObject(star);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param dir
	 * @return
	 */
	public String readScorFromLocal(File dir) {
		String fileName = dir.toString() + File.separator + "scor.dat";
		try (ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));){
			return input.readObject().toString();
		} catch (Throwable e) {
			return "0";
		}
	}
}
