package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import android.graphics.Color;

/**
 * 
 * @author sedpol
 *
 */
public class Constants {
	
	public static long SUBMIT_SCOR_MS = 15 * 60 * 1000;
	
	public interface BLACK_DOTS {
		public static final int NUMBER_OF_GAME 	= 1;
		public static final int MAX_LEVEL 		= 12;
		public static final int MIN_LEVEL 		= 1;
		public static final int TIME			= 8 * 1000;
	}
	
	public interface TIMED {
		public static final int NUMBER_OF_GAME 	= 1;
		public static final int MAX_LEVEL 		= 12;
		public static final int MIN_LEVEL 		= 1;
		public static final int TIME 			= 100 * 1000;
	}
	
	public enum VISUAL_MEMORY_GAME_STEPS {
		GO_QUESTION,
		GO_ANSWER,
		GO_RESULT
	}
	
	public enum COLOR {
		GREEN,
		BLUE,
		WHITE,
		BLACK,
		YELLOW,
		RED,
		PURPLE,
		ORANGE,
		PINK
	}
	
	public enum GAME_TYPE {
		TIMED,
		ENDLESS
	}
	
	public interface COLORS {
		public static final int PASTEL_ORANGE 	= Color.argb(255, 255, 179, 71);
		public static final int PASTEL_GREEN 	= Color.argb(255, 119, 221, 119);
		public static final int PASTEL_RED 		= Color.argb(255, 255, 105, 97);
		public static final int PASTEL_GRAY		= Color.argb(255, 207, 207, 196);
		public static final int PASTEL_BLUE		= Color.argb(255, 8,   146, 208);
		public static final int PASTEL_PURPLE	= Color.argb(255, 177, 156, 217);
		public static final int PASTEL_PINK		= Color.argb(255, 255, 209, 220);
		public static final int GOLD			= Color.argb(255, 243, 156, 18);
		public static final int YELLOW			= Color.argb(255, 255, 244, 79);
	}
	
	public static final float MAX_STAR = 5;
	public static final float MIN_STAR = 0.0f;
	
	public static final long TIMER_FREQ = 100l;

	public static final int FIRST_LEVEL_TIME = 8;
	public static final int QUESTION_TIME 	= 5;
	public static final int ANSWER_TIME 	= 8;
	
	public static final int LEVEL_D_MX = 25;
	public static final int LEVEL_1_MX = 20;
	public static final int LEVEL_2_MX = 40;
	public static final int LEVEL_3_MX = 60;
	public static final int LEVEL_4_MX = 70;
	public static final int LEVEL_5_MX = 80;
	public static final int LEVEL_6_MX = 100;
	public static final int LEVEL_7_MX = 200;
	public static final int LEVEL_8_MX = 350;
	public static final int LEVEL_9_MX = 500;
	public static final int LEVEL_10_MX = 600;
	public static final int LEVEL_11_MX = 750;
	public static final int LEVEL_12_MX = 999;
	
	public static final int LEVEL_D_MIN = 10;
	public static final int LEVEL_1_MIN = 1;
	public static final int LEVEL_2_MIN = 5;
	public static final int LEVEL_3_MIN = 10;
	public static final int LEVEL_4_MIN = 15;
	public static final int LEVEL_5_MIN = 25;
	public static final int LEVEL_6_MIN = 50;
	public static final int LEVEL_7_MIN = 100;
	public static final int LEVEL_8_MIN = 150;
	public static final int LEVEL_9_MIN = 350;
	public static final int LEVEL_10_MIN = 450;
	public static final int LEVEL_11_MIN = 550;
	public static final int LEVEL_12_MIN = 800;
	
	public static final int LEVEL_D_W = 5;
	public static final int LEVEL_1_W = 3;
	public static final int LEVEL_2_W = 10;
	public static final int LEVEL_3_W = 15;
	public static final int LEVEL_4_W = 20;
	public static final int LEVEL_5_W = 25;
	public static final int LEVEL_6_W = 30;
	public static final int LEVEL_7_W = 40;
	public static final int LEVEL_8_W = 50;
	public static final int LEVEL_9_W = 80;
	public static final int LEVEL_10_W = 90;
	public static final int LEVEL_11_W = 100;
	public static final int LEVEL_12_W = 150;
}
