package tr.com.sedatpolat.m.andro.blackdotsonmyface.time;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.LevelType;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.CommonUtil;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants;

/**
 * 
 * @author sedpol
 *
 */
public class CalcTime {
	public static int calculateTime(LevelType levelType) {
		if (LevelType.ONE.equals(levelType))
			return Constants.FIRST_LEVEL_TIME * 1000;
		
		return 1000 * (Constants.QUESTION_TIME + CommonUtil.getLevelTimeValue(levelType) / 2);
	}

	public static int calculateTimerDurationForVI(LevelType levelType) {
		if (LevelType.ONE.equals(levelType))
			return Constants.FIRST_LEVEL_TIME * 1000;
		return getLevelTimeValue(levelType) * 1000;
	}

	private static int getLevelTimeValue(LevelType levelType) {
		switch (levelType) {
		case ONE:
			return 6;
		case TWO:
			return 6;
		case THREE:
			return 5;
		case FOUR:
			return 6;
		case FIVE:
			return 6;
		case SIX:
			return 5;
		case SEVEN:
			return 6;
		case EIGHT:
			return 6;
		case NINE:
			return 5;
		case TEN:
			return 6;
		case ELEVEN:
			return 5;
		case TWELVE:
			return 4;
			
		case TRAIN:
			return 6;
		case IRON:
			return 6;
		case SILVER:
			return 5;
		case GOLD:
			return 5;
		default:
			return 6;
		}
	}
}
