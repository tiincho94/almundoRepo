package com.challenge.almundo.callcenter.dispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.almundo.callcenter.call.Call;
import com.challenge.almundo.callcenter.employee.Employee;

import lombok.Data;

/*
 * This class represents the Task to be done. It has an Employee and a Call and is in charge of make it run.
 */
@Data
public class Task implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	private Employee employee;
	private Call call;

	public Task(Employee e, Call c) {
		employee = e;
		call = c;
	}

	/*
	 * Method to run the Task
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
		logger.info("Initialization Time for" + " task name - " + call.getId() + " = " + ft.format(d) + "Empleado:"
				+ employee.getName());

		employee.answerCall(call);

		Date dd = new Date();
		SimpleDateFormat ftt = new SimpleDateFormat("hh:mm:ss");
		logger.info("Executing Time for task name - " + call.getId() + " = " + ftt.format(dd));
		logger.info(call.getId() + " complete");
	}
}
