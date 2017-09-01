package tr.com.sedatpolat.m.andro.blackdotsonmyface;

import java.security.SecureRandom;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.R;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.R.id;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.GA;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.operation.LocalOperations;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.CommonUtil;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.GATracker;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.util.Constants.GAME_TYPE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

/**
 * 
 * @author sedpol
 *
 */
public class Result extends Activity {

	private StartAppAd startAppAd;

	private Level level = new Level();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		/**
		 * startapp ads
		 */
		try {
			StartAppSDK.init(this, "101163814", "203828113", true);
			
			startAppAd = new StartAppAd(this);
			startAppAd.showAd(); // show the ad
			startAppAd.loadAd(); // load the next ad
			} catch (Throwable t) {}
		/**
		 * end of ads
		 */
		
		if (getIntent().getExtras() != null)
			level = (Level) getIntent().getExtras().getSerializable("LEVEL_INFO");

		level.setRating(CommonUtil.getAbsoluteRating(level.getRating()));

		Level local = LocalOperations.getInstance().readLevelFromLocal(getFilesDir(), level.getGameType());
		
		boolean writeLocal = false;
		if (local == null)
			writeLocal = true;
		else if (local.getScor() < level.getScor())
			writeLocal = true;
		else if (local.getScor() == level.getScor() && local.getTime() > level.getTime())
			writeLocal = true;
		
		if (writeLocal) {
			LocalOperations.getInstance().writeLevelOnLocal(getFilesDir(), level);
		}

		try {
			GATracker.getInstance().sendStatistic(GA.SCOR, level);;
		} catch (Throwable t) {}
		

		/**
		 * startapp ads
		 */
		try {
			if (new SecureRandom().nextInt(3) == 1)
				StartAppAd.showSplash(this, savedInstanceState);
			} catch (Throwable t) {}

		/**
		 * end of ads
		 */

		Button rating 	= (Button) 	 findViewById(id.rating);
		TextView ra		= (TextView) findViewById(id.tv_ra);
		TextView wa 	= (TextView) findViewById(id.tv_wa);
		TextView time 	= (TextView) findViewById(id.tv_time);
		TextView scor 	= (TextView) findViewById(id.tv_scor);
		
		
		ra.setText(getString(R.string.right_answer) + " " + level.getRightAns());
		wa.setText(getString(R.string.wrong_answer) + " " + level.getWrongAns());
		
		time.setText(getString(R.string.time) + " "	+ CommonUtil.organizeNumber(level.getTime() + "") + "s");
		scor.setText(String.valueOf(level.getScor()));
	
		if (GAME_TYPE.ENDLESS.equals(level.getGameType()))
			rating.setBackgroundDrawable(getResources().getDrawable(R.drawable.endless));
		else
			rating.setBackgroundDrawable(getResources().getDrawable(R.drawable.timed));
			
		Button home = (Button) findViewById(id.b_res_home);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button share = (Button) findViewById(id.b_share);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = getString(R.string.share_scor).replace(
						"#SCOR", String.valueOf(level.getScor()));
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.share_subject));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						getString(R.string.share)));
			}
		});
	}
}