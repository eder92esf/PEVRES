package br.com.vilarica.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Temporizador extends Timer {

	public void createTemporizador() {
		Timer timer = new Timer();
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_MONTH, 13);
		data.set(Calendar.MONTH, 7);
		data.set(Calendar.HOUR_OF_DAY, 23);
		data.set(Calendar.MINUTE, 59);
		data.set(Calendar.SECOND, 59);
		System.out.println(data.getTime());
		timer.scheduleAtFixedRate(new TimerTaskImpl(), data.getTime(),
				TimeUnit.MICROSECONDS.convert(1, TimeUnit.DAYS));
	}

}