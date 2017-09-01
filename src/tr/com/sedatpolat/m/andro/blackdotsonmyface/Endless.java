package tr.com.sedatpolat.m.andro.blackdotsonmyface;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.R;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.R.id;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.generator.VisualMemoryGameGenerator;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.LevelType;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.VMGame;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.CommonUtil;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.BLACK_DOTS;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.COLORS;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.GAME_TYPE;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.VISUAL_MEMORY_GAME_STEPS;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author sedpol
 *
 */
public class Endless extends Activity {

	private long time;
	private int levelTime;
	private int gameCounter;
	private int gameLevel = 1;

	private boolean isGameFinished;
	
	private Level level;
	
	private TextView 	timerTextView;
	private TextView 	promo;
	private Button		nextButton;
	private Button		yieldGame;
	private Button 		continueButton;

	private ArrayList<VMGame> games;

	private LevelType levelType;

	private int right;
	private int wrong;
	
	private CountDownTimer qTimer;
	private boolean isAppInActive;
	private VISUAL_MEMORY_GAME_STEPS step;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visual_memory);

		yieldGame 		= (Button) findViewById(id.b_number_of_game_vm);
		nextButton 		= (Button) findViewById(id.b_vm_next);
		continueButton 	= (Button) findViewById(id.b_vm_continue);

		timerTextView 	= (TextView) findViewById(id.t_timer_vm);
		promo 			= (TextView) findViewById(id.tv_promo_vm);
		
		gameCounter = wrong = right = 0;
		
		if (getIntent().getExtras() != null) {
        	levelType 	= CommonUtil.findLevelType(getIntent().getExtras().getInt("LEVEL", CommonUtil.getLevelValue(LevelType.TWO)));
        }
		
		level = new Level();
		level.setGameType(GAME_TYPE.ENDLESS);
		level.setLevelType(levelType);
		level.setDate(new Date());
		
		levelTime 	= BLACK_DOTS.TIME;

        if (levelType == LevelType.ONE)
        	promo.setVisibility(View.VISIBLE);
        
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				prepareQuestion();
			}
		});
		
		continueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isGameFinished) {

					Bundle bundle = new Bundle();
					bundle.putSerializable("LEVEL_INFO", level);

					Intent intent = new Intent(getApplicationContext(), Result.class);
					
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				} else {
					qTimer.cancel();
					if (step == VISUAL_MEMORY_GAME_STEPS.GO_QUESTION)
						prepareQuestion();
					else if (step == VISUAL_MEMORY_GAME_STEPS.GO_ANSWER)
						prepareAnswer(); 
					else if (step == VISUAL_MEMORY_GAME_STEPS.GO_RESULT)
						prepareResult();
					
				}
			}
		});

		qTimer = new QTimer(levelTime, Constants.TIMER_FREQ);
		prepareQuestion();
	}
	
	private synchronized void prepareQuestion() {
		if (wrong >= 1) {
			continueButton.setText(R.string.finish);
			finished();
			return;
		}
		if (promo.getVisibility() == View.VISIBLE)
			promo.setText(getString(R.string.promo_vm_q));
		
		if (gameCounter == 0)
			time = System.currentTimeMillis();
		
		gameCounter++;

		if (gameCounter % 5 == 0 && gameLevel < CommonUtil.getLevelValue(LevelType.FOURTEEN))
			gameLevel++;
		
		nextButton.setVisibility(View.GONE);
		continueButton.setVisibility(View.VISIBLE);
		continueButton.setText(getString(R.string.memorised));
		
		timerTextView.setVisibility(View.VISIBLE);
		yieldGame.setText(gameCounter + "");

		
		step = VISUAL_MEMORY_GAME_STEPS.GO_ANSWER;
		
		qTimer.cancel();
		qTimer = new QTimer(levelTime, Constants.TIMER_FREQ);
		qTimer.start();

		startGame();
	}

	private synchronized void prepareAnswer() {
		if (promo.getVisibility() == View.VISIBLE)
			promo.setText(getString(R.string.promo_vm_a));

		fillAnsTable(games);
		continueButton.setText(getString(R.string.check));
		nextButton.setVisibility(View.GONE);
		
		step = VISUAL_MEMORY_GAME_STEPS.GO_RESULT;
		
		qTimer.cancel();
		qTimer = new QTimer(levelTime, Constants.TIMER_FREQ);
		qTimer.start();
	}
	
	private synchronized void prepareResult() {
		if (promo.getVisibility() == View.VISIBLE)
			promo.setText(getString(R.string.promo_vm_r));

		fillResultTable(games);
		checkGame(games);
		
		timerTextView.setVisibility(View.GONE);
		
		if (wrong >= 1) {
			finished();
			return;
		}
		prepareQuestion();
	}
	
	private synchronized void nextGame() {
		if (gameCounter >= 3 && promo.getVisibility() == View.VISIBLE)
			promo.setVisibility(View.GONE);
		
    	games = VisualMemoryGameGenerator.generateGameForTraining(CommonUtil.generateRandLevelInRange(gameLevel));
	}

	private synchronized void startGame() {
		nextGame();
		fillQuestionTable(games);
	}
	
	private void clearTableView (TableRow tableRow) {
		tableRow = (TableRow) findViewById(id.tableRow1);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
		tableRow = (TableRow) findViewById(id.tableRow2);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
		tableRow = (TableRow) findViewById(id.tableRow3);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
		tableRow = (TableRow) findViewById(id.tableRow4);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
		tableRow = (TableRow) findViewById(id.tableRow5);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
		tableRow = (TableRow) findViewById(id.tableRow6);
		tableRow.removeAllViews();
		tableRow.refreshDrawableState();
	}
	
	@SuppressWarnings("deprecation")
	private void fillQuestionTable(ArrayList<VMGame> games) {

		TableRow tableRow = null;
		clearTableView(tableRow);
		int row 	= games.get(0).getRow();
		int column	= games.get(0).getColumn();
		int c 		= 0;
		
		for (int i = 0; i < row; i++) {

			if (i == 0) {
				tableRow = (TableRow) findViewById(id.tableRow1);
			} else if (i == 1) {
				tableRow = (TableRow) findViewById(id.tableRow2);
			} else if (i == 2) {
				tableRow = (TableRow) findViewById(id.tableRow3);
			} else if (i == 3) {
				tableRow = (TableRow) findViewById(id.tableRow4);
			} else if (i == 4) {
				tableRow = (TableRow) findViewById(id.tableRow5);
			} else if (i == 5) {
				tableRow = (TableRow) findViewById(id.tableRow6);
			}
			tableRow.removeAllViews();
			tableRow.refreshDrawableState();
			
			tableRow.setGravity(Gravity.CENTER);
			tableRow.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
			
			for (int j = 0; j < column; j++) {

				final VMGame game = games.get(c++);
				final ImageButton imageButton = new ImageButton(getApplicationContext());
				
				Bitmap bitmap;
				if (game.isFilled())
					bitmap = BitmapFactory.decodeResource(
							getApplicationContext().getResources(),
							R.drawable.ic_filled);
				else
					bitmap = BitmapFactory.decodeResource(
							getApplicationContext().getResources(),
							R.drawable.ic_empty);
				
				imageButton.setImageBitmap(bitmap);

				imageButton.setLayoutParams(new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.FILL_PARENT));
				imageButton.setBackgroundColor(Color.TRANSPARENT);
				imageButton.setClickable(false);

				resizeButton(imageButton);
				tableRow.addView(imageButton);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void fillAnsTable(ArrayList<VMGame> games) {

		TableRow tableRow = null;
		clearTableView(tableRow);
		int row 	= games.get(0).getRow();
		int column	= games.get(0).getColumn();
		int c = 0;
		for (int i = 0; i < row; i++) {

			if (i == 0) {
				tableRow = (TableRow) findViewById(id.tableRow1);
			} else if (i == 1) {
				tableRow = (TableRow) findViewById(id.tableRow2);
			} else if (i == 2) {
				tableRow = (TableRow) findViewById(id.tableRow3);
			} else if (i == 3) {
				tableRow = (TableRow) findViewById(id.tableRow4);
			} else if (i == 4) {
				tableRow = (TableRow) findViewById(id.tableRow5);
			} else if (i == 5) {
				tableRow = (TableRow) findViewById(id.tableRow6);
			}

			tableRow.removeAllViews();
			tableRow.setGravity(Gravity.CENTER);
			tableRow.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.FILL_PARENT,
					TableRow.LayoutParams.FILL_PARENT, 1.0f));

			for (int j = 0; j < column; j++) {

				final VMGame game = games.get(c++);
				final ImageButton imageButton = new ImageButton(
						getApplicationContext());

				Bitmap bitmap;
				bitmap = BitmapFactory.decodeResource(
						getApplicationContext().getResources(),
						R.drawable.ic_empty);

				imageButton.setImageBitmap(bitmap);

				imageButton.setLayoutParams(new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.FILL_PARENT));
				imageButton.setBackgroundColor(Color.TRANSPARENT);
				
				imageButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (game.isClicked()) {
							imageButton.setBackgroundColor(Color.TRANSPARENT);
							game.setClicked(false);
						} else {
							imageButton.setBackgroundColor(COLORS.PASTEL_GREEN);
							game.setClicked(true);
						}
					}
				});

				resizeButton(imageButton);
				tableRow.addView(imageButton);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void fillResultTable(ArrayList<VMGame> games) {
		
		TableRow tableRow = null;
		clearTableView(tableRow);
		int row 	= games.get(0).getRow();
		int column	= games.get(0).getColumn();
		int c = 0;
		for (int i = 0; i < row; i++) {
			
			if (i == 0) {
				tableRow = (TableRow) findViewById(id.tableRow1);
			} else if (i == 1) {
				tableRow = (TableRow) findViewById(id.tableRow2);
			} else if (i == 2) {
				tableRow = (TableRow) findViewById(id.tableRow3);
			} else if (i == 3) {
				tableRow = (TableRow) findViewById(id.tableRow4);
			} else if (i == 4) {
				tableRow = (TableRow) findViewById(id.tableRow5);
			} else if (i == 5) {
				tableRow = (TableRow) findViewById(id.tableRow6);
			}
			
			tableRow.removeAllViews();
			tableRow.setGravity(Gravity.CENTER);
			tableRow.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.FILL_PARENT,
					TableRow.LayoutParams.FILL_PARENT, 1.0f));
			
			for (int j = 0; j < column; j++) {
				
				final VMGame vmGame = games.get(c++);
				final ImageButton imageButton = new ImageButton(
						getApplicationContext());
				
				Bitmap bitmap;
				if (vmGame.isFilled())
					bitmap = BitmapFactory.decodeResource(
							getApplicationContext().getResources(),
							R.drawable.ic_filled);
				else
					bitmap = BitmapFactory.decodeResource(
							getApplicationContext().getResources(),
							R.drawable.ic_empty);
				
				imageButton.setImageBitmap(bitmap);
				
				imageButton.setLayoutParams(new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.FILL_PARENT));
				imageButton.setBackgroundColor(Color.TRANSPARENT);
				
				if (vmGame.isClicked() && !vmGame.isFilled()) {
					imageButton.setBackgroundColor(COLORS.PASTEL_RED);
				} else if (vmGame.isFilled() && !vmGame.isClicked()) {
					imageButton.setBackgroundColor(COLORS.PASTEL_ORANGE);
				} else if (vmGame.isClicked()) {
					imageButton.setBackgroundColor(COLORS.PASTEL_GREEN);
				}
				
				resizeButton(imageButton);
				
				tableRow.addView(imageButton);
			}
		}
	}

	private void checkGame(ArrayList<VMGame> vmGames) {
		for (VMGame vmGame : vmGames) {
			if (vmGame.isFilled()) {
				if (!vmGame.isClicked()) {
					if (right + wrong < gameCounter){
						toast(false);
						wrong++;
					}
					return;
				}
			} else {
				if (vmGame.isClicked()) {
					if (right + wrong < gameCounter) {
						toast(false);
						wrong++;
					}
					return;
				}
			}
		}
		if (right + wrong < gameCounter) {
			toast(true);
			right++;
		}
	}

	@SuppressWarnings("deprecation")
	private void resizeButton(ImageButton imageButton) {

		Display display = getWindowManager().getDefaultDisplay();
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);
			int screenWidth = size.x;

			int x = (int) screenWidth/6; //replace 10 by scaling factor

			imageButton.getLayoutParams().height = x;
			imageButton.getLayoutParams().width = x;

			imageButton.setLayoutParams(imageButton.getLayoutParams());
		}

		else {
			int screenWidth = display.getWidth();

			int x = (int) screenWidth/6; //replace 10 by scaling factor


			imageButton.getLayoutParams().height = x;
			imageButton.getLayoutParams().width = x;

			imageButton.setLayoutParams(imageButton.getLayoutParams());
		}
	}

	private void finished() {
		
		long t = System.currentTimeMillis() - time;
		
		level.setRightAns(right);
		level.setWrongAns(wrong);
		level.setTime(t);
		long extra = (2*right*BLACK_DOTS.TIME - t)/1000;
		
		if (extra < 0)
			extra = 0;
		
		level.setScor((long) (right*31 + extra));

		continueButton.setText(getString(R.string.finish));
		continueButton.setVisibility(View.VISIBLE);
		nextButton.setVisibility(View.GONE);
		isGameFinished = true;
	}
	
	private void toast(boolean isSucc) {

		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		Button toastView;
		if (isSucc) {
			toastView = (Button) layout.findViewById(R.id.b_toast_suc);
		} else {
			toastView = (Button) layout.findViewById(R.id.b_toast_fail);
		}
		toastView.setVisibility(View.VISIBLE);
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	@Override
	public void onBackPressed() {
		qTimer.cancel();
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		isAppInActive = true;
		qTimer.cancel();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (qTimer != null && isAppInActive) {
			isAppInActive = false;
			prepareResult();
		}
		super.onResume();
	}
	/**
	 * 
	 * @author sedpol
	 *
	 */
	public class QTimer extends CountDownTimer {

		public QTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			if (step == VISUAL_MEMORY_GAME_STEPS.GO_QUESTION)
				prepareQuestion();
			else if (step == VISUAL_MEMORY_GAME_STEPS.GO_ANSWER)
				prepareAnswer(); 
			else if (step == VISUAL_MEMORY_GAME_STEPS.GO_RESULT)
				prepareResult();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			String str = String.format("%02d.%01d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished),
					(TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)))/100);
    		timerTextView.setText(str);			
		}
	}
}
