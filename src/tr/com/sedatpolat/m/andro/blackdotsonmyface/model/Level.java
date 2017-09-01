package tr.com.sedatpolat.m.andro.blackdotsonmyface.model;

import java.io.Serializable;
import java.util.Date;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.GAME_TYPE;

/**
 * 
 * @author sedpol
 *
 */
public class Level implements Serializable {
	private static final long serialVersionUID = 1L;

	private LevelType levelType;
	private GAME_TYPE gameType;
	private int rightAns;
	private int wrongAns;
	private long scor;
	private float rating;
	private long time;
	private Date date = new Date();

	public Level() {
	}

	public LevelType getLevelType() {
		return levelType;
	}

	public void setLevelType(LevelType levelType) {
		this.levelType = levelType;
	}

	public GAME_TYPE getGameType() {
		return gameType;
	}

	public void setGameType(GAME_TYPE gameType) {
		this.gameType = gameType;
	}

	public int getRightAns() {
		return rightAns;
	}

	public void setRightAns(int rightAns) {
		this.rightAns = rightAns;
	}

	public int getWrongAns() {
		return wrongAns;
	}

	public void setWrongAns(int wrongAns) {
		this.wrongAns = wrongAns;
	}

	public long getScor() {
		return scor;
	}

	public void setScor(Long scor) {
		this.scor = scor;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Level [levelType=" + levelType + ", gameType=" + gameType
				+ ", rightAns=" + rightAns + ", wrongAns=" + wrongAns
				+ ", scor=" + scor + ", rating=" + rating + ", time=" + time
				+ ", date=" + date + "]";
	}
}
