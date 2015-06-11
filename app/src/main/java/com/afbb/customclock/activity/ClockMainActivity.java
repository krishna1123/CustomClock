package com.afbb.customclock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.afbb.customclock.widget.ProgressWidget;

public class ClockMainActivity extends Activity implements OnClickListener {

	private Handler handler=new Handler();
	private ProgressWidget clockWidget;

	private int timerInSeconds=100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock_main);

		clockWidget=(ProgressWidget) findViewById(R.id.clock_widget_id);
		clockWidget.setTimedText("Start");

		// add your font hear for text font.
		/*
		 Typeface font = Typeface.createFromAsset(getAssets(), "yourfont.ttf");
		clockWidget.setCustomTypeface(font);*/

		clockWidget.setOnClickListener(this);
	}

	// runnable object for handler.
	private Runnable runnable=new Runnable() {

		@Override
		public void run() {

			int seconds=timerInSeconds%(60);
			int minutes=timerInSeconds/(60);
			int hours=timerInSeconds/(60*60);

			clockWidget.setPercentage(seconds + 1);
			handler.postDelayed(runnable, timeDelay);
			
			count+=timeDelay;

			if(timerInSeconds<0)
			{
				handler.removeCallbacks(runnable);
				clockWidget.setTimedText("Time Up");
			}
			if(count==1000)
			{
				count=0;
				timerInSeconds--;
			}
		}
	};
	int count;
	int timeDelay=50;

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

	/*	if(v instanceof ProgressWidget)
		{
			if(!((ProgressWidget)v).isRunning())
			{
				int hours=25;
				int minutes=1;
				int seconds=20;

				timerInSeconds=(hours*60*60)+(minutes*60)+seconds-1;

				handler.postDelayed(runnable, 1000);

				((ProgressWidget)v).setRunning(true);
			}
		}*/
	}
}
