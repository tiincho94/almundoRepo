package com.challenge.almundo.callcenter.call;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

/**
 * This class represents the call. It has the basic and simple operations of the
 * Call and it knows his random duration.
 */
@Data
public class Call {
	private static final Logger logger = LoggerFactory.getLogger(Call.class);

	private static final int MAX = 10000;
	private static final int MIN = 5000;

	private Long id;

	/**
	 * This method is used to generate a duration of the call by a Random between 5
	 * and 10 sec.
	 * 
	 * @return duration of the call
	 */
	public long getDuration() {
		Random r = new Random();
		return r.nextInt(MAX - MIN) + MIN;
	}

	public Call() {
		this.id = System.currentTimeMillis();
	}

	/**
	 * This method is used to sleep the thread for the call duration.
	 * 
	 */
	public void attend() {
		try {
			long duration = this.getDuration();
			Thread.sleep(duration);
			logger.info("Call {}, duration {}", this.getId(), duration);
		} catch (InterruptedException e) {
			logger.error("Interrupted Exception", e);
		}
	}

}
