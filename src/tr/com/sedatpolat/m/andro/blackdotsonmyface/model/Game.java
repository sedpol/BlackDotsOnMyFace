package tr.com.sedatpolat.m.andro.blackdotsonmyface.model;

import java.io.Serializable;

/**
 * 
 * @author sedpol
 *
 */
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	private LevelType level;
	private FAO op;
	private int qleft;
	private int qright;
	private int aleft;
	private int aright;
	private int answer;
	private int rightAns;
	private int wrongAns;
	private int wrongAswer;

	public LevelType getLevel() {
		return level;
	}

	public void setLevel(LevelType level) {
		this.level = level;
	}

	public FAO getOp() {
		return op;
	}

	public void setOp(FAO op) {
		this.op = op;
	}

	public int getQleft() {
		return qleft;
	}

	public void setQleft(int qleft) {
		this.qleft = qleft;
	}

	public int getQright() {
		return qright;
	}

	public void setQright(int qright) {
		this.qright = qright;
	}

	public int getAleft() {
		return aleft;
	}

	public void setAleft(int aleft) {
		this.aleft = aleft;
	}

	public int getAright() {
		return aright;
	}

	public void setAright(int aright) {
		this.aright = aright;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getWrongAswer() {
		return wrongAswer;
	}

	public void setWrongAswer(int wrong) {
		this.wrongAswer = wrong;
	}

	public int getRightAns() {
		return rightAns;
	}

	public void setRightAns(int trueAns) {
		this.rightAns = trueAns;
	}

	public int getWrongAns() {
		return wrongAns;
	}

	public void setWrongAns(int falseAns) {
		this.wrongAns = falseAns;
	}
}
