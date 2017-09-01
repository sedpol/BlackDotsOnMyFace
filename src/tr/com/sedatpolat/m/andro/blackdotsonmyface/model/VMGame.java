package tr.com.sedatpolat.m.andro.blackdotsonmyface.model;

import java.io.Serializable;

/**
 * 
 * @author sedpol
 *
 */
public class VMGame implements Serializable {

	private static final long serialVersionUID = -6777734045329709248L;

	private LevelType level;
	private boolean clicked;
	private boolean filled;
	private int row;
	private int column;
	
	public VMGame() {
		setClicked(false);
	}
	
	public LevelType getLevel() {
		return level;
	}

	public void setLevel(LevelType level) {
		this.level = level;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
