package tr.com.sedatpolat.m.andro.blackdotsonmyface.util;

import java.util.HashMap;

import tr.com.sedatpolat.m.andro.blackdotsonmyface.Welcome;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.GA;
import tr.com.sedatpolat.m.andro.blackdotsonmyface.model.Level;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class GATracker {
	private static final String GA_PROPERTY_ID = "";//TODO add your id
	private static GATracker instance;

	public static GATracker getInstance() {
		if (instance == null)
			instance = new GATracker();
		return instance;
	}
	
	public void sendStatistic(GA ga, Level level) {
		Tracker tracker = getTracker();
		
		if (level == null)
			return;
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put(Fields.HIT_TYPE, 		com.google.analytics.tracking.android.HitTypes.EVENT);
		params.put(Fields.EVENT_CATEGORY, 	ga.toString());
		params.put(Fields.EVENT_LABEL, 		level.getGameType().toString());
		params.put(Fields.EVENT_ACTION, 	level.getScor() + "");
		
		tracker.send(params);
	}
	
	synchronized Tracker getTracker() {
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(Welcome.activity);
      	return analytics.getTracker(GA_PROPERTY_ID);
	}
}
