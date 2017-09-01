package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;

/**
 * 
 * @author sedpol
 *
 */
public class GlobalVariables {

	private static GlobalVariables instance = null;
	private Level level = null;

	public static synchronized GlobalVariables getInstance() {
		if (instance == null)
			instance = new GlobalVariables();
		return instance;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
