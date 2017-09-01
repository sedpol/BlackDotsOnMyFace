package tr.com.sedatpolat.m.andro.blackdotsonmyface;

import im.delight.apprater.AppRater;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.R;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.R.id;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.LevelType;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.operation.LocalOperations;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.CommonUtil;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.GAME_TYPE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * 
 * @author sedpol
 *
 */
public class Welcome extends BaseGameActivity {
	
	public static Activity activity;

	private TextView scor_endless;
	private TextView scor_timed;
	private long scorEndless;
	private long scorTimed;
	private Level levelTimed;
	private Level levelEndless;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
        
		scor_endless = (TextView) findViewById(id.tv_scor_endless);
		scor_timed = (TextView) findViewById(id.tv_scor_timed);
		
		Button vm = ((Button) findViewById(id.b_endless));
		
		vm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(getApplicationContext(),	Endless.class);
				if (scorEndless == 0) {
					bundle.putInt("LEVEL", CommonUtil.getLevelValue(LevelType.ONE));
					intent.putExtras(bundle);
				}
				startActivity(intent);
			}
		});

		Button di = ((Button) findViewById(id.b_timed));
		di.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(getApplicationContext(),	Timed.class);
				if (scorTimed == 0) {
					bundle.putInt("LEVEL", CommonUtil.getLevelValue(LevelType.ONE));
					intent.putExtras(bundle);
				}
				startActivity(intent);			}
		});
		
		Button boardTimed = ((Button) findViewById(id.b_board_timed));
		boardTimed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (getApiClient() != null && getApiClient().isConnected()) {
						startActivityForResult(Games.Leaderboards
								.getLeaderboardIntent(getApiClient(),
										getString(R.string.leaderboard_timed_game)), 3);
					} else {
						beginUserInitiatedSignIn();
					}
				} catch (Throwable e) {
				}
			}
		});
		Button boardEndless = ((Button) findViewById(id.b_board_endless));
		boardEndless.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if (getApiClient() != null && getApiClient().isConnected()) {
						startActivityForResult(Games.Leaderboards
								.getLeaderboardIntent(getApiClient(),
										getString(R.string.leaderboard_endless_game)), 2);
					} else {
						beginUserInitiatedSignIn();
					}
				} catch (Throwable e) {
				}
			}
		});

		Button vi = ((Button) findViewById(id.b_share));
		
		vi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				
				long scor = scorEndless > scorTimed 	? scorEndless
														: scorTimed;
				
				String shareBody = getString(R.string.share_scor).replace(
						"#SCOR", String.valueOf(scor));
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.share_subject));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						getString(R.string.share)));
			}
		});
		activity = this;
	}
	
	@Override
	protected void onResume() {
		levelEndless 	= LocalOperations.getInstance().readLevelFromLocal(getFilesDir(), GAME_TYPE.ENDLESS);
		levelTimed 		= LocalOperations.getInstance().readLevelFromLocal(getFilesDir(), GAME_TYPE.TIMED);
		
		scorEndless = levelEndless.getScor();
		scorTimed   = levelTimed.getScor();
		
		int l = scorEndless >= scorTimed 	? String.valueOf(scorEndless).length() 
											: String.valueOf(scorTimed).length();
		
		if (scorEndless <= 0)
			scorEndless = 1;
		if (scorTimed <= 0)
			scorTimed = 1;
		
		scor_endless.setText(CommonUtil.sifirEkle(String.valueOf(scorEndless), l));
		scor_timed	.setText(CommonUtil.sifirEkle(String.valueOf(scorTimed), l));
		
		submitScores();

		if (scorEndless > 1 || scorTimed > 1) {
			AppRater appRater = new AppRater(this);
			appRater.setDaysBeforePrompt(0);
			appRater.setLaunchesBeforePrompt(0);
			appRater.setPhrases(R.string.rate_title, R.string.rate_explanation, R.string.rate_now, R.string.rate_later, R.string.rate_never);
			appRater.show();
		}
		super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see com.google.example.games.basegameutils.GameHelper.GameHelperListener#onSignInFailed()
	 */
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.google.example.games.basegameutils.GameHelper.GameHelperListener#onSignInSucceeded()
	 */
	@Override
	public void onSignInSucceeded() {
		submitScores();
	}
	
	private void submitScores() {
		try {
			if (getApiClient() != null && getApiClient().isConnected()) {
				if (scorTimed > 0)
					Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_timed_game), scorTimed);
				if (scorEndless > 0)
					Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_endless_game), scorEndless);
			}
		} catch (Throwable t) {
		}
	}
}
