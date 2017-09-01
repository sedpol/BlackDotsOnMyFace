package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.R;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.LevelType;

/**
 * 
 * @author sedpol
 *
 */
public class CommonUtil extends Constants {
	
	public static float calculateRatingForDI(Level li) {
		return calcForDI(li);
	}
	
	public static String sifirEkle(String str, int length) {
		if (str.length() >= length) {
			return str;
		}
		int i = 0;
		int l = length - str.length(); 
		
		while(i < l) {
			str = "0" + str;
			i++;
		}
		return str;
	}
	
	private static float calcForDI(Level li) {
		int wa = li.getWrongAns();
		int ra = li.getRightAns();
		int total = ra + wa;

		if (ra - wa <= wa) { 
			if (ra - wa > 1)
				return MIN_STAR + 2.5f;
			if (ra == wa)
				return MIN_STAR + 2f;
			
			return MIN_STAR + (float) (ra) / wa * 2f;
		}
		
		return MIN_STAR + (float) (ra) / total * MAX_STAR;
	}

	/**
	 * range [from, to]
	 * @param from
	 * @param to
	 * @return
	 */
	public static LevelType generateRandLevelInRange(int l) {
		return findLevelType(l);
	}
	
	public static int getLevelTimeValue(LevelType levelType) {
		switch (levelType) {
		case ONE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
			return 10;
		case ELEVEN:
			return 11;
		case TWELVE:
			return 12;
		case THIRTEEN:
			return 13;
		case FOURTEEN:
			return 14;

		case TRAIN:
			return 8;
		case IRON:
			return 8;
		case SILVER:
			return 10;
		case GOLD:
			return 12;
		
		default:
			return 0;
		}
	}
	
	public static int getLevelStarValue(LevelType levelType) {
		switch (levelType) {
		case TRAIN:
			return 0;
		case ONE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
			return 10;
		case ELEVEN:
			return 11;
		case TWELVE:
			return 12;
			
		case IRON:
			return 25;
		case SILVER:
			return 50;
		case GOLD:
			return 100;
			
		default:
			return 0;
		}
	}
	
	public static int getLevelValue(LevelType levelType) {
		switch (levelType) {
		case TRAIN:
			return 0;
		case ONE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
			return 10;
		case ELEVEN:
			return 11;
		case TWELVE:
			return 12;
		case THIRTEEN:
			return 13;
		case FOURTEEN:
			return 14;
			
		case IRON:
			return 900;
		case SILVER:
			return 920;
		case GOLD:
			return 940;
			
		default:
			return 0;
		}
	}
	
	public static LevelType findLevelType(int l) {
		switch (l) {
		case 1:
			return LevelType.ONE;
		case 2:
			return LevelType.TWO;
		case 3:
			return LevelType.THREE;
		case 4:
			return LevelType.FOUR;
		case 5:
			return LevelType.FIVE;
		case 6:
			return LevelType.SIX;
		case 7:
			return LevelType.SEVEN;
		case 8:
			return LevelType.EIGHT;
		case 9:
			return LevelType.NINE;
		case 10:
			return LevelType.TEN;
		case 11:
			return LevelType.ELEVEN;
		case 12:
			return LevelType.TWELVE;
		case 13:
			return LevelType.THIRTEEN;
		case 14:
			return LevelType.FOURTEEN;
			
		case 0:
			return LevelType.TRAIN;
		case 900:
			return LevelType.IRON;
		case 920:
			return LevelType.SILVER;
		case 940:
			return LevelType.GOLD;
		default:
			return null;
		}
	}
	
	public static String organizeNumber (String str) {
		int ch = str.length() -1;
		int counter = 1;
		String result = "";
		
		while (ch >= 0) {
			result = str.charAt(ch) + result;
			if ( ch != 0 && counter%3 == 0)
				result = "." + result;
			ch--;
			counter++;
		}
		if (result.startsWith("."))
			result = result.subSequence(1, result.length()).toString();
		return result.subSequence(0, result.length()-1).toString();
	}
	
	public static float getAbsoluteRating(float rating) {
		float r = 0;
		if (rating == 0f)
			r = 0f;
		else if (rating <= 0.5f)
			r = 0.5f;
		else if (rating <= 1f)
			r = 1f;
		else if (rating <= 1.5f)
			r = 1.5f;
		else if (rating <= 2f)
			r = 2f;
		else if (rating <= 2.5f)
			r = 2.5f;
		else if (rating <= 3f)
			r = 3;
		else if (rating <= 3.5f)
			r = 3.5f;
		else if (rating <= 4f)
			r = 4f;
		else if (rating <= 4.5f)
			r = 4.5f;
		else if (rating <= 5f)
			r = 5f;
		return r;
	}
	
	public static Drawable getRating(Context context, float f) {
		Drawable drawable = context.getResources().getDrawable(R.drawable.star5);
		
		if (f == 0)
			drawable = context.getResources().getDrawable(R.drawable.star);
		else if (f <= 0.5)
			drawable = context.getResources().getDrawable(R.drawable.star_5);
		else if (f <= 1)
			drawable = context.getResources().getDrawable(R.drawable.star1);
		else if (f <= 1.5)
			drawable = context.getResources().getDrawable(R.drawable.star1_5);
		else if (f <= 2)
			drawable = context.getResources().getDrawable(R.drawable.star2);
		else if (f <= 2.5)
			drawable = context.getResources().getDrawable(R.drawable.star2_5);
		else if (f <= 3)
			drawable = context.getResources().getDrawable(R.drawable.star3);
		else if (f <= 3.5)
			drawable = context.getResources().getDrawable(R.drawable.star3_5);
		else if (f <= 4)
			drawable = context.getResources().getDrawable(R.drawable.star4);
		else if (f <= 4.5)
			drawable = context.getResources().getDrawable(R.drawable.star4_5);
		return drawable;
	}

	public static int getsizeAsDp(int size, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (size * scale + 0.5f);
	}
	
	public static boolean isEmpty (String str) {
		if (str == null || str.equals(""))
			return true;
		return false;
	}
	
	public static boolean isNotEmpty (String str) {
		return !isEmpty(str);
	}
}
