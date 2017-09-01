package tr.com.sedatpolat.m.andro.blackdotsonmyface.generator;

import java.security.SecureRandom;
import java.util.ArrayList;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.LevelType;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.VMGame;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants;

/**
 * 
 * @author sedpol
 *
 */
public class VisualMemoryGameGenerator extends Constants {

	public static ArrayList<VMGame> generateGame(LevelType levelType) {

		ArrayList<VMGame> games = new ArrayList<VMGame>();

		int row 	= getRowCount(levelType);
		int column 	= getColumnCount(levelType);
		
		boolean isAnyFilled = false;
		
		VMGame game;
		for (int j = 0; j < row*column; j++) {
			game = new VMGame();
			if (rand()) {
				game.setFilled(true);
				isAnyFilled = true;
			} else {
				game.setFilled(false);
			}
			game.setRow(row);
			game.setColumn(column);
			game.setLevel(levelType);
			games.add(game);
		}
		
		if (!isAnyFilled) {
			SecureRandom secureRandom = new SecureRandom();
			int i = secureRandom.nextInt(games.size());
			VMGame temp = games.get(i);
			temp.setFilled(true);
			games.set(i, temp);
		}
		return games;
	}
	
	public static ArrayList<VMGame> generateGameForTraining(LevelType levelType) {
		
		ArrayList<VMGame> games = new ArrayList<VMGame>();
		
		int row 	= getRowCount(levelType );
		int column 	= getColumnCount(levelType);
		
		boolean isAnyFilled = false;
		
		VMGame game;
		for (int j = 0; j < row*column; j++) {
			game = new VMGame();
			if (rand()) {
				game.setFilled(true);
				isAnyFilled = true;
			} else {
				game.setFilled(false);
			}
			game.setRow(row);
			game.setColumn(column);
			game.setLevel(levelType);
			games.add(game);
		}
		
		if (!isAnyFilled) {
			SecureRandom secureRandom = new SecureRandom();
			int i = secureRandom.nextInt(games.size());
			VMGame temp = games.get(i);
			temp.setFilled(true);
			games.set(i, temp);
		}
		return games;
	}

	private static boolean rand() {
		SecureRandom secureRandom = new SecureRandom();
		
		if (secureRandom.nextBoolean())
			return true;
		return false;
	}
	
	/*******************************************************************
	private static Bitmap drawCircle(boolean isFiiled, double size) {

		size = 1.0;
		Bitmap bitMap = createBitMap();
		Canvas canvas = new Canvas(bitMap);

		Paint paint = new Paint();

		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth((float) (9/size));

		if (isFiiled)
			paint.setStyle(Paint.Style.FILL);
		else
			paint.setStyle(Paint.Style.STROKE);

		canvas.drawCircle((float)(100/size), (float)(100/size), (float)(60/size), paint);

		return bitMap;
	}

	private static Bitmap createBitMap() {
		// Create a mutable bitmap
		Bitmap bitMap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);

		bitMap = bitMap.copy(bitMap.getConfig(), true);
		return bitMap;
	}
	*******************************************************************************/
	
	private static int getRowCount(LevelType levelType) {
		switch (levelType) {
		case ONE:
			return 2;
		case TWO:
			return 2;
		case THREE:
			return 2;
		case FOUR:
			return 3;
		case FIVE:
			return 3;
		case SIX:
			return 3;
		case SEVEN:
			return 3;
		case EIGHT:
			return 4;
		case NINE:
			return 3;
		case TEN:
			return 4;
		case ELEVEN:
			return 4;
		case TWELVE:
			return 5;
		case THIRTEEN:
			return 6;
		case FOURTEEN:
			return 6;
		default:
			return 0;
		}
	}

	private static int getColumnCount(LevelType levelType) {
		switch (levelType) {
		case ONE:
			return 2;
		case TWO:
			return 3;
		case THREE:
			return 4;
		case FOUR:
			return 3;
		case FIVE:
			return 3;
		case SIX:
			return 4;
		case SEVEN:
			return 5;
		case EIGHT:
			return 4;
		case NINE:
			return 6;
		case TEN:
			return 5;
		case ELEVEN:
			return 6;
		case TWELVE:
			return 5;
		case THIRTEEN:
			return 5;
		case FOURTEEN:
			return 6;
		default:
			return 0;
		}
	}
}
