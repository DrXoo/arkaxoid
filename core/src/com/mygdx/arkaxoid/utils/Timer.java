package com.mygdx.arkaxoid.utils;

import java.util.Calendar;
import java.util.Date;

import com.badlogic.gdx.utils.TimeUtils;

public class Timer {

	private Date time;
	private Date tickTime;

	private int segsPerTick;
	private int segsToWait;

	private boolean start;

	public Timer(int minutes, int seconds, int eachTick) {
		Calendar calendar = Calendar.getInstance();

		tickTime = calendar.getTime();

		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + seconds);

		time = new Date(calendar.getTime().getTime() - new Date(TimeUtils.millis()).getTime());

		segsPerTick = eachTick;

		start = false;

		segsToWait = minutes * 60 + seconds;
	}

	public void update() {
		if (start && segsToWait > 0) {
			if (TimeUtils.timeSinceMillis(tickTime.getTime()) / 1000 == segsPerTick) {
				tickTime = Calendar.getInstance().getTime();
				time = new Date(time.getTime() - 1000);
				segsToWait--;
			}
		}

	}

	public void start() {
		tickTime = Calendar.getInstance().getTime();
		start = true;
	}

	public void setTime(int minutes, int seconds, int eachTick) {
		Calendar calendar = Calendar.getInstance();

		tickTime = calendar.getTime();

		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + seconds);

		time = new Date(calendar.getTime().getTime() - new Date(TimeUtils.millis()).getTime());

		segsPerTick = eachTick;

		segsToWait = minutes * 60 + seconds;
		
		start = false;
	}

	public boolean hasStarted() {
		return start;
	}

	public void stop() {
		start = false;
	}

	public Date getTime() {
		return time;
	}

	public String showTimeLeft() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		String result = calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
		if (calendar.get(Calendar.SECOND) < 10) {
			result = calendar.get(Calendar.MINUTE) + ":0" + calendar.get(Calendar.SECOND);
		}
		calendar = null;
		return result;
	}

	public int showTimeIsSeconds() {
		return segsToWait;
	}

	public boolean isFinish() {
		return segsToWait == 0;
	}
}
